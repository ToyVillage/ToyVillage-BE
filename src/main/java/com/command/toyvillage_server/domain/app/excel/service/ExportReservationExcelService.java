package com.command.toyvillage_server.domain.app.excel.service;

import com.command.toyvillage_server.domain.app.excel.presentation.ExcelType;
import com.command.toyvillage_server.domain.app.reservation.domain.Reservation;
import com.command.toyvillage_server.domain.app.reservation.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.command.toyvillage_server.domain.app.excel.service.ExcelSheetSupport.*;

@Service
@RequiredArgsConstructor
public class ExportReservationExcelService implements ExcelSheetService {

    private final ReservationRepository reservationRepository;

    @Override
    public ExcelType type() {
        return ExcelType.RESERVATION;
    }

    @Override
    public void execute(XSSFSheet sheet, LocalDate startDate, LocalDate endDate) {
        header(sheet, "예약일", "제목", "예약자명", "인원", "사전답사일", "사전답사 시간", "사전답사 인원",
            "인솔자 수", "방문일", "방문 시간", "퇴장 시간", "상담일", "장소", "금액", "상태");
        forEachPage(page -> reservationRepository.findByReservationDateBetween(
            start(startDate), end(endDate), page(page, "reservationDate")), reservations -> {
            for (Reservation v : reservations) {
                String visitSiteTime = v.getVisitSiteTime() == null ? null : v.getVisitSiteTime() + " ~ " + v.getVisitSiteExitTime();
                row(sheet, v.getReservationDate(), v.getTitle(), v.getReservationName(), v.getReservationCount(),
                    v.getVisitSiteDate(), visitSiteTime, v.getVisitSiteCount(),
                    v.getLeaderCount(), v.getVisitDate(), v.getVisitTime(), v.getExitTime(), v.getCounselDate(),
                    v.getLocation(), v.getMoney(), v.getStatus().getDescription());
            }
        });
    }
}
