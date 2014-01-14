package com.recruiters.web.controller.recruiter;

import com.recruiters.model.Bid;
import com.recruiters.model.User;
import com.recruiters.service.*;
import com.recruiters.service.SecurityException;
import com.recruiters.web.controller.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    public ModelAndView showRecruiterActiveBids(
            final HttpServletRequest request,
            final HttpServletResponse response
    )throws Exception {
        ModelAndView recruiterBids = new ModelAndView("recruiter/recruiter-active-bids.jade");
        try {
            User user = userUtils.getCurrentUser(request);
            List<Bid> bids = recruiterService.findBidsForRecruiter(user.getRecruiterId());
            recruiterBids.addObject("bids", bids);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
