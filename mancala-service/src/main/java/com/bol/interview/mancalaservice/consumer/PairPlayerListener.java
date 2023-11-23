package com.bol.interview.mancalaservice.consumer;

import com.bol.interview.common.events.PlayersPairedEvent;
import com.bol.interview.mancalaservice.service.GameService;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class PairPlayerListener {
    private final GameService gameService;

    @Value("${mancala.new-game-wait-time}")
    private Integer timeToWait;

    public PairPlayerListener(GameService gameService) {
        this.gameService = gameService;
    }

    @KafkaListener(topics = "${mancala.kafka-topic.pair-player-topic}" , containerFactory = "pairPlayersListenerContainerFactory")
    public void receiveMessage(ConsumerRecord<String, PlayersPairedEvent> record) {
        waitFor(timeToWait);
        log.debug("Message Received: {} , {}",record.key(),record.value().toString());
        gameService.newGame(record.key(),record.value().pairPlayersDto());
    }

    private static void waitFor(Integer timeToWait) {
        try {
            Thread.sleep(timeToWait);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
