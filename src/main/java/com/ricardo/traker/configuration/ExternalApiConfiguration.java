package com.ricardo.traker.configuration;

import com.ricardo.traker.traccar.ApiClient;
import com.ricardo.traker.traccar.User;
import com.ricardo.traker.traccar.api.DevicesApi;
import com.ricardo.traker.traccar.api.SessionApi;
import com.ricardo.traker.traccar.api.UsersApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Configuration
public class ExternalApiConfiguration {

    @Value("${traccar.basePath}")
    private String traccarBasePath;

    @Value("${traccar.webSocketBasePath}")
    private String traccarWebSocketPath;

    @Value("${traccar.timeout:10000}")
    private Integer timeout;

    @Value("${traccar.user:admin@demo.com}")
    private String username;

    @Value("${traccar.pass:demo}")
    private String password;

    @Bean
    public ApiClient traccarApi() {
        ApiClient traccarApiClient = new ApiClient();
        traccarApiClient.setBasePath(traccarBasePath);
        traccarApiClient.setUsername(username);
        traccarApiClient.setPassword(password);

        SessionApi sessionApi = this.sessionApi(traccarApiClient);

        try {
            Mono<ResponseEntity<User> > response = sessionApi.sessionPostWithHttpInfo(username, password);
            List<String> cookies = response.block().getHeaders().get("set-cookie");
            cookies.stream().filter(s -> s.startsWith("JSESSIONID")).findAny().ifPresentOrElse(
                    token ->  new WebsocketClientConfig(traccarWebSocketPath, token),
                    () -> log.error("Cookie not found")
            );
        } catch (WebClientResponseException e) {
            log.error("Error creating session");
        }

        return traccarApiClient;
    }


    @Bean
    public SessionApi sessionApi(ApiClient traccarApi) {
        return new SessionApi(traccarApi);
    }

    @Bean
    public UsersApi usersApi(ApiClient traccarApi) {
        return new UsersApi(traccarApi);
    }

    @Bean
    public DevicesApi devicesApi(ApiClient traccarApi) {return  new DevicesApi(traccarApi); }

}
