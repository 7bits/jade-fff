package com.recruiters.web.controller.recruiter;

import com.recruiters.service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller Class for R11 "Show active vacancies"
 */
@Controller
public class AllAvailableVacancies {

    @Autowired
    private RecruiterService recruiterService = null;

    /**
     * Controller for R11 "Active vacancies list"
     * @return model and view with list of active vacancies
     */
    @RequestMapping(value = "recruiter-find-new-vacancies", method = RequestMethod.GET)
    public ModelAndView showAllAvailableVacancies(final HttpServletRequest request) {
        ModelAndView allAvailableVacancies = new ModelAndView("recruiter/recruiter-find-new-vacancies.jade");
        allAvailableVacancies.addObject("vacancies", getRecruiterService().findListOfAllAvailableVacancies());

        return allAvailableVacancies;
    }

    public RecruiterService getRecruiterService() {
        return recruiterService;
    }

    public void setRecruiterService(final RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }
}