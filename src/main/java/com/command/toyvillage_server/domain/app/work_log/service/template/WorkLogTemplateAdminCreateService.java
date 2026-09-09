package com.command.toyvillage_server.domain.app.work_log.service.template;

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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkLogTemplateAdminCreateService {
    private final WorkLogTemplateRepository workLogTemplateRepository;

    @Transactional
    public void execute(WorkLogTemplateRequest request) {
        if (workLogTemplateRepository.existsByTemplateTitle(request.templateTitle())) {
            throw WorkLogTemplateAlreadyExistsException.EXCEPTION;
        }

        WorkLogTemplate template = WorkLogTemplate.builder()
            .templateTitle(request.templateTitle())
            .build();

        addSections(template, request.sections());
        addQuestions(template, request.questions());

        workLogTemplateRepository.save(template);
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
                .required(questionRequest.required())
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
