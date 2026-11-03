package com.command.toyvillage_server.domain.web.auth.admin.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "email_verification")
public class EmailVerification {

    @Id
    private String email;

    private String code;

    private int failCount;

    @TimeToLive
    private int expiration;

    private static final int EXPIRATION_IN_SECONDS = 300; // 5분
    private static final int MAX_FAIL_COUNT = 5;

    public static EmailVerification create(String email, String code) {
        return EmailVerification.builder()
                .email(email)
                .code(code)
                .expiration(EXPIRATION_IN_SECONDS) // 5분
                .failCount(MAX_FAIL_COUNT)
                .build();
    }

    public boolean isValid(String inputCode) {
        return this.code.equals(inputCode);
    }

    public void increaseFailCount() {
        this.failCount--;
    }
}
