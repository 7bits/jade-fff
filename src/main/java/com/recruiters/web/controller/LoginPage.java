package com.recruiters.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginPage {

    @RequestMapping(value = "/login-page", method = RequestMethod.GET)
    public ModelAndView vacanciesSearch() {

        ModelAndView mav = new ModelAndView("login.jade");

        return mav;
    }

//    @ResponseBody
//    @RequestMapping(value = "/employer-progress-vacancies-list")
//    public ModelAndView customerVacanciesList() {
//        return new ModelAndView("employer-progress-vacancies-list");
//    }

    @ResponseBody
    @RequestMapping(value = "/employer-recruiter-search")
    public ModelAndView customerRecruitSearch() {
        return new ModelAndView("employer-recruiter-search");
    }

    @ResponseBody
    @RequestMapping(value = "/employer-recruiter-show")
    public ModelAndView customerRecruitShow() {
        return new ModelAndView("employer-recruiter-show");
    }

    @ResponseBody
    @RequestMapping(value = "/employer-progress-vacancy-show")
    public ModelAndView customerVacancyShow() {
        return new ModelAndView("employer-progress-vacancy-show");
    }

    @ResponseBody
    @RequestMapping(value = "/employer-employee-show")
    public ModelAndView employeeShow() {
        return new ModelAndView("employer-employee-show");
    }

}