package com.command.toyvillage_server.domain.app.excel.service.dto;

import java.util.List;

public record ExcelSheet(List<String> headers, List<List<Object>> rows) {
}
