package com.bol.interview.waitingroomservice.configuration;

import com.bol.interview.common.dto.PairPlayers;
import com.bol.interview.common.dto.Player;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    private Map<String, Object> getDefaultConfig() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        return configProps;
    }

    @Bean
    public ProducerFactory<String, Player> playerProducerFactory() {
        Map<String, Object> configProps = getDefaultConfig();
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Player> playerKafkaTemplate() {
        return new KafkaTemplate<>(playerProducerFactory());
    }


    @Bean
    public ProducerFactory<String, PairPlayers> pairPlayerProducerFactory() {
        Map<String, Object> configProps = getDefaultConfig();
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, PairPlayers> pairPlayerKafkaTemplate() {
        return new KafkaTemplate<>(pairPlayerProducerFactory());
    }

}
