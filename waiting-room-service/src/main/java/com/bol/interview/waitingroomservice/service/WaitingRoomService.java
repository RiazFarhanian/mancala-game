package com.bol.interview.waitingroomservice.service;

import com.bol.interview.common.dto.PairPlayers;
import com.bol.interview.common.dto.Player;
import com.bol.interview.waitingroomservice.exceptions.UnableToJoinException;
import com.bol.interview.waitingroomservice.serdes.PlayerDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class WaitingRoomService {
    private final KafkaTemplate<String, PairPlayers> pairPlayerKafkaTemplate;

    private final KafkaTemplate<String, Player> playerKafkaTemplate;

    @Value(value = "${waiting-room-service.player-topic}")
    private String playerTopic;
    @Value(value = "${waiting-room-service.pair-player-topic}")
    private String pairPlayerTopic;
    @Value(value = "${waiting-room-service.group-id}")
    private String groupId;
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public WaitingRoomService(KafkaTemplate<String, PairPlayers> pairPlayerKafkaTemplate, KafkaTemplate<String, Player> playerKafkaTemplate) {
        this.pairPlayerKafkaTemplate = pairPlayerKafkaTemplate;
        this.playerKafkaTemplate = playerKafkaTemplate;
    }

    /**
     * Joins a user to the waiting room for the Mancala game.
     * This method attempts to add the specified user to the waiting room, where they
     * can be matched with other players for a game.
     *
     * @param player The DTO (Data Transfer Object) representing the user to join.
     * @throws UnableToJoinException If the user cannot be joined to the waiting room.
     */
    public String joinPlayer(Player player) throws UnableToJoinException {
        try (KafkaConsumer<String, Player> playerConsumer = new KafkaConsumer<>(getProperties())) {
            //subscribe to player topic is queue for players
            // who joined and waiting for someone to start to play
            playerConsumer.subscribe(Collections.singletonList(playerTopic));

            //pole for one player for 2 second
            //we don't want to wait player to join the game
            ConsumerRecords<String, Player> consumerRecords = playerConsumer.poll(Duration.ofSeconds(2));


            if (consumerRecords.isEmpty()) {
                //if no player available to wait, so the player
                // should wait for someone to join

                //Generate random ID for joinID
                String joinId = UUID.randomUUID().toString();
                playerKafkaTemplate.send(new ProducerRecord<>(playerTopic, joinId, player));
                return joinId;
            } else {
                //The second player who is waited found, the pair player should create
                List<Player> playerList = new ArrayList<>();
                playerList.add(player);
                AtomicReference<String> joinId = new AtomicReference<>();
                consumerRecords.forEach(record -> {
                    playerList.add(0, record.value());
                    joinId.set(record.key());
                });

                //Pair Player send for Mancala-service to start a new game
                pairPlayerKafkaTemplate.send(pairPlayerTopic, joinId.get(), new PairPlayers(playerList));
                return joinId.get();
            }
        } catch (Exception e) {
            throw new UnableToJoinException(e);
        }
    }


    private Properties getProperties() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "PlayerConsumer");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, PlayerDeserializer.class.getName());
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        // Set the maximum number of records to poll
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);

        return props;
    }
}
