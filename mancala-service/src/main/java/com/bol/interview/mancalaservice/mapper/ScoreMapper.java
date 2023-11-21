package com.bol.interview.mancalaservice.mapper;

import com.bol.interview.common.dto.ScoreDto;
import com.bol.interview.mancalaservice.entity.Player;
import com.bol.interview.mancalaservice.entity.Score;
import org.mapstruct.*;

import java.util.HashMap;
import java.util.Map;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        componentModel = "spring"
)
public interface ScoreMapper {

    @Mapping(source = "playersScore",target = "playersScore",qualifiedByName = "toPlayersScoreDto")
    ScoreDto scoreToScoreDto(Score score);

    @Named("toPlayersScoreDto")
    default Map<String,Integer> playersScoreToPlayersScoreDto(Map<Player, Integer> playersScore){
        Map<String,Integer> playersScoreDto = new HashMap<>();
        playersScore.forEach((player, score) -> playersScoreDto.put(player.getName(),score));
        return playersScoreDto;

    }
}
