package com.recruiters.web.controller.employer;

import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.service.*;
import com.recruiters.service.exception.ServiceException;
import com.recruiters.service.utils.DateTimeUtils;
import com.recruiters.web.controller.utils.UserUtils;
import com.recruiters.web.form.EmployerVacanciesFilter;
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
 * Show list of recruiter applications
 */
@Controller
public class RecruiterSearch {

    @Autowired
    private EmployerService employerService = null;
    @Autowired
    private UserUtils userUtils = null;
    /** Session attribute name for Vacancies Filter */
    private static final String SESSION_FILTER_NAME = EmployerVacanciesFilter.class.getName() + ".filter";
    /** Json converter service */
    @Autowired
    private JsonService jsonService;

    /**
     * Show employer vacancies
     * @param request     Http Request
     * @param response    Http Response
     * @param employerVacanciesFilter    Vacancies Filter
     * @return model and view for "My vacancies" employer page,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/employer-recruiter-search")
    public ModelAndView employerVacanciesBids(
            final HttpServletRequest request,
            final HttpServletResponse response,
            @ModelAttribute("EmployerVacanciesFilter") final EmployerVacanciesFilter employerVacanciesFilter
    ) throws Exception {
        ModelAndView filteredVacancies = new ModelAndView("employer/employer-recruiter-search");
        fillVacanciesFilter(employerVacanciesFilter, request);
        addFilterToSession(employerVacanciesFilter, request);
        filteredVacancies.addObject("employerVacanciesFilter", employerVacanciesFilter);

        return filteredVacancies;
    }

    /**
     * Shows vacancies with filter applied by employer
     * @param request           Http Request
     * @param response          Http Response
     * @param employerVacanciesFilter   Vacancies Filter
     * @return json type list of vacancies,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/employer-vacancies-filter-ajax.json", method = RequestMethod.POST)
    public List<Map<String,String>> ajaxFilteredVacancies(
            final HttpServletRequest request,
            final HttpServletResponse response,
            @ModelAttribute("EmployerVacanciesFilter") final EmployerVacanciesFilter employerVacanciesFilter
    ) throws Exception {
        addFilterToSession(employerVacanciesFilter, request);
        try {
            User user = userUtils.getCurrentUser(request);
            List<Vacancy> vacancies = employerService.findFilteredVacanciesForEmployer(
                    user.getEmployerId(),
                    employerVacanciesFilter
            );
            Locale locale = RequestContextUtils.getLocale(request);
            return jsonService.employerVacanciesFilteredList(vacancies, locale);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }

    }

    /**
     * Load default settings for Vacancies Filter or
     * get settings from session
     * @param employerVacanciesFilter    Vacancies Filter
     * @param request            Http Request
     */
    private void fillVacanciesFilter(
            final EmployerVacanciesFilter employerVacanciesFilter,
            final HttpServletRequest request
    ) {
        Object o = request.getSession().getAttribute(SESSION_FILTER_NAME);
        if (o instanceof EmployerVacanciesFilter) {
            employerVacanciesFilter.setHideOldBids(((EmployerVacanciesFilter) o).getHideOldBids());
            employerVacanciesFilter.setHideUnpublished(((EmployerVacanciesFilter) o).getHideUnpublished());
        } else {
            // Default settings
            employerVacanciesFilter.setHideOldBids(false);
            employerVacanciesFilter.setHideUnpublished(false);
        }
    }

    /**
     * Add Filter to Session Attributes
     * @param employerVacanciesFilter    Vacancies Filter
     * @param request            Http Request
     */
    private void addFilterToSession(
            final EmployerVacanciesFilter employerVacanciesFilter,
            final HttpServletRequest request
    ) {
        request.getSession().setAttribute(SESSION_FILTER_NAME, employerVacanciesFilter);
    }

    public EmployerService getEmployerService() {
        return employerService;
    }

    public void setEmployerService(final EmployerService employerService) {
        this.employerService = employerService;
    }
}
