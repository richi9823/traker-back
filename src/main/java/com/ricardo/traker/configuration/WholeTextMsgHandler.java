package com.ricardo.traker.configuration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ricardo.traker.model.dto.MessageWebSocket;
import jakarta.websocket.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Slf4j
@Component
public class WholeTextMsgHandler implements MessageHandler.Whole<String> {

    @Autowired
    WebsocketHandler websocketHandler;

    @Override
    public void onMessage(String chat) {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            if(!chat.equalsIgnoreCase("{}")){
                MessageWebSocket messageWebSocket = objectMapper.readValue(chat, MessageWebSocket.class);
                websocketHandler.saveDeviceInfo(messageWebSocket);
            }
        } catch (JsonProcessingException  e) {
            log.warn("WARNING - JsonProcessingException " + e.getMessage());
        } catch (IOException e){
            log.warn("WARNING - IOException " + e.getMessage());
        }

    }
}