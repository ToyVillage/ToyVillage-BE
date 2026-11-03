package com.command.toyvillage_server.domain.web.animal.domain.repository;

import com.command.toyvillage_server.domain.web.animal.domain.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
