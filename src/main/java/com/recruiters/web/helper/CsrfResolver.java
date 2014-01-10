package com.recruiters.web.helper;

import org.springframework.security.web.csrf.CsrfToken;

import javax.servlet.http.HttpServletRequest;

/**
 * CSRF token Resolver
 */
public class CsrfResolver {
    private CsrfToken token = null;

    public CsrfResolver(final HttpServletRequest request) {

        token = (CsrfToken) request.getAttribute("_csrf");
    }

    public String getParameterName() {

        return token != null ? token.getParameterName() : null;
    }

    public String getToken() {

        return token != null ? token.getToken() : null;
    }

    public void setToken(final CsrfToken token) {
        this.token = token;
    }
}
