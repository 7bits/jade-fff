package com.recruiters.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Map roles to their start pages
 */
@Controller
public class Dashboard {

    /**
     * Map employer role to employer start page, recruiter role to
     * recruiter start page, otherwise redirect to login page
     * @param request    Http Request
     * @return redirect to recruiter page for recruiter, employer
     * page for employer, otherwise redirect to login page
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String routeByRole(final HttpServletRequest request) {

        if (request.isUserInRole("ROLE_RECRUITER")) {
            return "redirect:/recruiter-active-deals";
        }
        if (request.isUserInRole("ROLE_EMPLOYER")) {
            return "redirect:/employer-progress-vacancies-list";
        }

        return "redirect:/login-page";
    }
}