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
 * View bid information and do all bid actions for employer
 */
@Controller
public class EmployerRecruiterDeal {

    /** Employer Service provides all Employer related methods */
    @Autowired
    private EmployerService employerService = null;
    /** User utils for obtaining any session user information */
    @Autowired
    private UserUtils userUtils = null;

    /**
     * Show all bid information for employer
     * @param bidId       Id of bid
     * @param request     Http Request
     * @param response    Http Response
     * @return model and view with bid details,
     * Forbidden page if requested bid does not belong to this employer,
     * Not Found page if there is no bid with such id,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
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
        } catch (NotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

        return showBid;
    }

    /**
     * Approve recruiter application
     * @param bidId       Id of application bid
     * @param request     Http Request
     * @param response    Http Response
     * @return redirect to "recruiter search" page if everything goes fine,
     * Forbidden page if this bid does not belong to this employer,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
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

    /**
     * Decline recruiter application
     * @param bidId       Id of application bid
     * @param request     Http Request
     * @param response    Http Response
     * @return redirect to "recruiter search" page if everything goes fine,
     * Forbidden page if this bid does not belong to this employer,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
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
