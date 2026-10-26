package com.command.toyvillage_server.domain.app.notification.domain;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tbl_fcm_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmToken {
    @Id
    @Column(nullable = false, length = 512)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_admin_id", nullable = false)
    private AppAdmin appAdmin;
}
