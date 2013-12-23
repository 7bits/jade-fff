package com.recruiters.service.Utils;

import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;

/**
 *  server for support spring security
 */
public final class SecurityService {

    /** Name of recruiter role*/
    static final String ROLE_RECRUITER = "ROLE_RECRUITER";
    /** Name of employer role*/
    static final String ROLE_EMPLOYER = "ROLE_EMPLOYER";
    /** Name of anonymous role*/
    static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

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
