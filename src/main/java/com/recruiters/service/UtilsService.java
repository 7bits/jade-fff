package com.recruiters.service;

import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;

/**
 */
public abstract class UtilsService {

    static final String ROLE_RECRUITER = "ROLE_RECRUITER";
    static final String ROLE_EMPLOYER = "ROLE_EMPLOYER";
    static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

    public static String getUserRole(final HttpServletRequest request) {
        if (request.isUserInRole(ROLE_EMPLOYER)) {
            return ROLE_EMPLOYER;
        }
        if (request.isUserInRole(ROLE_RECRUITER)) {
            return ROLE_RECRUITER;
        }
        return ROLE_ANONYMOUS;
    }

    public static String getFullUrl(
            final String protocol,
            final String server,
            final String port,
            final String applicationName
    ) {
        String fullPath = protocol + "://" + server;
        if (!StringUtils.isBlank(port)) {
            fullPath += ":" + port;
        }
        if (!StringUtils.isBlank(applicationName)) {
            fullPath += "/" + applicationName;
        }
        return fullPath;
    }
}
