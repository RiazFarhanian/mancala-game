package com.bol.interview.mancalaservice.exception;

public class ExceptionMessages {
    public static final String GAME_NOT_FOUND = "Game Not Found!";
    public static final String NOT_PLAYABLE_GAME = "Game cant be played anymore!";
    public static final String PIT_IS_EMPTY = "Pit Is Empty!";
    public static final String WRONG_TURN = "Wrong turn of the player!";
    public static final String INVALID_PIT_SELECTED = "Invalid Pit Selected";
    public static final String OPPONENT_PIT_NOT_FOUND = "Opponent Pit Not Found!";
    public static final String JWT_CAST_EXCEPTION = "principal can't cast to JWT Token!";
    public static final String GAME_IS_NULL = "Game is Null!";

    // Add more messages as needed

    private ExceptionMessages() {
        // Private constructor to prevent instantiation
    }
}
