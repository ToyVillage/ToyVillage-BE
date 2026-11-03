package com.command.toyvillage_server.domain.web.news.domain;

import com.command.toyvillage_server.domain.web.file.domain.File;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "tbl_news")
@EntityListeners(AuditingEntityListener.class)
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id", nullable = false)
    private Long id;

    @Column(name = "news_title", nullable = false)
    private String title;

    @Column(name = "news_description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tbl_news_file",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    private List<File> files = new ArrayList<>();

    @CreatedDate
    @Column(name = "news_postdate",nullable = false)
    private LocalDateTime createdDate;

    public void update(String title, String description, List<File> files) {
        this.title = title;
        this.description = description;
        this.files = files;
    }

    protected News(String title, String description, List<File> files) {
        this.title = title;
        this.description = description;
        this.files = files;
    }

    public static News create(String title, String description, List<File> files) {
        return new News(title, description, files);
    }
}
