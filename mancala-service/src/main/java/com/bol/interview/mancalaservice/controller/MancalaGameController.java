package com.bol.interview.mancalaservice.controller;

import com.bol.interview.common.dto.GameDto;
import com.bol.interview.common.dto.LeaveRequestDto;
import com.bol.interview.common.dto.MoveRequestDto;
import com.bol.interview.common.dto.PlayerDto;
import com.bol.interview.mancalaservice.service.GameService;
import com.bol.interview.mancalaservice.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class MancalaGameController {

    private final GameService gameService;
    private final PlayerService jwtPlayerService;

    public MancalaGameController(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.jwtPlayerService = playerService;
    }

    @PostMapping("/move")
    public ResponseEntity<GameDto> makeMove(@RequestBody MoveRequestDto moveRequestDto) {
        PlayerDto currentPlayer = jwtPlayerService.getCurrentPlayer();
        GameDto gameDto = gameService.makeMove(moveRequestDto.joinId(), currentPlayer.userName(), moveRequestDto.selectedPitIndex());
        return ResponseEntity.ok(gameDto);
    }

    @PostMapping("/leave")
    public ResponseEntity<String> leave(@RequestBody LeaveRequestDto leaveRequestDto) {
        PlayerDto currentPlayer = jwtPlayerService.getCurrentPlayer();
        gameService.leave(leaveRequestDto.joinId(), currentPlayer.userName());
        return ResponseEntity.ok(leaveRequestDto.joinId());
    }
}
