package com.bol.interview.notificationservice.consumer;

import com.bol.interview.common.dto.PairPlayersDto;
import com.bol.interview.common.events.GameNotificationEvent;
import com.bol.interview.notificationservice.service.NotificationService;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class GameNotificationListener {
    private final NotificationService notificationService;

    public GameNotificationListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "${notification-service.kafka-topic.game-notification}" , containerFactory = "gameNotificationListenerContainerFactory")
    public void receiveMessage(ConsumerRecord<PairPlayersDto, GameNotificationEvent> record) {
        log.debug("Notification received: Key:{} , Event:{}",record.key(), record.value());
        notificationService.notifyPlayers(record.key(), record.value());
    }
}
