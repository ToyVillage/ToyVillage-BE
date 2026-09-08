package com.command.toyvillage_server.domain.app.work_log.service.work_log;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.auth.admin.domain.repository.AppAdminRepository;
import com.command.toyvillage_server.domain.app.auth.admin.exception.AppAdminNotFoundException;
import com.command.toyvillage_server.domain.app.work_log.domain.WorkLog;
import com.command.toyvillage_server.domain.app.work_log.domain.WorkLogAnswer;
import com.command.toyvillage_server.domain.app.work_log.domain.WorkLogTemplate;
import com.command.toyvillage_server.domain.app.work_log.domain.repository.WorkLogRepository;
import com.command.toyvillage_server.domain.app.work_log.domain.repository.WorkLogTemplateRepository;
import com.command.toyvillage_server.domain.app.work_log.exception.WorkLogTemplateNotFoundException;
import com.command.toyvillage_server.domain.app.work_log.presentation.dto.request.WorkLogAnswerRequest;
import com.command.toyvillage_server.domain.app.work_log.presentation.dto.request.WorkLogWriteRequest;
import com.command.toyvillage_server.domain.web.file.domain.File;
import com.command.toyvillage_server.domain.web.file.domain.repository.FileRepository;
import com.command.toyvillage_server.domain.web.file.exception.FileNotFoundException;
import com.command.toyvillage_server.global.security.auth.AppAdminDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkLogEmployeeWriteService {
    private final WorkLogRepository workLogRepository;
    private final WorkLogTemplateRepository workLogTemplateRepository;
    private final AppAdminRepository appAdminRepository;
    private final FileRepository fileRepository;

    @Transactional
    public void execute(Long workLogTemplateId, WorkLogWriteRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof AppAdminDetails appAdminDetails)) {
            throw AppAdminNotFoundException.EXCEPTION;
        }

        AppAdmin appAdmin = appAdminRepository.findById(appAdminDetails.getId())
            .orElseThrow(() -> AppAdminNotFoundException.EXCEPTION);

        WorkLogTemplate template = workLogTemplateRepository.findByIdAndDeleteYnFalse(workLogTemplateId)
            .orElseThrow(() -> WorkLogTemplateNotFoundException.EXCEPTION);

        List<WorkLogAnswer> answers = request.answers().stream()
            .map(answer -> createAnswer(template, answer))
            .toList();

        template.validateRequiredAnswered(answers);

        WorkLog workLog = WorkLog.builder()
            .template(template)
            .appAdmin(appAdmin)
            .build();
        answers.forEach(workLog::addAnswer);

        workLogRepository.save(workLog);
    }

    private WorkLogAnswer createAnswer(WorkLogTemplate template, WorkLogAnswerRequest request) {
        WorkLogAnswer answer = template.createAnswer(
            request.sectionId(),
            request.questionId(),
            request.answerText(),
            findFile(request.fileKey())
        );

        request.options().forEach(option -> answer.selectOption(option.optionId(), option.etcText()));

        return answer;
    }

    private File findFile(String fileKey) {
        if (fileKey == null || fileKey.isBlank()) {
            return null;
        }

        return fileRepository.findByFileKey(fileKey)
            .orElseThrow(() -> FileNotFoundException.EXCEPTION);
    }
}
