package com.recruiters.web.controller.recruiter;

import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.service.RecruiterService;
import com.recruiters.service.exception.ServiceException;
import com.recruiters.service.utils.DateTimeUtils;
import com.recruiters.web.controller.utils.UserUtils;
import com.recruiters.web.form.filter.RecruiterVacanciesFilter;
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
 * Show Vacancies for recruiter with all available actions
 */
@Controller
public class ShowVacancies {

    /** Recruiter Service provides all Recruiter related methods */
    @Autowired
    private RecruiterService recruiterService = null;
    /** User utils for obtaining any session user information */
    @Autowired
    private UserUtils userUtils = null;
    /** Session attribute name for Vacancies Filter */
    private static final String SESSION_FILTER_NAME = RecruiterVacanciesFilter.class.getName() + ".filter";
    /** Json converter service */
    @Autowired
    private JsonService jsonService;


    /**
     * Shows "filtered vacancies" page
     * @param request           Http Request
     * @param response          Http Response
     * @param recruiterVacanciesFilter   Vacancies Filter
     * @return model and view with list of vacancies,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/recruiter-find-new-vacancies", method = RequestMethod.GET)
    public ModelAndView showFilteredAvailableVacancies(
            final HttpServletRequest request,
            final HttpServletResponse response,
            @ModelAttribute("RecruiterVacanciesFilter") final RecruiterVacanciesFilter recruiterVacanciesFilter
    ) throws Exception {
        ModelAndView filteredVacancies = new ModelAndView("recruiter/recruiter-vacancies-find.jade");
        fillVacanciesFilter(recruiterVacanciesFilter, request);
        addFilterToSession(recruiterVacanciesFilter, request);
        filteredVacancies.addObject("recruiterVacanciesFilter", recruiterVacanciesFilter);

        return filteredVacancies;
    }

    /**
     * Shows vacancies with filter applied by recruiter
     * @param request           Http Request
     * @param response          Http Response
     * @param recruiterVacanciesFilter   Vacancies Filter
     * @return json type list of vacancies,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/recruiter-vacancies-filter-ajax.json", method = RequestMethod.POST)
    public List<Map<String,String>> ajaxFilteredVacancies(
            final HttpServletRequest request,
            final HttpServletResponse response,
            @ModelAttribute("RecruiterVacanciesFilter") final RecruiterVacanciesFilter recruiterVacanciesFilter
    ) throws Exception {
        addFilterToSession(recruiterVacanciesFilter, request);
        try {
            User user = userUtils.getCurrentUser(request);
            List<Vacancy> vacancies = recruiterService.findFilteredVacanciesForRecruiter(
                    user.getRecruiterId(),
                    recruiterVacanciesFilter
            );
            Locale locale = RequestContextUtils.getLocale(request);
            return jsonService.recruiterVacanciesFilteredList(vacancies, locale);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }

    }

    /**
     * Load default settings for Vacancies Filter or
     * get settings from session
     * @param recruiterVacanciesFilter    Vacancies Filter
     * @param request            Http Request
     */
    private void fillVacanciesFilter(
            final RecruiterVacanciesFilter recruiterVacanciesFilter,
            final HttpServletRequest request
    ) {
        Object o = request.getSession().getAttribute(SESSION_FILTER_NAME);
        if (o instanceof RecruiterVacanciesFilter) {
            recruiterVacanciesFilter.setHideVacancies(((RecruiterVacanciesFilter) o).getHideVacancies());
            recruiterVacanciesFilter.setHideBids(((RecruiterVacanciesFilter) o).getHideBids());
            recruiterVacanciesFilter.setHideDeals(((RecruiterVacanciesFilter) o).getHideDeals());
            recruiterVacanciesFilter.setDate(((RecruiterVacanciesFilter) o).getDate());
            recruiterVacanciesFilter.setSearchText(((RecruiterVacanciesFilter) o).getSearchText());
        } else {
            // Default settings
            recruiterVacanciesFilter.setHideVacancies(false);
            recruiterVacanciesFilter.setHideBids(false);
            recruiterVacanciesFilter.setHideDeals(false);
            DateTimeUtils dateTimeUtils = new DateTimeUtils();
            recruiterVacanciesFilter.setDate(dateTimeUtils.dateUrlFormat(new Date()));
        }
    }

    /**
     * Add Filter to Session Attributes
     * @param recruiterVacanciesFilter    Vacancies Filter
     * @param request            Http Request
     */
    private void addFilterToSession(
            final RecruiterVacanciesFilter recruiterVacanciesFilter,
            final HttpServletRequest request
    ) {
        request.getSession().setAttribute(SESSION_FILTER_NAME, recruiterVacanciesFilter);
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