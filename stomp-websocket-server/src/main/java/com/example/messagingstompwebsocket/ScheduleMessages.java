package com.example.messagingstompwebsocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
public class ScheduleMessages {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    private int increment = 0;

    @Scheduled(fixedDelay = 3000)
    public void sendEchoMessages() {
        Greeting gt = new Greeting("This is a Scheduled message : " + increment++);
        simpMessagingTemplate.convertAndSend("/topic/greetings", gt);
    }

}
