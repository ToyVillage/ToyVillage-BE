package com.command.toyvillage_server.domain.web.partnership.presentation.dto.response;

import com.command.toyvillage_server.domain.web.partnership.domain.Partnership;
import com.command.toyvillage_server.domain.web.partnership.domain.PartnershipType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class PartnershipResponse {
    private Long id;

    @JsonProperty("partnership_title")
    private String title;

    @JsonProperty("partnership_email")
    private String email;

    @JsonProperty("partnership_phone_number")
    private String phoneNumber;

    @JsonProperty("partnership_created_at")
    private LocalDateTime createdAt;

    @JsonProperty("partnership_content")
    private String content;

    @JsonProperty("partnership_type")
    private PartnershipType type;

    @JsonProperty("partnership_file_keys")
    private List<String> fileKeys;

    public static PartnershipResponse from(Partnership partnership) {
        return new PartnershipResponse(
                partnership.getId(),
                partnership.getTitle(),
                partnership.getEmail(),
                partnership.getPhoneNumber(),
                partnership.getCreatedDate(),
                partnership.getContent(),
                partnership.getType(),
                partnership.getFiles().stream()
                        .map(file -> file.getFileKey())
                        .toList()
        );
    }
}
