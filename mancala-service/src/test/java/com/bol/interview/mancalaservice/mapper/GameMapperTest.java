package com.bol.interview.mancalaservice.mapper;

import com.bol.interview.common.dto.GameDto;
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
        game.getBoardList().forEach(board -> {

        });

        //TODO: Compare game attributes with gameDto


    }

    @Test
    void boardToBoardDto() {
    }
}