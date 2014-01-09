package com.recruiters.web.controller.recruiter;

import com.recruiters.model.User;
import com.recruiters.service.RecruiterService;
import com.recruiters.web.controller.utils.UserUtils;
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
public class RecruiterBids {

    @Autowired
    private UserUtils userUtils = null;
    @Autowired
    private RecruiterService recruiterService = null;
    /**
     * Controller for R2 "My vacancies"
     * @return model and view with list of active vacancies
     */
    @RequestMapping(value = "recruiter-active-bids", method = RequestMethod.GET)
    public ModelAndView showRecruiterActiveBids(final HttpServletRequest request) {
        ModelAndView recruiterBids = new ModelAndView("recruiter/recruiter-active-bids.jade");
        User user = userUtils.getCurrentUser(request);
        if (user.getRecruiterId() != null) {
            recruiterBids.addObject("bids", getRecruiterService().findBidsForRecruiter(user.getRecruiterId()));
        }

        return recruiterBids;
    }

    public RecruiterService getRecruiterService() {
        return recruiterService;
    }

    public void setRecruiterService(final RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }
}
