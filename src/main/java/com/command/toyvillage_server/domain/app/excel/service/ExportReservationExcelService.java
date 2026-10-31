package com.command.toyvillage_server.domain.app.excel.service;

import com.command.toyvillage_server.domain.app.excel.service.dto.ExcelSheet;
import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.command.toyvillage_server.domain.app.reservation.domain.Reservation;
import com.command.toyvillage_server.domain.app.reservation.domain.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportReservationExcelService {

    private static final int PAGE_SIZE = 200;
    private static final int MAX_ROWS = 10_000;
    private static final List<String> HEADERS = List.of("예약일", "제목", "예약자명", "인원", "사전답사일",
        "사전답사 시작", "사전답사 종료", "사전답사 인원", "인솔자 수", "방문일", "방문 시간", "퇴장 시간", "상담일", "장소",
        "금액", "상태");

    private final ReservationRepository reservationRepository;

    public ExcelSheet execute(LocalDate startDate, LocalDate endDate) {
        List<List<Object>> rows = new ArrayList<>();
        for (int page = 0; ; page++) {
            List<Reservation> reservations = reservationRepository.findByReservationDateBetween(
                startDate, endDate, PageRequest.of(page, PAGE_SIZE, Sort.by("reservationDate", "id")));
            for (Reservation v : reservations) {
                rows.add(Arrays.asList(v.getReservationDate(), v.getTitle(), v.getReservationName(), v.getReservationCount(),
                    v.getVisitSiteDate(), v.getVisitSiteTime(), v.getVisitSiteExitTime(), v.getVisitSiteCount(),
                    v.getLeaderCount(), v.getVisitDate(), v.getVisitTime(), v.getExitTime(), v.getCounselDate(),
                    v.getLocation(), v.getMoney(), v.getStatus().getDescription()));
            }
            if (rows.size() > MAX_ROWS) {
                throw new ToyVillageException(ErrorCode.EXCEL_EXPORT_TOO_LARGE);
            }
            if (reservations.size() < PAGE_SIZE) {
                return new ExcelSheet(HEADERS, rows);
            }
        }
    }
}
