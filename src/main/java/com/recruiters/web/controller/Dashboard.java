package com.recruiters.web.controller;

import com.recruiters.web.helper.UrlResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Map roles to their start pages
 */
@Controller
public class Dashboard {
    @Autowired
    private UrlResolver urlResolver;

    /**
     * Map employer role to employer start page, recruiter role to
     * recruiter start page, otherwise redirect to login page
     * @param request    Http Request
     * @return redirect to recruiter page for recruiter, employer
     * page for employer, otherwise redirect to login page with
     * appropriate locale
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String routeByRole(final HttpServletRequest request
    ) {
        Locale locale = RequestContextUtils.getLocale(request);
        if (request.isUserInRole("ROLE_RECRUITER")) {
            return urlResolver.buildRedirectUri("recruiter-active-deals", locale);
        }
        if (request.isUserInRole("ROLE_EMPLOYER")) {
            return urlResolver.buildRedirectUri("employer-progress-vacancies-list", locale);
        }

        return urlResolver.buildRedirectUri("login-page", locale);
    }
}