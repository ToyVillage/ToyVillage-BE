package com.command.toyvillage_server.domain.app.excel.service;

import com.command.toyvillage_server.domain.app.excel.service.dto.ExcelSheet;
import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.join_team.domain.JoinTeam;
import com.command.toyvillage_server.domain.app.join_team.domain.repository.JoinTeamRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportTeamMemberExcelService {

    private static final int PAGE_SIZE = 200;
    private static final int MAX_ROWS = 10_000;

    private final JoinTeamRepository joinTeamRepository;

    public ExcelSheet execute() {
        List<List<Object>> rows = new ArrayList<>();
        for (int page = 0; ; page++) {
            List<JoinTeam> joinTeams = joinTeamRepository.findExportPage(PageRequest.of(page, PAGE_SIZE));
            for (JoinTeam j : joinTeams) {
                AppAdmin a = j.getAppAdmin();
                rows.add(Arrays.asList(j.getTeam().getName(), a.getName(), a.getPosition(), role(a.getRole().name()), a.getCreateAt()));
            }
            if (rows.size() > MAX_ROWS) {
                throw new ToyVillageException(ErrorCode.EXCEL_EXPORT_TOO_LARGE);
            }
            if (joinTeams.size() < PAGE_SIZE) {
                return new ExcelSheet(List.of("팀", "이름", "직책", "권한", "등록일"), rows);
            }
        }
    }

    private String role(String value) {
        return switch (value) {
            case "APP_ADMIN" -> "관리자";
            case "EMPLOYEE" -> "직원";
            default -> value;
        };
    }
}
