package com.bol.interview.mancalaservice.service;

import com.bol.interview.common.dto.PlayerDto;

public interface PlayerService {

    PlayerDto getCurrentPlayer() throws ClassCastException;
}
