package com.command.toyvillage_server.domain.app.excel.service;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.excel.presentation.ExcelType;
import com.command.toyvillage_server.domain.app.join_team.domain.JoinTeam;
import com.command.toyvillage_server.domain.app.join_team.domain.repository.JoinTeamRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.command.toyvillage_server.domain.app.excel.service.ExcelSheetSupport.*;

@Service
@RequiredArgsConstructor
public class ExportTeamMemberExcelService implements ExcelSheetService {

    private final JoinTeamRepository joinTeamRepository;

    @Override
    public ExcelType type() {
        return ExcelType.TEAM_MEMBER;
    }

    @Override
    public void execute(XSSFSheet sheet, LocalDate startDate, LocalDate endDate) {
        header(sheet, "팀", "이름", "직책", "권한", "등록일");
        forEachPage(page -> joinTeamRepository.findExportPage(PageRequest.of(page, PAGE_SIZE)), joinTeams -> {
            for (JoinTeam j : joinTeams) {
                AppAdmin a = j.getAppAdmin();
                row(sheet, j.getTeam().getName(), a.getName(), a.getPosition(), role(a.getRole().name()), a.getCreateAt());
            }
        });
    }

    private String role(String value) {
        return switch (value) {
            case "APP_ADMIN" -> "관리자";
            case "EMPLOYEE" -> "직원";
            default -> value;
        };
    }
}
