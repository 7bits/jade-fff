package com.recruiters.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class Dashboard {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String routeByRole(final HttpServletRequest request, final HttpServletResponse response) {
        if (request.isUserInRole("ROLE_RECRUITER")) {
            return "redirect:recruiter-my-vacancies";
        }
        if (request.isUserInRole("ROLE_EMPLOYER")) {
            return "redirect:recruiter-my-vacancies";
        }

        return "redirect:login-page";
    }
}