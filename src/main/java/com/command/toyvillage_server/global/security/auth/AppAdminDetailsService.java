package com.command.toyvillage_server.global.security.auth;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.auth.admin.domain.repository.AppAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppAdminDetailsService implements UserDetailsService {
    private final AppAdminRepository appAdminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        AppAdmin appAdmin = appAdminRepository.findByUsernameAndDeleteStatusFalse(username)
                .orElseThrow(() -> new UsernameNotFoundException("App admin not found"));

        return new AppAdminDetails(appAdmin);
    }
}
