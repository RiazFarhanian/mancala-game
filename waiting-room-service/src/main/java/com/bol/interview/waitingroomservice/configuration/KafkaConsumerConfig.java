package com.bol.interview.waitingroomservice.configuration;

import com.bol.interview.common.dto.PlayerDto;
import com.bol.interview.waitingroomservice.serdes.PlayerDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Properties;

@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${waiting-room-service.group-id}")
    private String groupId;
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value(value = "${waiting-room-service.player-topic}")
    private String playerTopic;

    @Bean
    public KafkaConsumer<String, PlayerDto> playerConsumer() {
        KafkaConsumer<String, PlayerDto> playerConsumer = new KafkaConsumer<>(getProperties());
        //subscribe to player topic is queue for players
        // who joined and waiting for someone to start to play
        playerConsumer.subscribe(Collections.singletonList(playerTopic));
        return playerConsumer;
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
