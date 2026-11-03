package com.command.toyvillage_server.global.security.jwt;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Getter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private static final int MIN_SECRET_KEY_BYTES = 64;

    private final String header;
    private final String prefix;
    private final String webSecretKey;
    private final String appSecretKey;
    private final Long webAccessExpiration;
    private final Long webRefreshExpiration;
    private final Long appAccessExpiration;
    private final Long appRefreshExpiration;

    public JwtProperties(
            String header,
            String prefix,
            String webSecretKey,
            String appSecretKey,
            Long webAccessExpiration,
            Long webRefreshExpiration,
            Long appAccessExpiration,
            Long appRefreshExpiration
    ) {
        this.header = header;
        this.prefix = prefix;
        this.webSecretKey = encodeSecretKey(webSecretKey);
        this.appSecretKey = encodeSecretKey(appSecretKey);
        this.webAccessExpiration = webAccessExpiration;
        this.webRefreshExpiration = webRefreshExpiration;
        this.appAccessExpiration = appAccessExpiration;
        this.appRefreshExpiration = appRefreshExpiration;
    }

    private String encodeSecretKey(String secretKey) {
        if (secretKey == null) {
            throw new IllegalArgumentException("JWT secret key must not be null");
        }

        byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        if (secretKeyBytes.length < MIN_SECRET_KEY_BYTES) {
            secretKeyBytes = sha512(secretKeyBytes);
        }

        return Base64.getEncoder().encodeToString(secretKeyBytes);
    }

    private byte[] sha512(byte[] secretKeyBytes) {
        try {
            return MessageDigest.getInstance("SHA-512").digest(secretKeyBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 algorithm is not available", e);
        }
    }
}
