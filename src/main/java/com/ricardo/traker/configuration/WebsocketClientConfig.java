package com.ricardo.traker.configuration;

import com.ricardo.traker.traccar.User;
import com.ricardo.traker.traccar.api.SessionApi;
import jakarta.websocket.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
public class WebsocketClientConfig extends Endpoint {

    @Autowired
    WholeTextMsgHandler wholeTextMsgHandler;

    @Autowired
    SessionApi sessionApi;

    @Value("${traccar.user:admin@demo.com}")
    private String username;

    @Value("${traccar.pass:demo}")
    private String password;

    @Value("${traccar.webSocketBasePath}")
    private String traccarWebSocketPath;

    Session session = null;
    public void initWebsocket(String token) {
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

            session = container.connectToServer(this, clientConfig, new URI(traccarWebSocketPath));
            session.setMaxIdleTimeout(0);
        } catch (Exception e) {
            log.error("Exception in websocket connection");
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        session.addMessageHandler(String.class, wholeTextMsgHandler); //specify class type for Whole message handler
        this.session = session;
        log.info("Opened session");
    }

    @Override
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("closing websocket - " + reason.getReasonPhrase());
        this.session = null;
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Mono<ResponseEntity<User>> response = sessionApi.sessionPostWithHttpInfo(username, password);
        List<String> cookies = response.block().getHeaders().get("set-cookie");
        cookies.stream().filter(s -> s.startsWith("JSESSIONID")).findAny().ifPresentOrElse(
                token ->  this.initWebsocket(token),
                () -> log.error("Cookie not found")
        );


    }
}
