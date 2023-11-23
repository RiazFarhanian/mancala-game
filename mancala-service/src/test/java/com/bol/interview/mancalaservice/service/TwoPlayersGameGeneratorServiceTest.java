package com.bol.interview.mancalaservice.service;

import com.bol.interview.common.dto.PairPlayersDto;
import com.bol.interview.common.dto.PlayerDto;
import com.bol.interview.mancalaservice.entity.Board;
import com.bol.interview.mancalaservice.entity.Game;
import com.bol.interview.mancalaservice.mapper.PlayerMapper;
import com.bol.interview.mancalaservice.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TwoPlayersGameGeneratorServiceTest {

    TwoPlayersGameGeneratorService twoPlayersGameGeneratorService;

    @Mock
    PlayerMapper playerMapper;

    @BeforeEach
    void setUp() {
        twoPlayersGameGeneratorService = new TwoPlayersGameGeneratorService(playerMapper);
    }

    @Test
    void generateNewGame() {
        String joinId = "joinId";
        PairPlayersDto pairPlayersDto = TestUtil.generatePairPlayersDto();

        Game game = twoPlayersGameGeneratorService.generateNewGame(joinId, pairPlayersDto);
        assertEquals(joinId, game.getJoinId());
        assertEquals(Game.Status.New, game.getStatus());
        assertEquals(2, game.getBoardList().size());
        assertEquals(1L, game.getVersion());
        assertNotNull(game.getCreatedDate());
        assertNotNull(game.getLastModifiedDate());

    }

    @Test
    void generateNewBoard() {
        when(playerMapper.playerDtoToPlayer(any(PlayerDto.class))).thenReturn(TestUtil.createDummyPlayer("Harry Potter"));
        PlayerDto playerDto = TestUtil.createDummyPlayerDto("Harry Potter");
        Board board= twoPlayersGameGeneratorService.generateNewBoard(playerDto);
        assertEquals(board.getPlayer().getName(), playerDto.name());
        assertEquals(board.getPitList().size(), 7);
        assertEquals(board.getScore(), 0);
    }

    @Test
    void generateNewPitList() {
        List<Board.Pit> pitList = twoPlayersGameGeneratorService.generateNewPitList();
        assertEquals(pitList.size(), 7);
        for (int i = 0; i < pitList.size(); i++) {
            assertEquals(pitList.get(i).getOrder(), i);
            assertEquals(pitList.get(i).getValue(), 6);
        }
    }
}