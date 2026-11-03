package com.command.toyvillage_server.global.swagger;

import com.command.toyvillage_server.global.error.ErrorResponse;
import com.command.toyvillage_server.global.error.exception.ErrorCode;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.GlobalOperationCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 모든 API 문서에 에러 응답을 자동으로 붙인다.
 * - 엔드포인트별 비즈니스 에러: {@link ThrownErrorCodeResolver} 가 바이트코드에서 추출
 * - 공통 에러: 403(인증/인가 실패), 405, 500
 */
@Component
@RequiredArgsConstructor
public class ErrorResponseCustomizer implements GlobalOperationCustomizer {
    private static final String JSON = "application/json";
    private static final String EXAMPLE_TIMESTAMP = "2026-01-01T12:00:00";

    private static final Set<ErrorCode> COMMON_ERROR_CODES = EnumSet.of(
            ErrorCode.METHOD_NOT_ALLOWED,
            ErrorCode.INTERNAL_SERVER_ERROR
    );

    private final ThrownErrorCodeResolver thrownErrorCodeResolver;

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        Set<ErrorCode> errorCodes = EnumSet.noneOf(ErrorCode.class);
        errorCodes.addAll(thrownErrorCodeResolver.resolve(handlerMethod.getMethod()));
        errorCodes.addAll(COMMON_ERROR_CODES);

        Map<Integer, List<ErrorCode>> byStatus = new TreeMap<>();
        for (ErrorCode code : errorCodes) {
            byStatus.computeIfAbsent(code.getStatusCode(), k -> new java.util.ArrayList<>()).add(code);
        }

        ApiResponses responses = operation.getResponses();
        if (responses == null) {
            responses = new ApiResponses();
            operation.setResponses(responses);
        }

        // 403 은 Spring Security 가 본문 없이 내려주므로 설명만 붙인다.
        responses.putIfAbsent(String.valueOf(HttpStatus.FORBIDDEN.value()),
                new ApiResponse().description("인증 토큰이 없거나 유효하지 않음, 또는 접근 권한 없음"));

        for (Map.Entry<Integer, List<ErrorCode>> entry : byStatus.entrySet()) {
            String status = String.valueOf(entry.getKey());
            if (responses.containsKey(status)) {
                continue;
            }
            responses.put(status, buildErrorResponse(entry.getKey(), entry.getValue()));
        }

        // 상태코드 순으로 정렬
        ApiResponses sorted = new ApiResponses();
        responses.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> sorted.put(e.getKey(), e.getValue()));
        operation.setResponses(sorted);

        return operation;
    }

    private ApiResponse buildErrorResponse(int status, List<ErrorCode> codes) {
        Map<String, Example> examples = new LinkedHashMap<>();
        for (ErrorCode code : codes) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("message", code.getErrorMessage());
            body.put("status", code.getStatusCode());
            body.put("timestamp", EXAMPLE_TIMESTAMP);
            body.put("description", code.getErrorMessage());
            examples.put(code.name(), new Example().summary(code.getErrorMessage()).value(body));
        }

        MediaType mediaType = new MediaType()
                .schema(errorResponseSchema())
                .examples(examples);

        HttpStatus httpStatus = HttpStatus.resolve(status);
        String description = httpStatus != null ? httpStatus.getReasonPhrase() : String.valueOf(status);

        return new ApiResponse()
                .description(description)
                .content(new Content().addMediaType(JSON, mediaType));
    }

    private Schema<?> errorResponseSchema() {
        ResolvedSchema resolved = ModelConverters.getInstance()
                .resolveAsResolvedSchema(new AnnotatedType(ErrorResponse.class).resolveAsRef(false));
        return resolved.schema;
    }
}
