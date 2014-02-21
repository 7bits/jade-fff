package com.recruiters.web.controller.recruiter;

import com.recruiters.model.Deal;
import com.recruiters.model.User;
import com.recruiters.service.RecruiterService;
import com.recruiters.service.exception.ServiceException;
import com.recruiters.web.controller.utils.UserUtils;
import com.recruiters.web.form.RecruiterDealsFilter;
import com.recruiters.web.helper.UrlResolver;
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
 * Shows deals for recruiter with all corresponding actions
 */
@Controller
public class RecruiterDeals {

    /** User utils for obtaining any session user information */
    @Autowired
    private UserUtils userUtils = null;
    /** Recruiter Service provides all Recruiter related methods */
    @Autowired
    private RecruiterService recruiterService = null;
    /** Url Builder */
    @Autowired
    private UrlResolver urlResolver;
    /** Session attribute name for Deals Filter */
    private static final String SESSION_FILTER_NAME = RecruiterDealsFilter.class.getName() + ".filter";
    /** Json converter service */
    @Autowired
    private JsonService jsonService = null;

    /**
     * Displays all active deals for current recruiter
     * @param request        Http Request
     * @param response       Http Response
     * @return model and view with list of all current recruiter deals,
     * Internal Server Error page if something is wrong with obtaining
     * data due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/recruiter-active-deals", method = RequestMethod.GET)
    public ModelAndView showMyVacancies(
            final HttpServletRequest request,
            final HttpServletResponse response,
            @ModelAttribute("RecruiterDealsFilter") final RecruiterDealsFilter dealsFilter
    ) throws  Exception {
        ModelAndView model = new ModelAndView("recruiter/recruiter-active-deals.jade");
        fillDealsFilter(dealsFilter, request);
        addFilterToSession(dealsFilter, request);
        model.addObject("recruiterDealsFilter", dealsFilter);

        return model;
    }

    /**
     * Shows deals with filter applied by recruiter
     * @param request           Http Request
     * @param response          Http Response
     * @param dealsFilter        Deals Filter
     * @return json type list of deals,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/recruiter-deals-filter-ajax.json", method = RequestMethod.POST)
    public List<Map<String,String>> ajaxFilteredDeals(
            final HttpServletRequest request,
            final HttpServletResponse response,
            @ModelAttribute("RecruiterDealsFilter") final RecruiterDealsFilter dealsFilter
    ) throws Exception {
        addFilterToSession(dealsFilter, request);
        try {
            User user = userUtils.getCurrentUser(request);
            List<Deal> deals = recruiterService.findFilteredDealsForRecruiter(
                    user.getRecruiterId(),
                    dealsFilter
            );
            Locale locale = RequestContextUtils.getLocale(request);

            return jsonService.recruiterDealsFilteredList(deals, locale);
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
            final RecruiterDealsFilter dealsFilter,
            final HttpServletRequest request
    ) {
        Object o = request.getSession().getAttribute(SESSION_FILTER_NAME);
        if (o instanceof RecruiterDealsFilter) {
            dealsFilter.setHideApproved(((RecruiterDealsFilter) o).getHideApproved());
            dealsFilter.setHideFired(((RecruiterDealsFilter) o).getHideFired());
        } else {
            // Default settings
            dealsFilter.setHideFired(false);
            dealsFilter.setHideApproved(false);
        }
    }

    /**
     * Add Filter to Session Attributes
     * @param dealsFilter     Deals Filter
     * @param request         Http Request
     */
    private void addFilterToSession(
            final RecruiterDealsFilter dealsFilter,
            final HttpServletRequest request
    ) {
        request.getSession().setAttribute(SESSION_FILTER_NAME, dealsFilter);
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
