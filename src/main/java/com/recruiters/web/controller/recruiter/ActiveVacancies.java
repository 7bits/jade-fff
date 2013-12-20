package com.recruiters.web.controller.recruiter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ActiveVacancies {

    @RequestMapping(value = "/recruiter-my-vacancies", method = RequestMethod.GET)
    public ModelAndView showActiveVacancies() {

        ModelAndView mav = new ModelAndView("recruiter-my-vacancies.jade");

        return mav;
    }
}