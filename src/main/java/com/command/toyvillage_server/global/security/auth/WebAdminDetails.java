package com.command.toyvillage_server.global.security.auth;

import com.command.toyvillage_server.domain.web.auth.admin.domain.WebAdmin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record WebAdminDetails(WebAdmin webAdmin) implements UserDetails {

    @Override
    public String getUsername() {
        return webAdmin.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_WEB_ADMIN"));
    }

    @Override
    public String getPassword() {
        return webAdmin.getPassword();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return webAdmin.getId();
    }
}
