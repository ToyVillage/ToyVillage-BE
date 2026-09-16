package com.command.toyvillage_server.domain.app.work_log.service.template;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.auth.admin.domain.repository.AppAdminRepository;
import com.command.toyvillage_server.domain.app.auth.admin.exception.AppAdminNotFoundException;
import com.command.toyvillage_server.domain.app.auth.admin.facade.UserFacade;
import com.command.toyvillage_server.domain.app.work_log.domain.WorkLogQuestionOption;
import com.command.toyvillage_server.domain.app.work_log.domain.WorkLogSection;
import com.command.toyvillage_server.domain.app.work_log.domain.WorkLogTemplate;
import com.command.toyvillage_server.domain.app.work_log.domain.WorkLogQuestion;
import com.command.toyvillage_server.domain.app.work_log.domain.repository.WorkLogTemplateRepository;
import com.command.toyvillage_server.domain.app.work_log.exception.WorkLogEtcOptionDuplicatedException;
import com.command.toyvillage_server.domain.app.work_log.exception.WorkLogOptionRequiredException;
import com.command.toyvillage_server.domain.app.work_log.exception.WorkLogTemplateAlreadyExistsException;
import com.command.toyvillage_server.domain.app.work_log.presentation.dto.request.WorkLogQuestionOptionRequest;
import com.command.toyvillage_server.domain.app.work_log.presentation.dto.request.WorkLogQuestionRequest;
import com.command.toyvillage_server.domain.app.work_log.presentation.dto.request.WorkLogTemplateRequest;
import com.command.toyvillage_server.domain.app.work_log.presentation.dto.response.WorkLogTemplateCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkLogTemplateAdminCreateService {
    private final WorkLogTemplateRepository workLogTemplateRepository;
    private final AppAdminRepository appAdminRepository;
    private final UserFacade userFacade;

    @Transactional
    public WorkLogTemplateCreateResponse execute(WorkLogTemplateRequest request) {
        if (workLogTemplateRepository.existsByTemplateTitle(request.templateTitle())) {
            throw WorkLogTemplateAlreadyExistsException.EXCEPTION;
        }

        AppAdmin appAdmin = appAdminRepository.findById(userFacade.getCurrentUserId())
            .orElseThrow(() -> AppAdminNotFoundException.EXCEPTION);

        WorkLogTemplate template = WorkLogTemplate.builder()
            .templateTitle(request.templateTitle())
            .appAdmin(appAdmin)
            .build();

        addSections(template, request.sections());
        addQuestions(template, request.questions());

        workLogTemplateRepository.save(template);

        return WorkLogTemplateCreateResponse.builder()
            .templateId(template.getId())
            .build();
    }

    private void addSections(WorkLogTemplate template, List<String> sectionNames) {
        for (int order = 0; order < sectionNames.size(); order++) {
            template.addSection(WorkLogSection.builder()
                .sectionName(sectionNames.get(order))
                .sectionOrder(order)
                .build());
        }
    }

    private void addQuestions(WorkLogTemplate template, List<WorkLogQuestionRequest> questions) {
        for (int order = 0; order < questions.size(); order++) {
            WorkLogQuestionRequest questionRequest = questions.get(order);

            WorkLogQuestion question = WorkLogQuestion.builder()
                .question(questionRequest.question())
                .questionType(questionRequest.questionType())
                .questionOrder(order)
                .build();

            addChoices(question, questionRequest);

            template.addQuestion(question);
        }
    }

    private void addChoices(WorkLogQuestion question, WorkLogQuestionRequest request) {
        List<WorkLogQuestionOptionRequest> options = request.options();

        if (request.questionType().isOptionRequired() && options.isEmpty()) {
            throw WorkLogOptionRequiredException.EXCEPTION;
        }

        if (options.stream().filter(WorkLogQuestionOptionRequest::etcOption).count() > 1) {
            throw WorkLogEtcOptionDuplicatedException.EXCEPTION;
        }

        for (int number = 0; number < options.size(); number++) {
            question.addOption(WorkLogQuestionOption.builder()
                .number(number)
                .content(options.get(number).content())
                .etcOption(options.get(number).etcOption())
                .build());
        }
    }
}
