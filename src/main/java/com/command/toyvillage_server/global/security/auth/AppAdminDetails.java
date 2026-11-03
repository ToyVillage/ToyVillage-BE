package com.command.toyvillage_server.global.security.auth;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdminRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record AppAdminDetails(AppAdmin appAdmin) implements UserDetails {

    @Override
    public String getUsername() {
        return appAdmin.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + appAdmin.getRole().name()));
    }

    @Override
    public String getPassword() {
        return appAdmin.getPassword();
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
        return appAdmin.getId();
    }

    public boolean isAdmin() {
        return appAdmin.getRole() == AppAdminRole.APP_ADMIN;
    }
}
