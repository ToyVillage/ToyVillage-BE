package com.command.toyvillage_server.domain.app.animal_manage.domain.repository;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalLegalStatus;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalManageRepository extends JpaRepository<AnimalManage, Long> {
}
