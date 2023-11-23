package com.bol.interview.waitingroomservice.service;

import com.bol.interview.common.dto.PairPlayersDto;
import com.bol.interview.common.dto.PlayerDto;
import com.bol.interview.common.events.PlayersPairedEvent;
import com.bol.interview.waitingroomservice.exceptions.UnableToJoinException;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class WaitingRoomService {
    private final KafkaTemplate<String, PlayersPairedEvent> pairPlayerKafkaTemplate;

    private final KafkaTemplate<String, PlayerDto> playerKafkaTemplate;

    private final KafkaConsumer<String, PlayerDto> playerConsumer;

    @Value(value = "${waiting-room-service.pair-player-topic}")
    private String pairPlayerTopic;
    @Value(value = "${waiting-room-service.player-topic}")
    private String playerTopic;

    public WaitingRoomService(KafkaTemplate<String, PlayersPairedEvent> pairPlayerKafkaTemplate, KafkaTemplate<String, PlayerDto> playerKafkaTemplate, KafkaConsumer<String, PlayerDto> playerConsumer) {
        this.pairPlayerKafkaTemplate = pairPlayerKafkaTemplate;
        this.playerKafkaTemplate = playerKafkaTemplate;
        this.playerConsumer = playerConsumer;
    }

    /**
     * Joins a user to the waiting room for the Mancala game.
     * This method attempts to add the specified user to the waiting room, where they
     * can be matched with other players for a game.
     *
     * @param playerDto The DTO (Data Transfer Object) representing the user to join.
     * @throws UnableToJoinException If the user cannot be joined to the waiting room.
     */
    public String joinPlayer(PlayerDto playerDto) throws UnableToJoinException {
        //pole for one player for 2 second
        //we don't want to wait player to join the game
        ConsumerRecords<String, PlayerDto> consumerRecords = playerConsumer.poll(Duration.ofSeconds(1));

        if (consumerRecords.isEmpty()) {
            //if no player available to wait, so the player
            // should wait for someone to join

            //Generate random ID for joinID
            String joinId = UUID.randomUUID().toString();
            playerKafkaTemplate.send(new ProducerRecord<>(playerTopic, joinId, playerDto));
            return joinId;
        } else {
            playerConsumer.commitSync();

            //The second player who is waited found, the pair player should create
            List<PlayerDto> playerDtoList = new ArrayList<>();
            playerDtoList.add(playerDto);
            AtomicReference<String> joinId = new AtomicReference<>();
            consumerRecords.forEach(record -> {
                playerDtoList.add(0, record.value());
                joinId.set(record.key());
            });

            //Pair Player send for Mancala-service to start a new game
            PairPlayersDto pairPlayersDto = new PairPlayersDto(playerDtoList);
            pairPlayerKafkaTemplate.send(pairPlayerTopic, joinId.get(), new PlayersPairedEvent(pairPlayersDto));
            return joinId.get();
        }
    }

}
