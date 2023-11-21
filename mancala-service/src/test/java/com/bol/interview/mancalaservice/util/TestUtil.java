package com.bol.interview.mancalaservice.util;

import com.bol.interview.common.dto.PlayerDto;
import com.bol.interview.mancalaservice.entity.Board;
import com.bol.interview.mancalaservice.entity.Game;
import com.bol.interview.mancalaservice.entity.Player;
import com.bol.interview.mancalaservice.entity.Score;

import java.time.LocalDateTime;
import java.util.*;

public class TestUtil {
    private static final Random random =new Random();
    public static Player createDummyPlayer(String name) {
        Player player = new Player();
        player.setName(name);
        player.setUserName(UUID.randomUUID().toString());
        return player;
    }

    public static Board creatDummyBoard(Player player){
        Board board = new Board();
        board.setPlayer(player);
        List<Board.Pit> pitList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            pitList.add(createDummyPit(i));
        }
        board.setPitList(pitList);
        return board;
    }

    public static Board.Pit createDummyPit(Integer order){
        return new Board.Pit(order,random.nextInt(6));
    }

    public static Game createDummyGame(){
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


    public static Score createDummyScore() {
        Score score = new Score();
        score.setPlayerUsername(UUID.randomUUID().toString());
        Map<Player,Integer> playersScore = new HashMap<>();
        Player firstPlayer = createDummyPlayer("Harry Potter");
        Player secondPlayer = createDummyPlayer("Hermione Granger");
        playersScore.put(firstPlayer,random.nextInt(36));
        playersScore.put(secondPlayer,random.nextInt(36));
        score.setPlayersScore(playersScore);
        return score;
    }

    public static PlayerDto createDummyPlayerDto(String playerName) {
        return new PlayerDto(UUID.randomUUID().toString(),playerName);
    }
}