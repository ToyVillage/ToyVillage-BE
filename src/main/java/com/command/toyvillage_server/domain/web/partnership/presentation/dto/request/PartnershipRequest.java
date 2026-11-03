package com.command.toyvillage_server.domain.web.partnership.presentation.dto.request;

import com.command.toyvillage_server.domain.web.partnership.domain.PartnershipType;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartnershipRequest {
    @NotNull(message = "제휴 문의 종류는 공백일 수 없습니다.")
    @JsonProperty("partnership_type")
    @JsonAlias("partnershipType")
    private PartnershipType partnershipType;

    @NotBlank(message = "제휴 제목은 공백일 수 없습니다.")
    @Size(max = 50, message = "제목은 50자를 초과할 수 없습니다.")
    @JsonProperty("partnership_title")
    @JsonAlias("title")
    private String title;

    @NotBlank(message = "이름은 공백일 수 없습니다.")
    @Size(max = 50, message = "이름은 50자를 초과할 수 없습니다.")
    @JsonProperty("partnership_name")
    @JsonAlias("name")
    private String name;

    @Email
    @NotBlank(message = "이메일은 공백일 수 없습니다.")
    @JsonProperty("partnership_email")
    @JsonAlias("email")
    private String email;

    @NotBlank(message = "전화번호는 공백일 수 없습니다.")
    @Size(max = 15, message = "전화번호는 15자를 초과할 수 없습니다.")
    @JsonProperty("partnership_phone_number")
    @JsonAlias("phoneNumber")
    private String phoneNumber;

    @NotBlank(message = "제휴 내용은 공백일 수 없습니다.")
    @JsonProperty("partnership_content")
    @JsonAlias("content")
    private String content;

    @JsonProperty("partnership_file_keys")
    @JsonAlias({"file_keys", "fileKeys"})
    private List<String> fileKeys;
}
