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
        Board.Pit opponentPit = getOpponentPit(context,lastPit);
        if (lastPit.getValue().equals(ONE) &&
                lastPit.getPlayerUserName().equals(context.getPlayerId()) &&
                opponentPit.getValue() != 0) {
            lastPit.setValuePlus(opponentPit.getValue());
        }
    }

    public Board.Pit getOpponentPit(GameContext context,PitView pit) {
        Board nextBoard = context.getNextBoard();
        int pitIndex = MancalaConstants.MAX_PIT_NUMBER - 2 - pit.getOrder();
        return nextBoard.getPitList().stream()
                .filter(pit1 -> pit1.getOrder().equals(pitIndex))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(ExceptionMessages.OPPONENT_PIT_NOT_FOUND));
    }

}
