package com.bol.interview.mancalaservice.mapper;

import com.bol.interview.common.dto.BoardDto;
import com.bol.interview.common.dto.GameDto;
import com.bol.interview.common.dto.PlayerDto;
import com.bol.interview.mancalaservice.entity.Board;
import com.bol.interview.mancalaservice.entity.Game;
import com.bol.interview.mancalaservice.entity.Player;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        componentModel = "spring"
)
public interface GameMapper {

    @Mapping(source = "joinId",target = "joinId")
    @Mapping(source = "turnPlayerId",target = "turnPlayerId")
    @Mapping(source = "boardList",target = "boardList")
    GameDto gameToGameDto(Game game);


    @Mapping(source = "pitList",target = "pitDtoList")
    @Mapping(source = "score",target = "score")
    @Mapping(source = "player",target = "playerDto")
    BoardDto boardToBoardDto(Board board);

    @Mapping(source = "order",target = "order")
    @Mapping(source = "value",target = "value")
    BoardDto.PitDto pitToPitDto(Board.Pit pit);

    List<BoardDto.PitDto> pitListToPitDtoList(List<Board.Pit> pitList);

    @Mapping(source = "name",target = "name")
    @Mapping(source = "userName",target = "userName")
    PlayerDto playerToPlayerDto(Player player);

}
