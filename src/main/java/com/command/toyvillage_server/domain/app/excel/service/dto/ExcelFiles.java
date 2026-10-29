package com.command.toyvillage_server.domain.app.excel.service.dto;

import com.command.toyvillage_server.domain.web.file.domain.File;

import java.util.List;

public record ExcelFiles(List<File> files) {
}
