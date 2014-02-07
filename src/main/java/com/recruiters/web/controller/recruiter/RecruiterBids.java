package com.recruiters.web.controller.recruiter;

import com.recruiters.model.Bid;
import com.recruiters.model.User;

import com.recruiters.service.RecruiterService;
import com.recruiters.service.exception.ServiceException;
import com.recruiters.web.controller.utils.UserUtils;
import com.recruiters.web.helper.UrlResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;

/**
 * Bid views for recruiter
 */
@Controller
public class RecruiterBids {

    /** User utils for obtaining any session user information */
    @Autowired
    private UserUtils userUtils = null;
    /** Recruiter Service provides all Recruiter related methods */
    @Autowired
    private RecruiterService recruiterService = null;
    /** Url Builder */
    @Autowired
    private UrlResolver urlResolver;

    /**
     * Displays all bids of current recruiter
     * @param request        Http Request
     * @param response       Http Response
     * @return model and view with list of current recruiter bids, Internal
     * Server Error page if something is wrong with obtaining data due to
     * technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/recruiter-active-bids", method = RequestMethod.GET)
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
            return null;
        }

        return recruiterBids;
    }

    /**
     * Clear rejected bids for current recruiter
     * @param request        Http Request
     * @param response       Http Response
     * @return redirect to list of all active bids for current recruiter,
     * Internal Server Error page if something is wrong with performing
     * command due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/recruiter-clear-rejected-bids", method = RequestMethod.GET)
    public String clearRejectedBids(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws  Exception {
        try {
            User user = userUtils.getCurrentUser(request);
            recruiterService.clearRejectedBidsForRecruiter(user.getRecruiterId());
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
        Locale locale = RequestContextUtils.getLocale(request);

        return  urlResolver.buildRedirectUri("recruiter-active-bids", locale);
    }

    /**
     * Clear approved bids for current recruiter
     * @param request        Http Request
     * @param response       Http Response
     * @return redirect to list of all active bids for current recruiter,
     * Internal Server Error page if something is wrong with performing
     * command due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/recruiter-clear-approved-bids", method = RequestMethod.GET)
    public String clearApprovedDeals(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws  Exception {
        try {
            User user = userUtils.getCurrentUser(request);
            recruiterService.clearApprovedBidsForRecruiter(user.getRecruiterId());
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
        Locale locale = RequestContextUtils.getLocale(request);

        return  urlResolver.buildRedirectUri("recruiter-active-bids", locale);
    }

    public RecruiterService getRecruiterService() {
        return recruiterService;
    }

    public void setRecruiterService(final RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }

    public UserUtils getUserUtils() {
        return userUtils;
    }

    public void setUserUtils(final UserUtils userUtils) {
        this.userUtils = userUtils;
    }
}
