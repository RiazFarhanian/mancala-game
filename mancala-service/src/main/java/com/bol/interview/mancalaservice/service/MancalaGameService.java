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
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class MancalaGameService implements GameService {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    private final GameGenerator gameGenerator;

    private final GameValidation moveGameValidation;

    private final GameValidation leaveGameValidation;

    private final GameRuleProcessor ruleProcessor;

    @Value("${mancala.kafka-topic.game-notification}")
    private String gameNotificationTopic;
    private final KafkaTemplate<PairPlayersDto, GameNotificationEvent> gameNotificationTemplate;

    private final PairPlayerService pairPlayerService;


    public MancalaGameService(GameRepository gameRepository, GameMapper gameMapper, GameGenerator gameGenerator,
                              GameValidation moveGameValidation, GameValidation leaveGameValidation, GameRuleProcessor ruleProcessor,
                              KafkaTemplate<PairPlayersDto, GameNotificationEvent> gameNotificationTemplate,
                              PairPlayerService pairPlayerService) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
        this.gameGenerator = gameGenerator;
        this.moveGameValidation = moveGameValidation;
        this.leaveGameValidation = leaveGameValidation;
        this.ruleProcessor = ruleProcessor;
        this.gameNotificationTemplate = gameNotificationTemplate;
        this.pairPlayerService = pairPlayerService;
    }

    @Override
    public void newGame(String joinId, PairPlayersDto pairPlayersDto) {
        //Generate new game
        Game generatedNewGame = gameGenerator.generateNewGame(joinId, pairPlayersDto);
        //Save new game
        Game game = gameRepository.insert(generatedNewGame);
        Game save = gameRepository.save(game);
        //Convert game to GameDto
        GameDto gameDto = gameMapper.gameToGameDto(save);
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
                .selectedPitIndexInGame(selectedPitIndex)
                .playerId(playerId)
                .build();

        // Fetch the game from the repository
        Game game = gameRepository.findByJoinId(joinId);
        context.setGame(game);

        //Validate parameters with game
        moveGameValidation.validate(context);

        // Perform the move
        performMove(context);

        // Save the updated game
        Game savedGame = gameRepository.save(game);

        //Convert game to GameDto
        GameDto gameDto = gameMapper.gameToGameDto(savedGame);

        //Get PairPlayersDto
        PairPlayersDto pairPlayersDto = pairPlayerService.getPairPlayers(context);
        //Send new GameDto for subscribers
        gameNotificationTemplate.send(gameNotificationTopic, pairPlayersDto, new GameNotificationEvent(NotificationSubject.Move.getSubject(), context.getJoinId(), gameDto));

        return gameDto;
    }

    @Override
    public void leave(String joinId, String playerId) {
        Game game = gameRepository.findByJoinId(joinId);
        GameContext context = GameContext.builder()
                .joinId(joinId)
                .game(game)
                .build();
        leaveGameValidation.validate(context);

        game.setStatus(Game.Status.Left);
        Game savedGame = gameRepository.save(game);

        GameDto gameDto = gameMapper.gameToGameDto(savedGame);
        PairPlayersDto pairPlayersDto = pairPlayerService.getPairPlayers(context,playerId);
        gameNotificationTemplate.send(gameNotificationTopic, pairPlayersDto, new GameNotificationEvent(
                NotificationSubject.Leave.getSubject(), context.getJoinId(), gameDto));
    }

    private void performMove(GameContext context) {
        ruleProcessor.applyRule(context);
    }


}
