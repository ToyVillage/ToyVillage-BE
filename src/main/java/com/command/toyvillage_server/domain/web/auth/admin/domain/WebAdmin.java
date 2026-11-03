package com.command.toyvillage_server.domain.web.auth.admin.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_web_admin")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class WebAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "web_admin_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    public static WebAdmin create(String email, String password) {
        return WebAdmin.builder()
                .email(email)
                .password(password)
                .build();
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
