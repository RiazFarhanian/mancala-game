package com.bol.interview.common.events;

import lombok.Getter;

@Getter
public enum NotificationSubject {
    Move("move"),
    Start("start"),
    End("end"),
    Leave("leave");


    private final String subject;

    NotificationSubject(String subject) {
        this.subject = subject;
    }
}
