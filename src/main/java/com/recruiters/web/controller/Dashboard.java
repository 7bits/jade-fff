package com.recruiters.web.controller;

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
        if (request.isUserInRole("ROLE_RECRUITER")) {
            return "redirect:/ru/recruiter-active-deals";
        }
        if (request.isUserInRole("ROLE_EMPLOYER")) {
            return "redirect:/ru/employer-progress-vacancies-list";
        }

        return "redirect:/ru/login-page";
    }

//    @RequestMapping(value = { "/{locale}", "/{locale}/j_*" }, method = RequestMethod.GET)
//    public String routeByRoleWithLocale(
//            final HttpServletRequest request,
//            @PathVariable final String locale
//    ) {
//
//        if (request.isUserInRole("ROLE_RECRUITER")) {
//            return "redirect:/" + locale + "/recruiter-active-deals";
//        }
//        if (request.isUserInRole("ROLE_EMPLOYER")) {
//            return "redirect:/" + locale + "/employer-progress-vacancies-list";
//        }
//
//        return "redirect:/" + locale + "/login-page";
//    }
}