package com.recruiters.web.controller.employer;

import com.recruiters.model.Applicant;
import com.recruiters.model.Bid;
import com.recruiters.model.Deal;
import com.recruiters.model.Employer;
import com.recruiters.model.User;
import com.recruiters.service.EmployerService;
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
 * Show control panel for Employer
 */
@Controller
public class EmployerControlPanel {

    /** Employer Service provides all Employer related methods */
    @Autowired
    private EmployerService employerService = null;
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
    @RequestMapping(value = "/employer-control-panel")
    public ModelAndView showControlPanel(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {

        ModelAndView controlPanel = new ModelAndView("employer/employer-dashboard.jade");
        try {
            User user = userUtils.getCurrentUser(request);
            Employer employer = employerService.findEmployer(user.getEmployerId());
            controlPanel.addObject("employer", employer);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }

        return controlPanel;
    }

    /**
     * Show deals employer to need leave feedback for
     * @param request     Http Request
     * @param response    Http Response
     * @return list of deals
     * Internal Server Error page if something is wrong with
     * saving data due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown     */
    @RequestMapping(value = "/employer-control-panel-feedback.json", method = RequestMethod.GET)
    public List<Map<String,String>> showDealsForFeedback(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        List<Deal> deals;
        try {
            User user = userUtils.getCurrentUser(request);
            deals = employerService.findDealsForFeedback(user.getEmployerId());
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
        Locale locale = RequestContextUtils.getLocale(request);
        return jsonService.employerDealsForFeedback(deals, locale);
    }

    /**
     * Show new applicants employer have not seen
     * @param request     Http Request
     * @param response    Http Response
     * @return list of deals
     * Internal Server Error page if something is wrong with
     * saving data due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown     */
    @RequestMapping(value = "/employer-control-panel-applicants.json", method = RequestMethod.GET)
    public List<Map<String,String>> showNewApplicants(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        List<Applicant> applicants;
        try {
            User user = userUtils.getCurrentUser(request);
            applicants = employerService.findNewApplicants(user.getEmployerId());
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
        Locale locale = RequestContextUtils.getLocale(request);
        return jsonService.employerNewApplicants(applicants, locale);
    }


    /**
     * Show new bids employer have not seen
     * @param request     Http Request
     * @param response    Http Response
     * @return list of bids
     * Internal Server Error page if something is wrong with
     * saving data due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown     */
    @RequestMapping(value = "/employer-control-panel-bids.json", method = RequestMethod.GET)
    public List<Map<String,String>> showNewBids(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        List<Bid> bids;
        try {
            User user = userUtils.getCurrentUser(request);
            bids = employerService.findNewBids(user.getEmployerId());
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
        Locale locale = RequestContextUtils.getLocale(request);
        return jsonService.employerNewBids(bids, locale);
    }


    public EmployerService getEmployerService() {
        return employerService;
    }

    public void setEmployerService(final EmployerService employerService) {
        this.employerService = employerService;
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
