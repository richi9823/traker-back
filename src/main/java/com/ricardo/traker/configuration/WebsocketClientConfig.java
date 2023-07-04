package com.ricardo.traker.configuration;

import jakarta.websocket.*;
import lombok.extern.slf4j.Slf4j;
import java.net.URI;
import java.util.Arrays;
import java.util.Map;

@Slf4j
public class WebsocketClientConfig extends Endpoint {

    Session session = null;
    public WebsocketClientConfig(String endpointURI, String token) {
        log.info("start conecction");
        //start connection
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

        try {
            ClientEndpointConfig.Configurator configurator = new ClientEndpointConfig.Configurator() {
                public void beforeRequest(Map headers) {
                    headers.put("Cookie", Arrays.asList(token));
                }
            };
            ClientEndpointConfig clientConfig = ClientEndpointConfig.Builder.create()
                    .configurator(configurator)
                    .build();

            session = container.connectToServer(this, clientConfig, new URI(endpointURI));
            session.setMaxIdleTimeout(0);
        } catch (Exception e) {
            log.error("Exception in websocket connection");
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        session.addMessageHandler(String.class, new WholeTextMsgHandler()); //specify class type for Whole message handler
        this.session = session;
        log.info("Opened session");
    }

    @Override
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("closing websocket");
        this.session = null;
    }
}
