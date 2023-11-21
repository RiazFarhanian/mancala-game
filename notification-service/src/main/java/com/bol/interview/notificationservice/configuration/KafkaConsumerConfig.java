package com.bol.interview.notificationservice.configuration;

import com.bol.interview.common.dto.PairPlayersDto;
import com.bol.interview.common.events.GameNotificationEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value(value = "${spring.kafka.consumer.properties.spring.json.trusted.packages}")
    private String trustedPackages;

    private Map<String, Object> getDefaultSettings() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        props.put("spring.kafka.consumer.properties.spring.json.trusted.packages", trustedPackages);
        return props;
    }

    @Bean
    public ConsumerFactory<PairPlayersDto, GameNotificationEvent> gameNoticationEventConsumerFactory() {
        Map<String, Object> props = getDefaultSettings();
        return new DefaultKafkaConsumerFactory<>(props, new JsonDeserializer<>(PairPlayersDto.class), new JsonDeserializer<>(GameNotificationEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<PairPlayersDto, GameNotificationEvent> gameNotificationListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<PairPlayersDto, GameNotificationEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(gameNoticationEventConsumerFactory());
        return factory;
    }

}
