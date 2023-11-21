package com.bol.interview.mancalaservice.mapper;

import com.bol.interview.common.dto.PlayerDto;
import com.bol.interview.mancalaservice.entity.Player;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        componentModel = "spring"
)
public interface PlayerMapper {

    @Mapping(source = "name",target = "name")
    @Mapping(source = "userName",target = "userName")
    Player playerDtoToPlayer(PlayerDto playerDto);

    @Mapping(source = "name",target = "name")
    @Mapping(source = "userName",target = "userName")
    PlayerDto playerToPlayerDto(Player player);


}
