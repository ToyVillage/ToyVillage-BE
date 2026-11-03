package com.command.toyvillage_server.domain.web.partnership.presentation.dto.response;

import com.command.toyvillage_server.domain.web.partnership.domain.Partnership;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PartnershipQueryAllResponse {
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("received_Date")
    private LocalDateTime createdAt;

    public static PartnershipQueryAllResponse from(Partnership partnership) {
        return new PartnershipQueryAllResponse(
                partnership.getId(),
                partnership.getTitle(),
                partnership.getCreatedDate()
        );
    }
}
