package com.bol.interview.mancalaservice.controller;

import com.bol.interview.common.dto.PlayerDto;
import com.bol.interview.common.dto.ScoreDto;
import com.bol.interview.mancalaservice.service.PlayerService;
import com.bol.interview.mancalaservice.service.ScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class MancalaScoreController {
    private final PlayerService jwtPlayerService;
    private final ScoreService scoreService;

    public MancalaScoreController(PlayerService jwtPlayerService, ScoreService scoreService) {
        this.jwtPlayerService = jwtPlayerService;
        this.scoreService = scoreService;
    }

    @PostMapping("/scores")
    public ResponseEntity<List<ScoreDto>> getScores() {
        PlayerDto currentPlayer = jwtPlayerService.getCurrentPlayer();
        List<ScoreDto> topScores = scoreService.getTopScores(currentPlayer.userName());
        return ResponseEntity.ok(topScores);
    }
}
