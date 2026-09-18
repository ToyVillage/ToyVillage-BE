package com.command.toyvillage_server.domain.app.notice.domain;

import com.command.toyvillage_server.domain.web.file.domain.File;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "content")
    private String content;

    @Column(nullable = false)
    private LocalDate createdAt;

    @OneToMany
    @JoinTable(
        name = "tbl_notice_file",
        joinColumns = @JoinColumn(name = "notice_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "file_id", unique = true, nullable = false)
    )
    private List<File> files = new ArrayList<>();

    public void update(String title, String content, List<File> files) {
        this.title = title;
        this.content = content;
        if (files != null) {
            this.files.clear();
            this.files.addAll(files);
        }
    }

    private Notice(String title, String content, List<File> files) {
        this.title = title;
        this.content = content;
        this.createdAt = LocalDate.now();
        this.files = new ArrayList<>(files);
    }

    public static Notice create(String title, String content, List<File> files) {
        return new Notice(title, content, files);
    }
}
