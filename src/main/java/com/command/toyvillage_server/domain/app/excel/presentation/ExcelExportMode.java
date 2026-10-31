package com.command.toyvillage_server.domain.app.excel.presentation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExcelExportMode {
    SHEET("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    ZIP("zip", "application/zip");

    private final String extension;
    private final String contentType;
}
