package com.command.toyvillage_server.global.swagger;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {
    private final String username;
    private final String password;

    public SwaggerProperties(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("Swagger username/password must not be blank");
        }
        this.username = username;
        this.password = password;
    }
}
