package com.recruiters.web.controller.recruiter;

import com.recruiters.model.Deal;
import com.recruiters.service.RecruiterService;
import com.recruiters.web.controller.utils.UserUtils;
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
    private UserUtils userUtils = null;
    @Autowired
    private RecruiterService recruiterService = null;

    @RequestMapping(value = "recruiter-show-in-progress-vacancy/{dealId}", method = RequestMethod.GET)
    public ModelAndView showInProgressVacancy(@PathVariable final Long dealId, final HttpServletRequest request) {

        ModelAndView vacancyInProgress = new ModelAndView("recruiter/recruiter-show-in-progress-vacancy.jade");
        Long userId = userUtils.getCurrentUserId(request);
        vacancyInProgress.addObject("deal", this.getRecruiterService().findDealForRecruiter(dealId, userId));

        return vacancyInProgress;
    }

    public RecruiterService getRecruiterService() {
        return recruiterService;
    }

    public void setRecruiterService(final RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }
}
