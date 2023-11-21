package com.bol.interview.notificationservice.consumer;

import com.bol.interview.common.dto.PairPlayersDto;
import com.bol.interview.common.events.GameNotificationEvent;
import com.bol.interview.notificationservice.service.NotificationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@KafkaListener(topics = "${notification-service.kafka-topic.game-notification}" , containerFactory = "gameNotificationListenerContainerFactory")
public class GameNotificationListener {
    private final NotificationService notificationService;

    public GameNotificationListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaHandler
    public void receiveMessage(PairPlayersDto pairPlayersDto, GameNotificationEvent gameNotificationEvent) {
        log.debug("Notification received: Key:{} , Event:{}",pairPlayersDto, gameNotificationEvent);
        notificationService.notifyPlayers(pairPlayersDto, gameNotificationEvent);
    }
}
