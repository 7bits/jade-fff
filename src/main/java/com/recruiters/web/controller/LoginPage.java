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

    @ResponseBody
    @RequestMapping(value = "/vacancy-show")
    public ModelAndView vacancyShow() {
        return new ModelAndView("vacancy-show");
    }

    @ResponseBody
    @RequestMapping(value = "/customer-recruit-search")
    public ModelAndView customerRecruitSearch() {
        return new ModelAndView("customer-recruit-search");
    }

    @ResponseBody
    @RequestMapping(value = "/customer-recruit-show")
    public ModelAndView customerRecruitShow() {
        return new ModelAndView("customer-recruit-show");
    }

    @ResponseBody
    @RequestMapping(value = "/customer-vacancy-show")
    public ModelAndView customerVacancyShow() {
        return new ModelAndView("customer-vacancy-show");
    }

    @ResponseBody
    @RequestMapping(value = "/recruit-vacancy-show")
    public ModelAndView recruitVacancyShow() {
        return new ModelAndView("recruit-vacancy-show");
    }

    @ResponseBody
    @RequestMapping(value = "/employee-create")
    public ModelAndView employeeCreate() {
        return new ModelAndView("employee-create");
    }


    @ResponseBody
    @RequestMapping(value = "/employee-show")
    public ModelAndView employeeShow() {
        return new ModelAndView("employee-show");
    }

}