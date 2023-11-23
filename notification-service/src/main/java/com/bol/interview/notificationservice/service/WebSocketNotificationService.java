package com.bol.interview.notificationservice.service;

import com.bol.interview.common.dto.PairPlayersDto;
import com.bol.interview.common.dto.PlayerDto;
import com.bol.interview.common.events.GameNotificationEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class WebSocketNotificationService implements NotificationService {
    private final SimpMessagingTemplate notificationTemplate;

    public WebSocketNotificationService(SimpMessagingTemplate notificationTemplate) {
        this.notificationTemplate = notificationTemplate;
    }

    @Override
    public void notifyPlayers(PairPlayersDto pairPlayersDto, GameNotificationEvent gameNotificationEvent) {
        pairPlayersDto.playerDtoList().forEach(playerDto -> pushMessageToSingleUser(playerDto, gameNotificationEvent.joinId(), gameNotificationEvent));
    }

    private void pushMessageToSingleUser(PlayerDto playerDto, String joinId, GameNotificationEvent gameNotificationEvent) {
        String destination = "/app/user/" + playerDto.userName() + "/" + joinId;
        log.info("Sending message on " + destination + " to user: " + playerDto + ": " + gameNotificationEvent);
        notificationTemplate.convertAndSend(destination, gameNotificationEvent);

    }

}
