package com.bol.interview.mancalaservice.mapper;

import com.bol.interview.common.dto.ScoreDto;
import com.bol.interview.mancalaservice.entity.Score;
import com.bol.interview.mancalaservice.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ScoreMapperTest {

    @Autowired
    private ScoreMapper scoreMapper;
    Score score;
    @BeforeEach
    void setUp() {
        score = TestUtil.createDummyScore();
    }

    @Test
    void scoreToScoreDto() {
        ScoreDto scoreDto = scoreMapper.scoreToScoreDto(score);
        assertNotNull(scoreDto);

        //TODO: Compare score attributes with ScoreDto
    }

}