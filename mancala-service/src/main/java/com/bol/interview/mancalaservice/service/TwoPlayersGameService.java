package com.bol.interview.mancalaservice.service;

import com.bol.interview.common.dto.GameDto;
import com.bol.interview.common.dto.PairPlayersDto;
import com.bol.interview.common.events.GameNotificationEvent;
import com.bol.interview.common.events.NotificationSubject;
import com.bol.interview.mancalaservice.entity.Game;
import com.bol.interview.mancalaservice.mapper.GameMapper;
import com.bol.interview.mancalaservice.model.GameContext;
import com.bol.interview.mancalaservice.repository.GameRepository;
import com.bol.interview.mancalaservice.service.rules.GameRuleProcessor;
import com.bol.interview.mancalaservice.service.validation.GameValidation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TwoPlayersGameService implements GameService {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    private final GameGenerator gameGenerator;

    private final GameValidation gameValidateService;

    private final GameRuleProcessor ruleProcessor;

    @Value("${mancala.kafka-topic.game-notification}")
    private String gameNotificationTopic;
    private final KafkaTemplate<PairPlayersDto, GameNotificationEvent> gameNotificationTemplate;

    private final PairPlayerService pairPlayerService;


    public TwoPlayersGameService(GameRepository gameRepository, GameMapper gameMapper, GameGenerator gameGenerator,
                                 GameValidation gameValidateService, GameRuleProcessor ruleProcessor,
                                 KafkaTemplate<PairPlayersDto, GameNotificationEvent> gameNotificationTemplate,
                                 PairPlayerService pairPlayerService) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
        this.gameGenerator = gameGenerator;
        this.gameValidateService = gameValidateService;
        this.ruleProcessor = ruleProcessor;
        this.gameNotificationTemplate = gameNotificationTemplate;
        this.pairPlayerService = pairPlayerService;
    }

    @Override
    public void newGame(String joinId, PairPlayersDto pairPlayersDto) {
        Game game = gameRepository.insert(gameGenerator.generateNewGame(joinId, pairPlayersDto));
        GameDto gameDto;
        gameDto = gameMapper.gameToGameDto(game);
        //Send new GameDto for subscribers
        gameNotificationTemplate.send(gameNotificationTopic, pairPlayersDto, new GameNotificationEvent(NotificationSubject.Start.getSubject(),joinId, gameDto));
    }

    @Override
    public GameDto makeMove(String joinId, String playerId, int selectedPitIndex) throws
            IllegalStateException,
            NullPointerException {
        GameContext context = GameContext
                .builder()
                .joinId(joinId)
                .selectedPitIndexInPitView(selectedPitIndex)
                .playerId(playerId)
                .build();

        // Fetch the game from the repository
        Game game = gameRepository.findByJoinId(joinId);
        context.setGame(game);

        //Validate parameters with game
        gameValidateService.validate(context);

        // Perform the move
        performMove(context);

        // Save the updated game
        Game savedGame = gameRepository.save(game);

        GameDto gameDto = gameMapper.gameToGameDto(savedGame);

        //Send new GameDto for subscribers
        PairPlayersDto pairPlayersDto = pairPlayerService.getPairPlayers(context);
        gameNotificationTemplate.send(gameNotificationTopic, pairPlayersDto, new GameNotificationEvent(NotificationSubject.Move.getSubject(), context.getJoinId(), gameDto));

        return gameDto;
    }

    private void performMove(GameContext context) {
        ruleProcessor.applyRule(context);
    }


}
