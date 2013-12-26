package com.recruiters.service.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *  server for support spring security
 */
public final class SecurityService {

    public String getUserRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return ((GrantedAuthority)(auth.getAuthorities().toArray()[0])).getAuthority();
    }

    public Boolean isUserInRole(final String roleName) {

        return this.getUserRole().equals(roleName);
    }
}
