package com.command.toyvillage_server.domain.app.animal_manage.domain;

import com.command.toyvillage_server.domain.web.file.domain.File;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_animal_observation_file")
public class AnimalObservationFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_observation_id", nullable = false)
    private AnimalObservation animalObservation;
}
