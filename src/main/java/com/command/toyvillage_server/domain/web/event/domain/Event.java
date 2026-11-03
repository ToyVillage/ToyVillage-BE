package com.command.toyvillage_server.domain.web.event.domain;

import com.command.toyvillage_server.domain.web.file.domain.File;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_event")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", nullable = false)
    private Long id;

    @Column(name = "event_name",nullable = false)
    private String title;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;

    @Column(name = "event_description", nullable = false)
    private String description;

    @Column(name = "event_start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "event_end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "event_subjects", nullable = false)
    private String subjects;

    public void update(String title, String description, LocalDateTime startDate, LocalDateTime endDate, String subjects,File file) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.subjects = subjects;
        this.file = file;
    }
}
