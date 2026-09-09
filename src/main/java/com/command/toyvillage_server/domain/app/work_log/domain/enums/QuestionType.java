package com.command.toyvillage_server.domain.app.work_log.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum QuestionType {
    SHORT_TEXT("주관식", false, false),
    LONG_TEXT("장문형", false, false),
    MULTIPLE_CHOICE("객관식", true, false),
    CHECK_BOX("체크박스", true, true),
    FILE_UPLOAD("파일 업로드", false, false);

    private final String description;
    private final boolean optionRequired;
    private final boolean multipleSelectable;

    public boolean isOptionRequired() {
        return optionRequired;
    }

    public boolean isMultipleSelectable() {
        return multipleSelectable;
    }
}
