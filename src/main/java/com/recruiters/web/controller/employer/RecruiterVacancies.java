package com.recruiters.web.controller.employer;

import com.recruiters.model.Deal;
import com.recruiters.model.User;
import com.recruiters.service.*;
import com.recruiters.service.exception.ServiceException;
import com.recruiters.service.utils.DateTimeUtils;
import com.recruiters.web.controller.utils.UserUtils;
import com.recruiters.web.form.EmployerDealsFilter;
import com.recruiters.web.service.JsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Show list of deals
 */
@Controller
public class RecruiterVacancies {

    @Autowired
    private EmployerService employerService = null;
    @Autowired
    private UserUtils userUtils = null;
    /** Session attribute name for Deals Filter */
    private static final String SESSION_FILTER_NAME = EmployerDealsFilter.class.getName() + ".filter";
    /** Json converter service */
    @Autowired
    private JsonService jsonService;

    /**
     * Show deals (vacancies with recruiter)
     * @param request     Http Request
     * @param response    Http Response
     * @param dealsFilter Deals Filter
     * @return model and view with list of deals for current recruiter,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/employer-progress-vacancies-list", method = RequestMethod.GET)
    public ModelAndView showEmployerDeals(
            final HttpServletRequest request,
            final HttpServletResponse response,
            @ModelAttribute("EmployerDealsFilter") final EmployerDealsFilter dealsFilter
    ) throws Exception {

        ModelAndView myDeals = new ModelAndView("employer/employer-progress-vacancies-list.jade");
        fillDealsFilter(dealsFilter, request);
        addFilterToSession(dealsFilter, request);
        myDeals.addObject("employerDealsFilter", dealsFilter);
        return myDeals;
    }

    /**
     * Shows deals with filter applied by employer
     * @param request           Http Request
     * @param response          Http Response
     * @param dealsFilter       Deals Filter
     * @return json type list of deals,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/employer-deals-filter-ajax.json", method = RequestMethod.POST)
    public List<Map<String,String>> ajaxFilteredDeals(
            final HttpServletRequest request,
            final HttpServletResponse response,
            @ModelAttribute("EmployerDealsFilter") final EmployerDealsFilter dealsFilter
    ) throws Exception {
        addFilterToSession(dealsFilter, request);
        try {
            User user = userUtils.getCurrentUser(request);
            List<Deal> deals = employerService.findDealsForEmployer(
                    user.getEmployerId(),
                    dealsFilter
            );
            Locale locale = RequestContextUtils.getLocale(request);
            return jsonService.employerDealsFilteredList(deals, locale);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }

    }


    /**
     * Load default settings for Deals Filter or
     * get settings from session
     * @param dealsFilter    Deals Filter
     * @param request        Http Request
     */
    private void fillDealsFilter(
            final EmployerDealsFilter dealsFilter,
            final HttpServletRequest request
    ) {
        Object o = request.getSession().getAttribute(SESSION_FILTER_NAME);
        if (o instanceof EmployerDealsFilter) {
            dealsFilter.setHideApproved(((EmployerDealsFilter) o).getHideApproved());
            dealsFilter.setHideFired(((EmployerDealsFilter) o).getHideFired());
        } else {
            // Default settings
            dealsFilter.setHideFired(false);
            dealsFilter.setHideApproved(false);
        }
    }

    /**
     * Add Filter to Session Attributes
     * @param dealsFilter    Deals Filter
     * @param request        Http Request
     */
    private void addFilterToSession(
            final EmployerDealsFilter dealsFilter,
            final HttpServletRequest request
    ) {
        request.getSession().setAttribute(SESSION_FILTER_NAME,dealsFilter);
    }

    public EmployerService getEmployerService() {
        return employerService;
    }

    public void setEmployerService(final EmployerService employerService) {
        this.employerService = employerService;
    }
}
