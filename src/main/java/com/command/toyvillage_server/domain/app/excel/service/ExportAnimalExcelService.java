package com.command.toyvillage_server.domain.app.excel.service;

import com.command.toyvillage_server.domain.app.excel.service.dto.ExcelSheet;
import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalKind;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalLegalDesignationRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.global.aws.s3.AwsS3Provider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExportAnimalExcelService {

    private static final int PAGE_SIZE = 200;
    private static final int MAX_ROWS = 1_000;
    private static final List<String> HEADERS = List.of("사진", "개체명", "종", "영문명", "학명", "분류", "세부종", "성별",
        "출생년도", "법적지위", "기타정보");

    private final AnimalManageRepository animalManageRepository;
    private final AnimalLegalDesignationRepository animalLegalDesignationRepository;
    private final AwsS3Provider awsS3Provider;

    public ExcelSheet execute() {
        List<List<Object>> rows = new ArrayList<>();
        for (int page = 0; ; page++) {
            List<AnimalManage> animals = animalManageRepository.findExportPage(PageRequest.of(page, PAGE_SIZE));
            if (rows.size() + animals.size() > MAX_ROWS) {
                throw new ToyVillageException(ErrorCode.EXCEL_EXPORT_TOO_LARGE);
            }
            List<Long> kindIds = animals.stream().map(a -> a.getAnimalKind().getId()).distinct().toList();
            Map<Long, String> legalByKind = animalLegalDesignationRepository.findAllByAnimalKind_IdIn(kindIds).stream()
                .collect(Collectors.groupingBy(d -> d.getAnimalKind().getId(),
                    Collectors.mapping(d -> d.getAnimalLegalStatus().getKind(), Collectors.joining(", "))));
            for (AnimalManage a : animals) {
                AnimalKind kind = a.getAnimalKind();
                byte[] image = a.getAnimalImage() == null ? null : awsS3Provider.download(a.getAnimalImage().getFileKey());
                rows.add(Arrays.asList(image == null ? "이미지 없음" : image, a.getAnimalName(), kind.getKindName(),
                    kind.getEngName(), kind.getScientificName(), kind.getAnimalTaxonomic().getType(), kind.getDetailKind(),
                    a.getAnimalGender().getType(), a.getBirthYear(), legalByKind.getOrDefault(kind.getId(), ""),
                    a.getOtherInfo()));
            }
            if (animals.size() < PAGE_SIZE) {
                return new ExcelSheet(HEADERS, rows);
            }
        }
    }
}
