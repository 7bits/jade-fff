package com.recruiters.web.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * User related helper
 */
public final class UserResolver {

    /**
     * Provide User role
     * @return user role
     */
    public String getUserRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return ((GrantedAuthority) (auth.getAuthorities().toArray()[0])).getAuthority();
    }

    /**
     * Check if current user is in certain role
     * @param roleName    Role to compare with
     * @return true if current user is in the same role, otherwise false
     */
    public Boolean isUserInRole(final String roleName) {

        return this.getUserRole().equals(roleName);
    }
}
