package com.recruiters.web.controller.utils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 */
@Controller
public class ErrorViews {

    @RequestMapping(value = "403", method = RequestMethod.GET)
    public ModelAndView errorForbidden() {

        return new ModelAndView("errors/403.jade");
    }

    @RequestMapping(value = "404", method = RequestMethod.GET)
    public ModelAndView errorNotFound() {

        return new ModelAndView("errors/404.jade");
    }

    @RequestMapping(value = "500", method = RequestMethod.GET)
    public ModelAndView errorServerException() {

        return new ModelAndView("errors/500.jade");
    }


}