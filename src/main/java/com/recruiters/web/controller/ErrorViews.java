package com.recruiters.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Error pages
 */
@Controller
public class ErrorViews {

    /**
     * 403 "Forbidden" error page
     * @return model and view with 403 error page
     */
    @RequestMapping(value = "403")
    public ModelAndView errorForbidden() {

        return new ModelAndView("errors/error-403.jade");
    }

    /**
     * 404 "Not Found" error page
     * @return model and view with 404 error page
     */
    @RequestMapping(value = "404")
    public ModelAndView errorNotFound() {

        return new ModelAndView("errors/error-404.jade");
    }

    /**
     * 500 "Internal Server Error" error page
     * @return model and view with 500 error page
     */
    @RequestMapping(value = "500")
    public ModelAndView errorServerException() {

        return new ModelAndView("errors/error-500.jade");
    }
}
