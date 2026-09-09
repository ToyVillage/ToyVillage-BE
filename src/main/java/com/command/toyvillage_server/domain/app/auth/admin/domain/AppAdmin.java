package com.command.toyvillage_server.domain.app.auth.admin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@Table(name = "tbl_app_admin")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_admin_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppAdminRole role;

    @Column(name = "position", length = 30)
    private String position;

    public static AppAdmin createAppAdmin(String username, String name, String encodedPassword) {
        return create(username, name, encodedPassword, AppAdminRole.APP_ADMIN, null);
    }

    public static AppAdmin createEmployee(String username, String name, String encodedPassword, String position) {
        return create(username, name, encodedPassword, AppAdminRole.EMPLOYEE, position);
    }

    private static AppAdmin create(
            String username,
            String name,
            String encodedPassword,
            AppAdminRole role,
            String position
    ) {
        return AppAdmin.builder()
                .username(username)
                .name(name)
                .password(encodedPassword)
                .role(role)
                .position(position)
                .build();
    }

    public boolean isAppAdmin() {
        return role == AppAdminRole.APP_ADMIN;
    }

    public void changePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
