package com.bol.interview.notificationservice.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/public")
    public void sendPublicMessage(){
    }

}