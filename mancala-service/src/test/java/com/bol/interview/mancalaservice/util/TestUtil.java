package com.bol.interview.mancalaservice.util;

import com.bol.interview.common.dto.BoardDto;
import com.bol.interview.common.dto.GameDto;
import com.bol.interview.common.dto.PairPlayersDto;
import com.bol.interview.common.dto.PlayerDto;
import com.bol.interview.mancalaservice.entity.Board;
import com.bol.interview.mancalaservice.entity.Game;
import com.bol.interview.mancalaservice.entity.Player;
import com.bol.interview.mancalaservice.model.GameContext;
import com.bol.interview.mancalaservice.model.PitView;

import java.time.LocalDateTime;
import java.util.*;

public class TestUtil {
    private static final Random random = new Random();

    public static Player createDummyPlayer(String name) {
        Player player = new Player();
        player.setName(name);
        player.setUserName(UUID.randomUUID().toString());
        return player;
    }

    public static Board creatDummyBoard(Player player) {
        Board board = new Board();
        board.setPlayer(player);
        List<Board.Pit> pitList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            pitList.add(createDummyPit(i));
        }
        board.setPitList(pitList);
        return board;
    }

    public static Board creatDummyBoardWithScore(Player player) {
        Board board = new Board();
        board.setPlayer(player);
        List<Board.Pit> pitList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            pitList.add(createDummyPit(i));
        }
        board.setPitList(pitList);
        board.setScore(random.nextInt(100));
        return board;
    }


    public static Board.Pit createDummyPit(Integer order) {
        return new Board.Pit(order, random.nextInt(6));
    }

    public static Game createDummyGame() {
        Game game = new Game();
        game.setId(UUID.randomUUID().toString());
        game.setVersion(random.nextLong(10000L));
        game.setCreatedDate(LocalDateTime.now().minusHours(1));
        game.setLastModifiedDate(LocalDateTime.now());
        game.setStatus(Game.Status.InProgress);
        game.setJoinId(UUID.randomUUID().toString());

        Player firstPlayer = createDummyPlayer("Harry Potter");
        Player secondPlayer = createDummyPlayer("Hermione Granger");
        game.setTurnPlayerId(firstPlayer.getUserName());
        List<Board> boardList = new ArrayList<>();
        boardList.add(creatDummyBoard(firstPlayer));
        boardList.add(creatDummyBoard(secondPlayer));
        game.setBoardList(boardList);
        return game;
    }

    public static Game createDummyGameWithScore() {
        Game game = new Game();
        game.setId(UUID.randomUUID().toString());
        game.setVersion(random.nextLong(10000L));
        game.setCreatedDate(LocalDateTime.now().minusHours(1));
        game.setLastModifiedDate(LocalDateTime.now());
        game.setStatus(Game.Status.InProgress);
        game.setJoinId(UUID.randomUUID().toString());

        Player firstPlayer = createDummyPlayer("Harry Potter");
        Player secondPlayer = createDummyPlayer("Hermione Granger");
        game.setTurnPlayerId(firstPlayer.getUserName());
        List<Board> boardList = new ArrayList<>();
        boardList.add(creatDummyBoardWithScore(firstPlayer));
        boardList.add(creatDummyBoardWithScore(secondPlayer));
        game.setBoardList(boardList);
        return game;
    }

    public static PlayerDto createDummyPlayerDto(String playerName) {
        return new PlayerDto(UUID.randomUUID().toString(), playerName);
    }

    public static PairPlayersDto generatePairPlayersDto() {
        return new PairPlayersDto(
                Arrays.asList(
                        createDummyPlayerDto("Harry Potter"),
                        createDummyPlayerDto("Hermione Granger")
                )
        );
    }


    public static PairPlayersDto createDummyPairPlayersDto() {
        return new PairPlayersDto(
                Arrays.asList(
                        createDummyPlayerDto("Harry Potter"),
                        createDummyPlayerDto("Hermione Granger")
                )
        );
    }

    public static GameDto createDummyGameDto() {
        return new GameDto(
                UUID.randomUUID().toString(),
                "Harry Potter",
                //Send new GameDto for subscribers
                Arrays.asList(
                        creatDummyBoardDto(createDummyPlayerDto("Harry Potter")),
                        creatDummyBoardDto(createDummyPlayerDto("Hermione Granger"))
                )
        );
    }

    private static BoardDto creatDummyBoardDto(PlayerDto player) {
        List<BoardDto.PitDto> pitList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            pitList.add(createDummyPitDto(i));
        }
        return new BoardDto(pitList, player, 0);

    }

    private static BoardDto.PitDto createDummyPitDto(int i) {
        return new BoardDto.PitDto(i, random.nextInt(6));
    }


    public static Game createDummyGameWithNoPits() {
        Game game = new Game();
        game.setId(UUID.randomUUID().toString());
        game.setVersion(random.nextLong(10000L));
        game.setCreatedDate(LocalDateTime.now().minusHours(1));
        game.setLastModifiedDate(LocalDateTime.now());
        game.setStatus(Game.Status.InProgress);
        game.setJoinId(UUID.randomUUID().toString());

        Player firstPlayer = createDummyPlayer("Harry Potter");
        Player secondPlayer = createDummyPlayer("Hermione Granger");
        game.setTurnPlayerId(firstPlayer.getUserName());
        List<Board> boardList = new ArrayList<>();
        boardList.add(creatDummyBoardWithNoPits(firstPlayer));
        boardList.add(creatDummyBoardWithNoPits(secondPlayer));
        game.setBoardList(boardList);
        return game;
    }

    private static Board creatDummyBoardWithNoPits(Player player) {
        Board board = new Board();
        board.setPlayer(player);
        List<Board.Pit> pitList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            pitList.add(createEmptyDummyPit(i));
        }
        board.setPitList(pitList);
        return board;
    }

    public static Board.Pit createEmptyDummyPit(Integer order) {
        return new Board.Pit(order, 0);
    }

    public static List<PitView> createDummyPitViewList(Game game, Player player, Player opponent) {
        List<PitView> pitViews = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            pitViews.add(createDummyPitView(i, 0, false, player));
        }
        pitViews.add(createDummyPitView(6, 0, true, player));
        for (int i = 0; i < 6; i++) {
            pitViews.add(createDummyPitView(i, 0, false, opponent));
        }

        return pitViews;
    }

    public static GameContext createContextForCaptureStoneTest() {
        //Create Game with no stone in each pit
        Game noPitsGame = createDummyGameWithNoPits();
        //Set stone in first pit of first board
        noPitsGame.getBoardList().get(0).getPitList().get(0).setValue(1);
        //Set stone in last small pit of second board
        noPitsGame.getBoardList().get(1).getPitList().get(5).setValue(7);

        //Create PitView List with 0 values
        List<PitView> pitViews = convertGameToPitView(noPitsGame);

        int lastPitIndex = 0;

        int currentBoardIndex = 0;
        String playerId = noPitsGame.getBoardList().get(0).getPlayer().getUserName();

        return GameContext.builder()
                .pitViews(pitViews)
                .game(noPitsGame)
                .lastPitIndex(lastPitIndex)
                .lastPit(pitViews.get(lastPitIndex))
                .currentBoardIndex(currentBoardIndex)
                .playerId(playerId)
                .build();
    }

    public static GameContext createNormalContext() {
        //Create Game with no stone in each pit
        Game noPitsGame = createDummyGameWithNoPits();
        //Set stone in first pit of first board
        noPitsGame.getBoardList().get(0).getPitList().get(0).setValue(2);
        //Set stone in last small pit of second board
        noPitsGame.getBoardList().get(1).getPitList().get(5).setValue(7);

        //Create PitView List with 0 values
        List<PitView> pitViews = convertGameToPitView(noPitsGame);

        int selectedPitIndex = 0;
        int selectedPitIndexInPitView = 0;
        PitView lastPit = pitViews.get(0);
        int lastPitIndex = 0;
        int currentBoardIndex = 0;
        String playerId = noPitsGame.getBoardList().get(0).getPlayer().getUserName();

        return GameContext.builder()
                .pitViews(pitViews)
                .game(noPitsGame)
                .lastPitIndex(lastPitIndex)
                .lastPit(lastPit)
                .currentBoardIndex(currentBoardIndex)
                .playerId(playerId)
                .selectedPitIndexInGame(selectedPitIndex)
                .selectedPitIndexInPitView(selectedPitIndexInPitView)
                .selectedPit(pitViews.get(selectedPitIndex))
                .build();
    }

    public static GameContext createPitEmptyContextForPitProcessor() {
        //Create Game with no stone in each pit
        Game pitsGame = createDummyGameWithNoPits();
        //Set stone in first pit of first board
        pitsGame.getBoardList().get(0).getPitList().get(0).setValue(2);
        //Set stone in last small pit of second board
        pitsGame.getBoardList().get(1).getPitList().get(5).setValue(7);


        int selectedPitIndex = 1;
        String playerId = pitsGame.getBoardList().get(0).getPlayer().getUserName();

        return GameContext.builder()
                .game(pitsGame)
                .playerId(playerId)
                .selectedPitIndexInGame(selectedPitIndex)
                .build();
    }

    public static GameContext createNormalContextForPitProcessor() {
        //Create Game with no stone in each pit
        Game pitsGame = createDummyGameWithNoPits();
        //Set stone in first pit of first board
        pitsGame.getBoardList().get(0).getPitList().get(0).setValue(2);
        //Set stone in last small pit of second board
        pitsGame.getBoardList().get(1).getPitList().get(5).setValue(7);


        int selectedPitIndex = 0;
        String playerId = pitsGame.getBoardList().get(0).getPlayer().getUserName();

        return GameContext.builder()
                .game(pitsGame)
                .playerId(playerId)
                .selectedPitIndexInGame(selectedPitIndex)
                .build();
    }


    public static GameContext createFinishContextForGameFinishTest() {
        //Create Game with no stone in each pit
        Game noPitsGame = createDummyGameWithNoPits();

        for (int i = 0; i < 7; i++) {
            noPitsGame.getBoardList().get(1).getPitList().get(i).setValue(1);
        }

        //Create PitView List with 0 values
        List<PitView> pitViews = convertGameToPitView(noPitsGame);

        int lastPitIndex = 0;
        int currentBoardIndex = 0;
        String playerId = noPitsGame.getBoardList().get(0).getPlayer().getUserName();

        return GameContext.builder()
                .pitViews(pitViews)
                .game(noPitsGame)
                .lastPitIndex(lastPitIndex)
                .currentBoardIndex(currentBoardIndex)
                .playerId(playerId)
                .build();
    }

    public static GameContext createLastPitIsScorePitContext() {
        //Create Game with no stone in each pit
        Game noPitsGame = createDummyGameWithNoPits();
        //Set stone in first pit of first board
        noPitsGame.getBoardList().get(0).getPitList().get(0).setValue(2);
        //Set stone in last small pit of second board
        noPitsGame.getBoardList().get(1).getPitList().get(5).setValue(7);

        //Create PitView List with 0 values
        List<PitView> pitViews = convertGameToPitView(noPitsGame);

        PitView lastPit = pitViews.get(6);
        int lastPitIndex = 6;
        int currentBoardIndex = 0;
        String playerId = noPitsGame.getBoardList().get(0).getPlayer().getUserName();

        return GameContext.builder()
                .pitViews(pitViews)
                .game(noPitsGame)
                .lastPitIndex(lastPitIndex)
                .currentBoardIndex(currentBoardIndex)
                .playerId(playerId)
                .lastPit(lastPit)
                .build();
    }


    private static List<PitView> convertGameToPitView(Game noPitsGame) {
        List<PitView> pitViews = new ArrayList<>();
        for (int i = 0; i < noPitsGame.getBoardList().size(); i++) {
            Board board = noPitsGame.getBoardList().get(i);
            for (int j = 0; j < board.getPitList().size(); j++) {
                Board.Pit pit = board.getPitList().get(j);
                if (!(j == 6 && i == 1))
                    pitViews.add(new PitView(pit, board.getPlayer().getUserName(), j == 6));
            }
        }
        return pitViews;
    }


    private static PitView createDummyPitView(int order, int value, boolean isBigPit, Player player) {
        PitView pitView = new PitView();
        pitView.setPlayerUserName(player.getUserName());
        pitView.setPit(new Board.Pit(order, value));
        pitView.setIsScorePit(isBigPit);
        return pitView;
    }


}