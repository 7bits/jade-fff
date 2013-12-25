package com.recruiters.web.controller.recruiter;

import com.recruiters.model.Deal;
import com.recruiters.model.Recruiter;
import com.recruiters.model.User;
import com.recruiters.service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller Class for R6 "Show in progress vacancy"
 */
@Controller
public class ShowInProgressVacancy {

    @Autowired
    private RecruiterService recruiterService = null;

    @RequestMapping(value = "recruiter-show-in-progress-vacancy/{dealId}", method = RequestMethod.GET)
    public ModelAndView showInProgressVacancy(@PathVariable final Long dealId, final HttpServletRequest request) {

        ModelAndView vacancyInProgress = new ModelAndView("recruiter/recruiter-show-in-progress-vacancy.jade");
        User currentUser = this.getRecruiterService().getCurrentUser(request);
        Recruiter recruiter = this.getRecruiterService().findRecruiterByUserId(currentUser.getId());
        if (recruiter != null) {
            Deal deal = this.getRecruiterService().getDealById(dealId, recruiter);
            vacancyInProgress.addObject("deal", deal);
        }
        return vacancyInProgress;
    }

    public RecruiterService getRecruiterService() {
        return recruiterService;
    }

    public void setRecruiterService(final RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }
}
