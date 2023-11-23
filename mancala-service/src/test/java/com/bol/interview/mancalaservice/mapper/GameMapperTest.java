package com.bol.interview.mancalaservice.mapper;

import com.bol.interview.common.dto.BoardDto;
import com.bol.interview.common.dto.GameDto;
import com.bol.interview.mancalaservice.entity.Board;
import com.bol.interview.mancalaservice.entity.Game;
import com.bol.interview.mancalaservice.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class GameMapperTest {

    @Autowired
    private GameMapper gameMapper;

    Game game;

    @BeforeEach
    void setUp() {
        game = TestUtil.createDummyGame();
    }

    @Test
    void gameToGameDto() {
        GameDto gameDto = gameMapper.gameToGameDto(game);
        assertNotNull(gameDto);
        assertEquals(game.getJoinId(),gameDto.joinId());
        assertEquals(game.getTurnPlayerId(),gameDto.turnPlayerId());
        for (int i = 0; i < game.getBoardList().size(); i++) {
            Board board = game.getBoardList().get(i);
            BoardDto boardDto = gameDto.boardList().get(i);
            assertEquals(boardDto.playerDto().name(),board.getPlayer().getName());
            for (int j = 0; j < board.getPitList().size(); j++) {
                BoardDto.PitDto pitDto = boardDto.pitDtoList().get(j);
                Board.Pit pit = board.getPitList().get(j);
                assertEquals(pit.getOrder(),pitDto.order());
                assertEquals(pit.getValue(),pitDto.value());
            }
        }
    }

    @Test
    void boardToBoardDto() {
        Board board = game.getBoardList().get(0);
        BoardDto boardDto = gameMapper.boardToBoardDto(board);
        assertEquals(boardDto.playerDto().name(),board.getPlayer().getName());
        for (int j = 0; j < board.getPitList().size(); j++) {
            BoardDto.PitDto pitDto = boardDto.pitDtoList().get(j);
            Board.Pit pit = board.getPitList().get(j);
            assertEquals(pit.getOrder(),pitDto.order());
            assertEquals(pit.getValue(),pitDto.value());
        }
    }

    @Test
    void pitToPitDto(){
        Board.Pit pit = game.getBoardList().get(0).getPitList().get(0);
        BoardDto.PitDto pitDto = gameMapper.pitToPitDto(pit);
        assertEquals(pit.getOrder(),pitDto.order());
        assertEquals(pit.getValue(),pitDto.value());
    }
}