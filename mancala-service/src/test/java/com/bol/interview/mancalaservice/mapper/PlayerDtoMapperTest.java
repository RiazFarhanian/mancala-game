package com.bol.interview.mancalaservice.mapper;

import com.bol.interview.common.dto.PlayerDto;
import com.bol.interview.mancalaservice.entity.Player;
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
class PlayerDtoMapperTest {

    @Autowired
    private PlayerMapper playerMapper;
    private PlayerDto playerDto;

    @BeforeEach
    void setUp() {
        playerDto = TestUtil.createDummyPlayerDto("Harry Potter");
    }

    @Test
    void playerDtoToPlayer() {
        Player player = playerMapper.playerDtoToPlayer(playerDto);
        assertNotNull(player);
        assertEquals(player.getName(),playerDto.name());
        assertEquals(player.getUserName(),playerDto.userName());
    }
}