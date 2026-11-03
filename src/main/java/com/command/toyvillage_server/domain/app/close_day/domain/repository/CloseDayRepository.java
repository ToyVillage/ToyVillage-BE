package com.command.toyvillage_server.domain.app.close_day.domain.repository;

import com.command.toyvillage_server.domain.app.close_day.domain.CloseDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CloseDayRepository extends JpaRepository<CloseDay, Long> {
    List<CloseDay> findAllByOrderByStartCloseTimeAscIdAsc();

    List<CloseDay> findAllByStartCloseTimeLessThanEqualAndEndCloseTimeGreaterThanEqualOrderByStartCloseTimeAscIdAsc(
            LocalDate startCloseTime,
            LocalDate endCloseTime
    );
}
