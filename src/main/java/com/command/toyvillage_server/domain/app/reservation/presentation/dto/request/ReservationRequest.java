package com.command.toyvillage_server.domain.app.reservation.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ReservationRequest(
    @NotBlank(message = "단체명을 입력해주세요.")
    @Size(max = 50, message = "단체명은 50자 이하여야 합니다.")
    String title,

    @NotBlank(message = "지역을 입력해주세요.")
    @Size(max = 50, message = "지역은 50자 이하여야 합니다.")
    String location,

    @NotNull(message = "상담일을 선택해주세요.")
    LocalDate counselDate,

    @NotBlank(message = "예약인 이름을 입력해주세요.")
    @Size(max = 20, message = "예약인 이름은 20자 이하여야 합니다.")
    String reservationName,

    @NotBlank(message = "대표자 연락처를 입력해주세요.")
    @Size(max = 15, message = "대표자 연락처는 15자 이하여야 합니다.")
    String leaderPhoneNumber,

    @NotNull(message = "총 인원을 입력해주세요.")
    @Positive(message = "총 인원은 1명 이상이어야 합니다.")
    Integer reservationCount,

    @NotNull(message = "인솔자 인원을 입력해주세요.")
    @PositiveOrZero(message = "인솔자 인원은 0명 이상이어야 합니다.")
    Integer leaderCount,

    @NotNull(message = "입장료를 입력해주세요.")
    @PositiveOrZero(message = "입장료는 0원 이상이어야 합니다.")
    Integer money,

    @NotNull(message = "방문일을 선택해주세요.")
    LocalDate visitDate,

    @NotNull(message = "방문 시간을 선택해주세요.")
    @JsonFormat(pattern = "HH:mm")
    LocalTime visitTime,

    @NotNull(message = "퇴장 시간을 선택해주세요.")
    @JsonFormat(pattern = "HH:mm")
    LocalTime exitTime,

    @PositiveOrZero(message = "사전답사 인원은 0명 이상이어야 합니다.")
    Integer visitSiteCount,

    LocalDate visitSiteDate,

    @JsonFormat(pattern = "HH:mm")
    LocalTime visitSiteTime,

    @JsonFormat(pattern = "HH:mm")
    LocalTime visitSiteExitTime,

    List<Long> appAdminIds
) {
    public Integer visitSiteCount() {
        return visitSiteCount == null ? 0 : visitSiteCount;
    }

    public List<Long> appAdminIds() {
        return appAdminIds == null ? List.of() : appAdminIds;
    }
}
