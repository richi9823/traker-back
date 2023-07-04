package com.ricardo.traker.configuration;
import jakarta.websocket.MessageHandler;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class WholeTextMsgHandler implements MessageHandler.Whole<String> {
    @Override
    public void onMessage(String chat) {
        System.out.println("Got message - " + chat);
    }
}