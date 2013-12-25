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
 * Controller Class for "In progress vacancies"
 */
@Controller
public class RecruiterDeals {

    @Autowired
    private RecruiterService recruiterService = null;
    /**
     * Controller for "in progress vacancies"
     * @return model and view with list of active vacancies
     */
    @RequestMapping(value = "recruiter-active-deals", method = RequestMethod.GET)
    public ModelAndView showMyVacancies(final HttpServletRequest request) {
        ModelAndView activeDeals = new ModelAndView("recruiter/recruiter-active-deals.jade");
        User currentUser = this.getRecruiterService().getCurrentUser(request);
        Recruiter recruiter = this.getRecruiterService().findRecruiterByUserId(currentUser.getId());
        if (recruiter != null) {
            activeDeals.addObject("deals", getRecruiterService().findListOfDealsForRecruiterByRecruiterId(recruiter.getId()));
        }

        return activeDeals;
    }

    public RecruiterService getRecruiterService() {
        return recruiterService;
    }

    public void setRecruiterService(final RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }
}
