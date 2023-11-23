package com.bol.interview.mancalaservice.service.rules;

import com.bol.interview.common.dto.GameDto;
import com.bol.interview.common.dto.PairPlayersDto;
import com.bol.interview.common.events.GameNotificationEvent;
import com.bol.interview.common.events.NotificationSubject;
import com.bol.interview.mancalaservice.entity.Board;
import com.bol.interview.mancalaservice.entity.Game;
import com.bol.interview.mancalaservice.mapper.GameMapper;
import com.bol.interview.mancalaservice.model.GameContext;
import com.bol.interview.mancalaservice.service.PairPlayerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.bol.interview.mancalaservice.configuration.MancalaConstants.ZERO;

@Component
@Order(4)
public class GameFinish implements GameRules {
    private final PairPlayerService pairPlayerService;
    private final GameMapper gameMapper;

    @Value("${mancala.kafka-topic.game-notification}")
    private String gameNotificationTopic;

    @Value("${mancala.debug-mode}")
    private boolean isDebugMode;

    private final KafkaTemplate<PairPlayersDto, GameNotificationEvent> gameNotificationTemplate;

    public GameFinish(PairPlayerService pairPlayerService, GameMapper gameMapper, KafkaTemplate<PairPlayersDto, GameNotificationEvent> gameNotificationTemplate) {
        this.pairPlayerService = pairPlayerService;
        this.gameMapper = gameMapper;
        this.gameNotificationTemplate = gameNotificationTemplate;
    }

    @Override
    public void applyRule(GameContext context) {
        if (isGameEnded(context) || (isDebugMode && isGameEndedFake(context))) {
            //Calculate final scores and put it to Big Pit
            context.getGame().getBoardList().forEach(board -> {
                Integer score = board.getPitList().stream().map(Board.Pit::getValue).reduce(Integer::sum).orElse(0);
                board.setScore(score);
            });
            context.getGame().setStatus(Game.Status.Finished);
            GameDto gameDto = gameMapper.gameToGameDto(context.getGame());
            gameNotificationTemplate.send(gameNotificationTopic,
                    pairPlayerService.getPairPlayers(context),
                    new GameNotificationEvent(NotificationSubject.End.getSubject(), context.getJoinId(), gameDto));
        }
    }

    private boolean isGameEnded(GameContext context) {
        String userName = context.getCurrentBoard().getPlayerUserName();
        return context.getPitViews().stream()
                .filter(pit -> pit.getPlayerUserName().equals(userName) &&
                        !pit.getIsScorePit())
                .allMatch(pit -> pit.getValue().equals(ZERO));
    }

    private boolean isGameEndedFake(GameContext context) {
        return context.getGame().getBoardList().get(0).getPitList().get(6).getValue() > 10 ||
                context.getGame().getBoardList().get(1).getPitList().get(6).getValue() > 10;
    }

}
