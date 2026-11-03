package com.command.toyvillage_server.domain.app.auth.admin.facade;

import com.command.toyvillage_server.domain.app.auth.admin.exception.AppAdminNotFoundException;
import com.command.toyvillage_server.global.security.auth.AppAdminDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {

    public Long getCurrentUserId() {
        return getCurrentUserDetails().getId();
    }

    public boolean isCurrentUserAppAdmin() {
        return getCurrentUserDetails().appAdmin().isAppAdmin();
    }

    private AppAdminDetails getCurrentUserDetails() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof AppAdminDetails details)) {
            throw AppAdminNotFoundException.EXCEPTION;
        }

        return details;
    }
}
