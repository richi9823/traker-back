package com.ricardo.traker.configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        LocalDate localDate = LocalDate.now();
        return new OpenAPI()
                .info(new Info().title("Traker API")
                        .description("Traker application")
                        .version(localDate.getYear() + "." + localDate.getMonthValue() + "." +localDate.getDayOfMonth() + "-" + LocalDateTime.now().getNano()));
    }
}
