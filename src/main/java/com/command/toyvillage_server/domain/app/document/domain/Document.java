package com.command.toyvillage_server.domain.app.document.domain;

import com.command.toyvillage_server.domain.web.file.domain.File;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Long id;

    @Column(name = "document_title", length = 100, nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false)
    private DocumentType type;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany
    @JoinTable(
        name = "tbl_document_file",
        joinColumns = @JoinColumn(name = "document_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "file_id", unique = true, nullable = false)
    )
    private List<File> files;

    @Builder
    public Document(String title, DocumentType type, List<File> files) {
        this.title = title;
        this.type = type;
        this.files = files != null ? files : new ArrayList<>();
    }

    public void update(String title, DocumentType type, List<File> files) {
        this.title = title;
        this.type = type;
        this.files.clear();
        this.files.addAll(files);
    }
}



