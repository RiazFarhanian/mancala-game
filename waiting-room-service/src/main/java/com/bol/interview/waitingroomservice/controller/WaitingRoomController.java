package com.bol.interview.waitingroomservice.controller;

import com.bol.interview.common.dto.PlayerDto;
import com.bol.interview.waitingroomservice.service.PlayerService;
import com.bol.interview.waitingroomservice.service.WaitingRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/waitingroom")
public class WaitingRoomController {

    private final PlayerService playerService;
    private final WaitingRoomService roomService;

    public WaitingRoomController(PlayerService playerService, WaitingRoomService roomService) {
        this.playerService = playerService;
        this.roomService = roomService;
    }


    @PostMapping("/join")
    public ResponseEntity<String> join() {
        PlayerDto requestPlayerDto = playerService.getCurrentPlayer();
        try{
            String joinId = roomService.joinPlayer(requestPlayerDto);
            return ResponseEntity.ok(joinId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to join waiting room: " + e.getMessage());
        }
    }


}
