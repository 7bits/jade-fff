package com.recruiters.web.controller.recruiter;

import com.recruiters.model.Bid;
import com.recruiters.model.Deal;
import com.recruiters.model.Recruiter;
import com.recruiters.model.User;
import com.recruiters.service.RecruiterService;
import com.recruiters.service.exception.ServiceException;
import com.recruiters.web.controller.utils.UserUtils;
import com.recruiters.web.service.JsonService;
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
import java.util.Map;

/**
 * Show control panel for Recruiter
 */
@Controller
public class RecruiterControlPanel {

    /** Employer Service provides all Employer related methods */
    @Autowired
    private RecruiterService recruiterService = null;
    /** User utils for obtaining any session user information */
    @Autowired
    private UserUtils userUtils = null;
    /** Json converter service */
    @Autowired
    private JsonService jsonService;

    /**
     * Showing control panel
     * @param request        Http Request
     * @param response       Http Response
     * @return model and view with control panel
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/recruiter-control-panel")
    public ModelAndView showControlPanel(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {

        ModelAndView controlPanel = new ModelAndView("recruiter/recruiter-dashboard.jade");
        try {
            User user = userUtils.getCurrentUser(request);
            Recruiter recruiter = recruiterService.findRecruiter(user.getRecruiterId());
            controlPanel.addObject("recruiter", recruiter);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }

        return controlPanel;
    }

    /**
     * Show deals recruiter to need leave feedback for
     * @param request     Http Request
     * @param response    Http Response
     * @return list of deals
     * Internal Server Error page if something is wrong with
     * saving data due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown     */
    @RequestMapping(value = "/recruiter-control-panel-feedback.json", method = RequestMethod.GET)
    public List<Map<String,String>> showDealsForFeedback(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        List<Deal> deals;
        try {
            User user = userUtils.getCurrentUser(request);
            deals = recruiterService.findDealsForFeedback(user.getRecruiterId());
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
        Locale locale = RequestContextUtils.getLocale(request);
        return jsonService.recruiterDealsForFeedback(deals, locale);
    }

    /**
     * Show new deals
     * @param request     Http Request
     * @param response    Http Response
     * @return list of deals
     * Internal Server Error page if something is wrong with
     * saving data due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown     */
    @RequestMapping(value = "/recruiter-control-panel-deals.json", method = RequestMethod.GET)
    public List<Map<String,String>> showNewDeals(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        List<Deal> deals;
        try {
            User user = userUtils.getCurrentUser(request);
            deals = recruiterService.findNewDeals(user.getRecruiterId());
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
        Locale locale = RequestContextUtils.getLocale(request);
        return jsonService.recruiterNewDeals(deals, locale);
    }

    /**
     * Show latest bids
     * @param request     Http Request
     * @param response    Http Response
     * @return list of bids
     * Internal Server Error page if something is wrong with
     * saving data due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown     */
    @RequestMapping(value = "/recruiter-control-panel-bids.json", method = RequestMethod.GET)
    public List<Map<String,String>> showLastBids(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        List<Bid> bids;
        try {
            User user = userUtils.getCurrentUser(request);
            bids = recruiterService.findLastBids(user.getRecruiterId());
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
        Locale locale = RequestContextUtils.getLocale(request);
        return jsonService.recruiterLastBids(bids, locale);
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

    public JsonService getJsonService() {
        return jsonService;
    }

    public void setJsonService(final JsonService jsonService) {
        this.jsonService = jsonService;
    }
}
