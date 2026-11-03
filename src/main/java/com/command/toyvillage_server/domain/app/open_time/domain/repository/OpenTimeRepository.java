package com.command.toyvillage_server.domain.app.open_time.domain.repository;

import com.command.toyvillage_server.domain.app.open_time.domain.OpenTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OpenTimeRepository extends JpaRepository<OpenTime, Long> {
    List<OpenTime> findAllByOrderByOpenDateAscStartOpenTimeAscIdAsc();

    List<OpenTime> findAllByOpenDateOrderByStartOpenTimeAscIdAsc(LocalDate openDate);
}
