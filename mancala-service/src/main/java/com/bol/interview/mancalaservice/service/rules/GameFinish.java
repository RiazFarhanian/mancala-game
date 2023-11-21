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

    private final KafkaTemplate<PairPlayersDto, GameNotificationEvent> gameNotificationTemplate;
    public GameFinish(PairPlayerService pairPlayerService, GameMapper gameMapper,KafkaTemplate<PairPlayersDto, GameNotificationEvent> gameNotificationTemplate) {
        this.pairPlayerService = pairPlayerService;
        this.gameMapper = gameMapper;
        this.gameNotificationTemplate = gameNotificationTemplate;
    }

    @Override
    public void applyRule(GameContext context) {
        if (isGameEnded(context)) {
            //Calculate final scores and put it to Big Pit
            context.getGame().getBoardList().forEach(board -> {
                int size = board.getPitList().size();
                Board.Pit scorePit = board.getPitList().get(size - 1);
                for (int i = 0; i < size - 1; i++) {
                    Board.Pit pit = board.getPitList().get(i);
                    scorePit.setValuePlus(pit.getValue());
                    pit.setValue(ZERO);
                }
            });
            context.getGame().setStatus(Game.Status.Finished);
            GameDto gameDto = gameMapper.gameToGameDto(context.getGame());
            gameNotificationTemplate.send(gameNotificationTopic,
                    pairPlayerService.getPairPlayers(context),
                    new GameNotificationEvent(NotificationSubject.End.getSubject(), context.getJoinId(), gameDto));

        }
    }

    public boolean isGameEnded(GameContext context){
        String userName = context.getCurrentBoard().getPlayerUserName();
        return context.getPitViews().stream()
                .filter(pit -> pit.getPlayerUserName().equals(userName) &&
                        !pit.getIsScorePit())
                .allMatch(pit -> pit.getValue().equals(ZERO));
    }

}