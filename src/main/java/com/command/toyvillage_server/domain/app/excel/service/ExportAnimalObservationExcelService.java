package com.command.toyvillage_server.domain.app.excel.service;

import com.command.toyvillage_server.domain.app.excel.service.dto.ExcelFiles;
import com.command.toyvillage_server.domain.app.excel.service.dto.ExcelSheet;
import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalObservation;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalObservationFileRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalObservationRepository;
import com.command.toyvillage_server.domain.web.file.domain.File;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExportAnimalObservationExcelService {

    private static final int PAGE_SIZE = 200;
    private static final int MAX_ROWS = 10_000;
    private static final List<String> HEADERS = List.of("작성일시", "개체명", "제목", "내용", "작성자", "첨부파일");

    private final AnimalObservationRepository animalObservationRepository;
    private final AnimalObservationFileRepository animalObservationFileRepository;

    public ExcelSheet execute(LocalDate startDate, LocalDate endDate) {
        List<List<Object>> rows = new ArrayList<>();
        for (int page = 0; ; page++) {
            List<AnimalObservation> observations = animalObservationRepository.findByCreatedAtBetween(
                startDate.atStartOfDay(), endDate.atTime(23, 59, 59, 999_999_000),
                PageRequest.of(page, PAGE_SIZE, Sort.by("createdAt", "id")));
            Map<Long, List<File>> files = animalObservationFileRepository
                .findAllByAnimalObservationIdIn(observations.stream().map(AnimalObservation::getId).toList()).stream()
                .collect(Collectors.groupingBy(f -> f.getAnimalObservation().getId(),
                    Collectors.mapping(f -> f.getFile(), Collectors.toList())));
            for (AnimalObservation o : observations) {
                rows.add(Arrays.asList(o.getCreatedAt(), animalName(o.getAnimalManage()), o.getTitle(), o.getContent(),
                    o.getAuthor().getName(), files.containsKey(o.getId()) ? new ExcelFiles(files.get(o.getId())) : null));
            }
            if (rows.size() > MAX_ROWS) {
                throw new ToyVillageException(ErrorCode.EXCEL_EXPORT_TOO_LARGE);
            }
            if (observations.size() < PAGE_SIZE) {
                return new ExcelSheet(HEADERS, rows);
            }
        }
    }

    private String animalName(AnimalManage animal) {
        return animal.isDeleteYn() ? animal.getAnimalName() + " (삭제됨)" : animal.getAnimalName();
    }
}
