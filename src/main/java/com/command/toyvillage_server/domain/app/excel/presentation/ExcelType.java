package com.command.toyvillage_server.domain.app.excel.presentation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExcelType {
    NOTICE("공지사항"),
    TASK("업무지시"),
    WORK_REPORT("업무보고"),
    RESERVATION("예약"),
    TEAM_MEMBER("팀구성원"),
    ANIMAL("동물"),
    WORK_LOG("사육일지");

    private final String title;
}
