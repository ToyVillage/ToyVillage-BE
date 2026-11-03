package com.command.toyvillage_server.domain.web.auth.admin.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "password_reset_token")
public class PasswordResetToken {

    @Id
    private String token; //uuid

    private String email;

    @TimeToLive
    private int expiration;

    private static final int EXPIRATION_IN_SECONDS = 600; // 10분

    public static PasswordResetToken create(String email) {
        return PasswordResetToken.builder()
                .token(UUID.randomUUID().toString())
                .email(email)
                .expiration(EXPIRATION_IN_SECONDS)
                .build();
    }
}
