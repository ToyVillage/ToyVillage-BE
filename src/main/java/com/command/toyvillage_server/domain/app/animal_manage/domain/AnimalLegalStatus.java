package com.command.toyvillage_server.domain.app.animal_manage.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_animal_legal_status")
public class AnimalLegalStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_legal_status_id")
    private Long id;

    @Column(name = "kind", nullable = false)
    private String kind;

    public void update(String kind) {
        this.kind = kind;
    }
}
