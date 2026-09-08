package com.command.toyvillage_server.global.error;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void 잘못된_JSON_enum은_400을_반환한다() {
        ResponseEntity<ErrorResponse> response = handler.handleInvalidRequestValueException(
            new HttpMessageNotReadableException("invalid enum", new MockHttpInputMessage(new byte[0]))
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(400);
    }

    @Test
    void 잘못된_쿼리_enum은_400을_반환한다() {
        ResponseEntity<ErrorResponse> response = handler.handleInvalidRequestValueException(
            new MethodArgumentTypeMismatchException("INVALID", Object.class, "animalTaxonomic", null, null)
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(400);
    }
}
