package com.recruiters.web.controller.recruiter;

import com.recruiters.service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller Class for R11 "Show active vacancies"
 */
@Controller
public class AvailableVacancies {

    @Autowired
    private RecruiterService recruiterService = null;

    /**
     * Controller for R11 "Active vacancies list"
     * @return model and view with list of active vacancies
     */
    @RequestMapping(value = "recruiter-available-vacancies", method = RequestMethod.GET)
    public ModelAndView showActiveVacancies() {
        ModelAndView activeVacancies = new ModelAndView("recruiter-available-vacancies.jade");
        activeVacancies.addObject("vacancyList", getRecruiterService().findListOfAvailableVacancies());

        return activeVacancies;
    }

    public RecruiterService getRecruiterService() {
        return recruiterService;
    }

    public void setRecruiterService(final RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }
}