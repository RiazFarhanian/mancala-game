package com.bol.interview.common.events;

import com.bol.interview.common.dto.GameDto;

public record GameNotificationEvent(String subject, String joinId, GameDto gameDto) {
}
