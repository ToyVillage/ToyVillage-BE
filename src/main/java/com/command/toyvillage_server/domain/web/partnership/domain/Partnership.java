package com.command.toyvillage_server.domain.web.partnership.domain;

import com.command.toyvillage_server.domain.web.file.domain.File;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tbl_partnership")
public class Partnership {
    @Id
    @Column(name = "partnership_id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "partnership_phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "partnership_name", nullable = false, length = 50)
    private String name;

    @Column(name = "partnership_email", nullable = false)
    @Email
    private String email;

    @Column(name = "partnership_title",nullable = false , length = 50)
    private String title;

    @Column(name = "partnership_content", nullable = false,columnDefinition = "LONGTEXT")
    private String content;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tbl_partnership_file",
            joinColumns = @JoinColumn(name = "partnership_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    private List<File> files = new ArrayList<>();

    @Column(name = "partnership_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PartnershipType type;

    @CreatedDate
    @Column(name = "partnership_date",nullable = false)
    private LocalDateTime createdDate;
}
