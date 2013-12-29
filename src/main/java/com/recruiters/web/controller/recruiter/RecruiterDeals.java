package com.recruiters.web.controller.recruiter;

import com.recruiters.service.RecruiterService;
import com.recruiters.web.controller.utils.UserUtils;
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
    private UserUtils userUtils = null;
    @Autowired
    private RecruiterService recruiterService = null;
    /**
     * Controller for "in progress vacancies"
     * @return model and view with list of active vacancies
     */
    @RequestMapping(value = "recruiter-active-deals", method = RequestMethod.GET)
    public ModelAndView showMyVacancies(final HttpServletRequest request) {
        ModelAndView activeDeals = new ModelAndView("recruiter/recruiter-active-deals.jade");
        Long userId = userUtils.getCurrentUserId(request);
        Long recruiterId = this.getRecruiterService().findRecruiterIdByUserId(userId);
        if (recruiterId != null) {
            activeDeals.addObject("deals", getRecruiterService().findActiveDealsForRecruiter(recruiterId));
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
