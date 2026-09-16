package com.command.toyvillage_server.global.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class TimeZoneConfig {
    private static final String KST_ZONE_ID = "Asia/Seoul";

    @PostConstruct
    void setDefaultTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone(KST_ZONE_ID));
    }
}
