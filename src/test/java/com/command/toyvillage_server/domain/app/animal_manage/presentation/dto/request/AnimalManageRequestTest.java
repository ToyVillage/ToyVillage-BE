package com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.request;

import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalGender;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AnimalManageRequestTest {
    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void 출생년도를_생략할_수_없다() {
        Set<ConstraintViolation<AnimalManageRequest>> violations = validator.validate(request(null, null));

        assertThat(violations)
            .extracting(ConstraintViolation::getMessage)
            .contains("출생년도를 입력해주세요.");
    }

    @Test
    void 출생년도는_양수여야_한다() {
        Set<ConstraintViolation<AnimalManageRequest>> violations = validator.validate(request(-1, null));

        assertThat(violations)
            .extracting(ConstraintViolation::getMessage)
            .contains("출생년도는 1 이상이어야 합니다.");
    }

    @Test
    void 출생년도는_현재_연도보다_클_수_없다() {
        Set<ConstraintViolation<AnimalManageRequest>> violations = validator.validate(
            request(Year.now().getValue() + 1, null)
        );

        assertThat(violations)
            .extracting(ConstraintViolation::getMessage)
            .contains("출생년도는 현재 연도보다 클 수 없습니다.");
    }

    @Test
    void 기타정보는_255자를_초과할_수_없다() {
        Set<ConstraintViolation<AnimalManageRequest>> violations = validator.validate(
            request(Year.now().getValue(), "가".repeat(256))
        );

        assertThat(violations)
            .extracting(ConstraintViolation::getMessage)
            .contains("기타정보는 255자 이하여야 합니다.");
    }

    private AnimalManageRequest request(Integer birthYear, String otherInfo) {
        return new AnimalManageRequest(1L, "개체", AnimalGender.MAN, birthYear, otherInfo, "file-key");
    }
}
