package com.recruiters.service.utils;

import javax.servlet.http.HttpServletRequest;

/**
 *  server for support spring security
 */
public final class SecurityService {

    /** Name of recruiter role*/
    public static final String ROLE_RECRUITER = "ROLE_RECRUITER";
    /** Name of employer role*/
    public static final String ROLE_EMPLOYER = "ROLE_EMPLOYER";
    /** Name of anonymous role*/
    public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

    private SecurityService() {}

    public static String getUserRole(final HttpServletRequest request) {
        if (request.isUserInRole(ROLE_EMPLOYER)) {
            return ROLE_EMPLOYER;
        }
        if (request.isUserInRole(ROLE_RECRUITER)) {
            return ROLE_RECRUITER;
        }
        return ROLE_ANONYMOUS;
    }
}
