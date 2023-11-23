package com.bol.interview.mancalaservice.service.rules;

import com.bol.interview.mancalaservice.configuration.MancalaConstants;
import com.bol.interview.mancalaservice.entity.Board;
import com.bol.interview.mancalaservice.exception.ExceptionMessages;
import com.bol.interview.mancalaservice.model.GameContext;
import com.bol.interview.mancalaservice.model.PitView;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

import static com.bol.interview.mancalaservice.configuration.MancalaConstants.ONE;

@Component
@Order(2)
public class CaptureStone implements GameRules {
    @Override
    public void applyRule(GameContext context) {
        List<PitView> organizedPits = context.getPitViews();
        PitView lastPit = organizedPits.get(context.getLastPitIndex());
        if (!lastPit.getIsScorePit() &&
                lastPit.getPlayerUserName().equals(context.getPlayerId()) &&
                lastPit.getValue().equals(ONE) ) {
            Board.Pit opponentPit = getOpponentFronPitForLastPit(context);
            if (opponentPit.getValue() != 0) {
                lastPit.setValuePlus(opponentPit.getValue());
                opponentPit.setValue(0);
            }

        }
    }

    private Board.Pit getOpponentFronPitForLastPit(GameContext context) {
        Board nextBoard = context.getNextBoard();

        int pitIndex = MancalaConstants.DEFAULT_SMALL_PIT_VALUE - context.getLastPit().getPit().getOrder();;
        for (int i = 0; i < nextBoard.getPitList().size(); i++) {
            if (nextBoard.getPitList().get(i).getOrder().equals(pitIndex)) {
                return nextBoard.getPitList().get(i);
            }
        }
        throw new NoSuchElementException(ExceptionMessages.OPPONENT_PIT_NOT_FOUND);
    }

}
