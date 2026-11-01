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
    WORK_LOG("사육일지", true),
    FEED_LOG("급여일지", true),
    FIFTH_FLOOR_FEED_LOG("5층 급여일지", true),
    ANIMAL_OBSERVATION("관찰일지", true),
    DOCUMENT("자료실", true);

    private final String title;
    private final boolean dated;
}
