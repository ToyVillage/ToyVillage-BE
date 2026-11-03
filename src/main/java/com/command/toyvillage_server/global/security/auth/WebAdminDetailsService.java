package com.command.toyvillage_server.global.security.auth;

import com.command.toyvillage_server.domain.web.auth.admin.domain.WebAdmin;
import com.command.toyvillage_server.domain.web.auth.admin.domain.repository.WebAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebAdminDetailsService implements UserDetailsService {
    private final WebAdminRepository webAdminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        WebAdmin webAdmin = webAdminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Web administrator not found"));

        return new WebAdminDetails(webAdmin);
    }
}
