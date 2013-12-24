package com.recruiters.web.controller.recruiter;

import com.recruiters.model.Recruiter;
import com.recruiters.model.User;
import com.recruiters.service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller Class for R12 "My vacancies"
 */
@Controller
public class MyVacancies {

    @Autowired
    private RecruiterService recruiterService = null;
    /**
     * Controller for R2 "My vacancies"
     * @return model and view with list of active vacancies
     */
    @RequestMapping(value = "recruiter-vacancies-list", method = RequestMethod.GET)
    public ModelAndView showMyVacancies(final HttpServletRequest request) {
        ModelAndView myVacancies = new ModelAndView("recruiter-my-vacancies.jade");
        User currentUser = this.getRecruiterService().getCurrentUser(request);
        Recruiter recruiter = this.getRecruiterService().findRecruiterByUserId(currentUser.getId());
        if (recruiter != null) {
            myVacancies.addObject("vacancyList", getRecruiterService().findListOfInProgressVacanciesForRecruiter(recruiter.getId()));
        }

        return myVacancies;
    }

    public RecruiterService getRecruiterService() {
        return recruiterService;
    }

    public void setRecruiterService(final RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }
}
