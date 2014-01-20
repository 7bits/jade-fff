package com.recruiters.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Login Page
 */
@Controller
public class LoginPage {

    /**
     * Display login page
     * @return model and view with login page
     */
    @RequestMapping(value = "/{locale}/login-page", method = RequestMethod.GET)
    public ModelAndView vacanciesSearch() {

        ModelAndView mav = new ModelAndView("login.jade");

        return mav;
    }
}