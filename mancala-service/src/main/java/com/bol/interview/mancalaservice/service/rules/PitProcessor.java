package com.bol.interview.mancalaservice.service.rules;

import com.bol.interview.mancalaservice.configuration.MancalaConstants;
import com.bol.interview.mancalaservice.entity.Board;
import com.bol.interview.mancalaservice.exception.ExceptionMessages;
import com.bol.interview.mancalaservice.exception.PitIsEmpty;
import com.bol.interview.mancalaservice.model.GameContext;
import com.bol.interview.mancalaservice.model.PitView;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Order(1)
public class PitProcessor implements GameRules {
    private final static int PIT_SCORE_INDEX = MancalaConstants.MAX_PIT_NUMBER - 1;
    @Override
    public void applyRule(GameContext context) {
        //Organize Pits in a clean way
        List<PitView> pitViews = processPits(context);
        context.setPitViews(pitViews);
    }

    private static List<PitView> processPits(GameContext context) {
        List<PitView> pitViews = new ArrayList<>();
        int pitViewIndex=0;

        for (int i = 0; i < context.getGame().getBoardList().size(); i++) {
            Board board = context.getGame().getBoardList().get(i);
            for (int j = 0; j < board.getPitList().size(); j++) {
                Board.Pit pit = board.getPitList().get(j);
                if (pit.getOrder().equals(PIT_SCORE_INDEX) &&
                        !board.getPlayerUserName().equals(context.getPlayerId())) {
                    pitViewIndex++;
                    PitView pitView = getPitView(context, pit, board, pitViews);
                    findSelectedPitIndexInPitViewCollection(context,pitView,pitViewIndex);
                }
            }
            findCurrentBoardIndex(context, board, i);
        }
        return pitViews;
    }

    private static PitView getPitView(GameContext context, Board.Pit pit, Board board, List<PitView> pitViews) {
        PitView pitView = new PitView();
        pitView.setPit(pit);
        pitView.setPlayerUserName(board.getPlayerUserName());
        if (pit.getOrder().equals(PIT_SCORE_INDEX) &&
                board.getPlayerUserName().equals(context.getPlayerId())) {
            pitView.setIsScorePit(true);
        }
        pitViews.add(pitView);
        return pitView;
    }

    private static void findCurrentBoardIndex(GameContext context, Board board, int index) {
        if (board.getPlayerUserName().equals(context.getPlayerId())){
            context.setCurrentBoardIndex(index);
        }
    }

    private static void findSelectedPitIndexInPitViewCollection(GameContext context, PitView pitView, int index) {
        if (pitView.getPlayerUserName().equals(context.getPlayerId()) &&
                pitView.getOrder().equals(context.getSelectedPitIndexInGame())) {
            if (pitView.getValue() == 0) {
                throw new PitIsEmpty(ExceptionMessages.PIT_IS_EMPTY);
            }
            context.setSelectedPitIndexInGame(index);
            context.setSelectedPit(pitView);
        }
    }

}
