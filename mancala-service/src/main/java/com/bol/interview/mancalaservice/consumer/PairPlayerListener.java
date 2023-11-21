package com.bol.interview.mancalaservice.consumer;

import com.bol.interview.common.events.PlayersPairedEvent;
import com.bol.interview.mancalaservice.service.GameService;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@KafkaListener(topics = "${mancala.kafka-topic.pair-player-topic}" , containerFactory = "pairPlayersListenerContainerFactory")
public class PairPlayerListener {
    private final GameService gameService;

    public PairPlayerListener(GameService gameService) {
        this.gameService = gameService;
    }

    @KafkaHandler
    public void receiveMessage(String joinId, PlayersPairedEvent playersPairedEvent) {
        log.debug("Message Received: {} , {}",joinId,playersPairedEvent.toString());
        gameService.newGame(joinId,playersPairedEvent.pairPlayersDto());
    }
}
