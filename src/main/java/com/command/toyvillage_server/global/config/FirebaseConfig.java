package com.command.toyvillage_server.global.config;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class FirebaseConfig {
    @Value("${firebase.service-account.project-id}")
    private String projectId;

    @Value("${firebase.service-account.client-email}")
    private String clientEmail;

    @Value("${firebase.service-account.private-key}")
    private String privateKey;

    @Bean(destroyMethod = "delete")
    public FirebaseApp firebaseApp() throws IOException {
        FirebaseOptions options = FirebaseOptions.builder()
            .setProjectId(projectId)
            .setCredentials(ServiceAccountCredentials.newBuilder()
                .setClientEmail(clientEmail)
                .setPrivateKeyString(privateKey.replace("\\n", "\n"))
                .build())
            .build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}
