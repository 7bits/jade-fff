package com.recruiters.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/AddVacancy")
public class LoginPage {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView indexPage() {

        ModelAndView mav = new ModelAndView("main");

        return mav;
    }
}