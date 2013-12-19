package com.recruiters.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class LoginPage {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView vacanciesSearch() {

        ModelAndView mav = new ModelAndView("vacancies-search");

        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/vacancy-show")
    public ModelAndView vacancyShow() {
        return new ModelAndView("vacancy-show");
    }

    @ResponseBody
    @RequestMapping(value = "/vacancies-list")
    public ModelAndView vacanciesList() {
        return new ModelAndView("vacancies-list");
    }

    @ResponseBody
    @RequestMapping(value = "/customer-vacancies-list")
    public ModelAndView customerVacanciesList() {
        return new ModelAndView("customer-vacancies-list");
    }
}