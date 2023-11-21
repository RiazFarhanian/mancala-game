package com.bol.interview.mancalaservice.model;

import com.bol.interview.mancalaservice.entity.Board;
import com.bol.interview.mancalaservice.entity.Game;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GameContext {

    private Game game;
    private String playerId;
    private String joinId;
    private Integer selectedPitIndexInGame;
    private Integer currentBoardIndex;

    private List<PitView> pitViews;
    private PitView selectedPit;
    private Integer selectedPitIndexInPitView;

    private Integer lastPitIndex;
    private PitView lastPit;

    public String getNextPlayerUserName() {
        return getNextBoard().getPlayerUserName();
    }

    public Board getCurrentBoard(){
        return game.getBoardList().get(currentBoardIndex);
    }

    public Board getNextBoard() {
        int nextBoardIndex = currentBoardIndex + 1;
        int index = (nextBoardIndex < game.getBoardList().size()) ? nextBoardIndex : nextBoardIndex - game.getBoardList().size();
        return game.getBoardList().get(index);
    }

    public void setTurnPlayerId(String turnPlayerId) {
        getGame().setTurnPlayerId(turnPlayerId);
    }

    public Game.Status getGameStatus() {
        return getGame().getStatus();
    }
}
