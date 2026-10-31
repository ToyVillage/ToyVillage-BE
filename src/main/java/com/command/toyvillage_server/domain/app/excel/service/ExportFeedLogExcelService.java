package com.command.toyvillage_server.domain.app.excel.service;

import com.command.toyvillage_server.domain.app.excel.service.dto.ExcelSheet;
import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import com.command.toyvillage_server.domain.app.feed_log.domain.Unit;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FeedLogRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportFeedLogExcelService {

    private static final int PAGE_SIZE = 200;
    private static final int MAX_ROWS = 10_000;
    private static final List<String> HEADERS =
        List.of("급여일시", "개체명", "먹이 종류", "급여량", "남은 양", "단위", "특이사항", "작성자");

    private final FeedLogRepository feedLogRepository;

    public ExcelSheet execute(LocalDate startDate, LocalDate endDate) {
        List<List<Object>> rows = new ArrayList<>();
        for (int page = 0; ; page++) {
            List<FeedLog> feedLogs = feedLogRepository.findByFeedDateTimeBetween(
                startDate.atStartOfDay(), endDate.atTime(23, 59, 59, 999_999_000),
                PageRequest.of(page, PAGE_SIZE, Sort.by("feedDateTime", "id")));
            for (FeedLog f : feedLogs) {
                rows.add(Arrays.asList(f.getFeedDateTime(), animalName(f.getAnimalManage()), f.getFeedType(),
                    f.getFeedAmount(), f.getRemainingAmount(), unit(f.getFeedUnit()), f.getSignificant(),
                    f.getAppAdmin() == null ? null : f.getAppAdmin().getName()));
            }
            if (rows.size() > MAX_ROWS) {
                throw new ToyVillageException(ErrorCode.EXCEL_EXPORT_TOO_LARGE);
            }
            if (feedLogs.size() < PAGE_SIZE) {
                return new ExcelSheet(HEADERS, rows);
            }
        }
    }

    private String animalName(AnimalManage animal) {
        return animal.isDeleteYn() ? animal.getAnimalName() + " (삭제됨)" : animal.getAnimalName();
    }

    private String unit(Unit value) {
        return switch (value) {
            case GML -> "g/mL";
            case KGL -> "kg/L";
        };
    }
}
