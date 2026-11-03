package com.command.toyvillage_server.domain.web.partnership.presentation.dto.response;

import com.command.toyvillage_server.domain.web.partnership.domain.Partnership;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PartnershipQueryResponse {
    private Long id;
    private String title;
    private LocalDateTime createdAt;

    public static PartnershipQueryResponse from(Partnership partnership) {
        return new PartnershipQueryResponse(
                partnership.getId(),
                partnership.getTitle(),
                partnership.getCreatedDate()
        );
    }
}

