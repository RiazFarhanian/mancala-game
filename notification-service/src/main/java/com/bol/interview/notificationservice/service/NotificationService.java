package com.bol.interview.notificationservice.service;

import com.bol.interview.common.dto.PairPlayersDto;
import com.bol.interview.common.events.GameNotificationEvent;

public interface NotificationService {
    void notifyPlayers(PairPlayersDto pairPlayersDto, GameNotificationEvent gameNotificationEvent);
}
