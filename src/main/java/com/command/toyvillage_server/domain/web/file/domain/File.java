package com.command.toyvillage_server.domain.web.file.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "tbl_file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Column(unique = true, nullable = false, name = "file_key")
    private String fileKey;

    @Column(nullable = false, name = "file_name")
    private String fileName;
}
