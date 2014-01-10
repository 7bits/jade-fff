package com.recruiters.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Dashboard {

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