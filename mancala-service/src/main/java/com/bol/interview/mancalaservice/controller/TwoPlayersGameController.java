package com.bol.interview.mancalaservice.controller;

import com.bol.interview.common.dto.GameDto;
import com.bol.interview.common.dto.PlayerDto;
import com.bol.interview.mancalaservice.service.GameService;
import com.bol.interview.mancalaservice.service.JwtPlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/game")
public class TwoPlayersGameController {

    private final GameService gameService;
    private final JwtPlayerService jwtPlayerService;

    public TwoPlayersGameController(GameService gameService, JwtPlayerService jwtPlayerService) {
        this.gameService = gameService;
        this.jwtPlayerService = jwtPlayerService;
    }

    @PostMapping("/move")
    public ResponseEntity<GameDto> makeMove(String joinId, int selectedPitIndex) {
        PlayerDto currentPlayer = jwtPlayerService.getCurrentPlayer();
        GameDto gameDto = gameService.makeMove(joinId, currentPlayer.userName(), selectedPitIndex);
        return ResponseEntity.ok(gameDto);
    }
}
