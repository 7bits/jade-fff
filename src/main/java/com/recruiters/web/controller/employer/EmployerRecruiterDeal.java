package com.recruiters.web.controller.employer;

import com.recruiters.model.Bid;
import com.recruiters.model.User;
import com.recruiters.service.*;
import com.recruiters.service.SecurityException;
import com.recruiters.web.controller.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller for C5 "Show recruiter info to employer"
 */
@Controller
public class EmployerRecruiterDeal {

    @Autowired
    private EmployerService employerService = null;
    @Autowired
    private UserUtils userUtils = null;

    /**
     * Method for C5 "Show recruiter info to employer page"
     * @param bidId    Id of corresponding bid
     * @param request  Http request
     * @return model and view with data
     */
    @RequestMapping(value = "employer-recruiter-show/{bidId}")
    public ModelAndView employerShowRecruiterBid(
            @PathVariable final Long bidId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        ModelAndView showBid = new ModelAndView("employer/employer-recruiter-show.jade");
        try {
            User user = userUtils.getCurrentUser(request);
            Bid bid = employerService.findBid(bidId, user.getEmployerId());
            showBid.addObject("bid", bid);
        } catch (SecurityException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return showBid;
    }

    @RequestMapping(value = "employer-recruiter-approve/{bidId}", method = RequestMethod.GET)
    public String approveRecruiterBid(
            @PathVariable final Long bidId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        try {
            User user = userUtils.getCurrentUser(request);
            employerService.approveBidForRecruiter(bidId, user.getEmployerId());
        } catch (SecurityException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return "redirect:/employer-recruiter-search";
    }

    @RequestMapping(value = "employer-recruiter-decline/{bidId}", method = RequestMethod.GET)
    public String declineRecruiterBid(
            @PathVariable final Long bidId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        try {
            User user = userUtils.getCurrentUser(request);
            employerService.declineBidForRecruiter(bidId, user.getEmployerId());
        } catch (SecurityException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return "redirect:/employer-recruiter-search";
    }

    public EmployerService getEmployerService() {
        return employerService;
    }

    public void setEmployerService(final EmployerService employerService) {
        this.employerService = employerService;
    }
}
