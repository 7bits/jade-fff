package com.recruiters.web.controller.recruiter;

import com.recruiters.model.Bid;
import com.recruiters.model.User;

import com.recruiters.service.RecruiterService;
import com.recruiters.service.exception.ServiceException;
import com.recruiters.web.controller.utils.UserUtils;
import com.recruiters.web.form.filter.RecruiterBidsFilter;
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
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
    /** Session attribute name for Deals Filter */
    private static final String SESSION_FILTER_NAME = RecruiterBidsFilter.class.getName() + ".filter";
    /** Json converter service */
    @Autowired
    private JsonService jsonService = null;

    /**
     * Displays view with table for recruiter bids
     * @param request        Http Request
     * @param bidsFilter     Session bids filter
     * @return model and view with table for recruiter bids, Internal
     * Server Error page if something is wrong with obtaining data due to
     * technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/recruiter-active-bids", method = RequestMethod.GET)
    public ModelAndView showRecruiterActiveBids(
            final HttpServletRequest request,
            @ModelAttribute("RecruiterBidsFilter") final RecruiterBidsFilter bidsFilter
    )throws Exception {
        ModelAndView recruiterBids = new ModelAndView("recruiter/recruiter-active-bids.jade");
        fillBidsFilter(bidsFilter, request);
        addFilterToSession(bidsFilter, request);
        recruiterBids.addObject("recruiterBidsFilter", bidsFilter);

        return recruiterBids;
    }

    /**
     * Shows bids with filter applied by recruiter
     * @param request           Http Request
     * @param response          Http Response
     * @param bidsFilter        Bids Filter
     * @return json type list of bids,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/recruiter-bids-filter-ajax.json", method = RequestMethod.POST)
    public List<Map<String,String>> ajaxFilteredBids(
            final HttpServletRequest request,
            final HttpServletResponse response,
            @ModelAttribute("RecruiterBidsFilter") final RecruiterBidsFilter bidsFilter
    ) throws Exception {
        addFilterToSession(bidsFilter, request);
        try {
            User user = userUtils.getCurrentUser(request);
            List<Bid> bids = recruiterService.findBidsForRecruiter(
                    user.getRecruiterId(),
                    bidsFilter
            );
            Locale locale = RequestContextUtils.getLocale(request);

            return jsonService.recruiterBidsFilteredList(bids, locale);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
    }


    /**
     * Load default settings for Bids Filter or
     * get settings from session
     * @param bidsFilter     Bids Filter
     * @param request        Http Request
     */
    private void fillBidsFilter(
            final RecruiterBidsFilter bidsFilter,
            final HttpServletRequest request
    ) {
        Object o = request.getSession().getAttribute(SESSION_FILTER_NAME);
        if (o instanceof RecruiterBidsFilter) {
            bidsFilter.setHideApproved(((RecruiterBidsFilter) o).getHideApproved());
            bidsFilter.setHideRejected(((RecruiterBidsFilter) o).getHideRejected());
        } else {
            // Default settings
            bidsFilter.setHideRejected(false);
            bidsFilter.setHideApproved(false);
        }
    }

    /**
     * Add Filter to Session Attributes
     * @param bidsFilter     Bids Filter
     * @param request        Http Request
     */
    private void addFilterToSession(
            final RecruiterBidsFilter bidsFilter,
            final HttpServletRequest request
    ) {
        request.getSession().setAttribute(SESSION_FILTER_NAME, bidsFilter);
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
