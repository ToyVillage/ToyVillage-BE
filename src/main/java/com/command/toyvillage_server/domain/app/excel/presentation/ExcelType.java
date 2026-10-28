package com.command.toyvillage_server.domain.app.excel.presentation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExcelType {
    NOTICE("공지사항", true),
    TASK("업무지시", true),
    WORK_REPORT("업무보고", true),
    RESERVATION("예약", true),
    TEAM_MEMBER("팀구성원", false),
    ANIMAL("동물", false),
    WORK_LOG("사육일지", true);

    private final String title;
    private final boolean dated;
}
