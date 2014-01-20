package com.recruiters.web.helper;

import org.springframework.security.web.csrf.CsrfToken;

import javax.servlet.http.HttpServletRequest;

/**
 * CSRF Token Resolver for
 * resolving csrf tokens in templates
 */
public class CsrfResolver {
    private CsrfToken token = null;

    /**
     * Constructor
     * We should have request to get CSRF Token for it
     * @param request    Http Request
     */
    public CsrfResolver(final HttpServletRequest request) {

        token = (CsrfToken) request.getAttribute("_csrf");
    }

    /**
     * Get name of CSRF Token parameter
     * @return name of CSRF Token
     */
    public String getParameterName() {

        return token != null ? token.getParameterName() : null;
    }

    /**
     * Get CSRF Token for current request
     * @return CSRF Token
     */
    public String getToken() {

        return token != null ? token.getToken() : null;
    }

    public void setToken(final CsrfToken token) {
        this.token = token;
    }
}
