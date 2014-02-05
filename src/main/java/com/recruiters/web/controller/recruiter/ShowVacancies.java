package com.recruiters.web.controller.recruiter;

import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.service.RecruiterService;
import com.recruiters.service.exception.ServiceException;
import com.recruiters.service.utils.DateTimeUtils;
import com.recruiters.web.controller.utils.UserUtils;
import com.recruiters.web.form.VacanciesFilter;
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
    /** Url Builder */
    @Autowired
    private UrlResolver urlResolver;
    /** Session attribute name for Vacancies Filter */
    private static final String SESSION_FILTER_NAME = VacanciesFilter.class.getName() + ".filter";
    /** Json converter service */
    @Autowired
    private JsonService jsonService;


    /**
     * Shows vacancies with filter applied for recruiter
     * @param request           Http Request
     * @param response          Http Response
     * @param vacanciesFilter   Vacancies Filter
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
            @ModelAttribute("VacanciesFilter") final VacanciesFilter vacanciesFilter
    ) throws Exception {
        ModelAndView filteredVacancies = new ModelAndView("recruiter/recruiter-find-new-vacancies.jade");
        DateTimeUtils dateTimeUtils = new DateTimeUtils();
        Locale locale = RequestContextUtils.getLocale(request);
        // If this is first page visit
        if (vacanciesFilter.getSubmit() == null) {
            fillVacanciesFilter(vacanciesFilter, request);
        }
        addFilterToSession(vacanciesFilter, request);
        filteredVacancies.addObject("vacanciesFilter", vacanciesFilter);

        Date curDate;
        if (vacanciesFilter.getDate().isEmpty()) {
            curDate = new Date();
            vacanciesFilter.setDate(dateTimeUtils.dateUrlFormat(curDate));
        } else {
            curDate = dateTimeUtils.urlDateParse(vacanciesFilter.getDate());
        }

        // If requested date is not today we need next date link
        if (!dateTimeUtils.isToday(curDate)) {
            Date nextDate = dateTimeUtils.nextDay(curDate);
            filteredVacancies.addObject("nextDate", nextDate);
            filteredVacancies.addObject(
                    "nextDateLink",
                    buildFilterLinkByDate(nextDate, vacanciesFilter, locale)
            );
        }

        Date prevDate = dateTimeUtils.previousDay(curDate);
        filteredVacancies.addObject("curDate", curDate);
        filteredVacancies.addObject("prevDate", prevDate);
        filteredVacancies.addObject(
                "prevDateLink",
                buildFilterLinkByDate(prevDate, vacanciesFilter, locale)
        );

        try {
            User user = userUtils.getCurrentUser(request);
            List<Vacancy> vacancies = recruiterService.findFilteredVacanciesForRecruiter(
                    user.getRecruiterId(),
                    vacanciesFilter
            );
            filteredVacancies.addObject("vacancies", vacancies);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }

        return filteredVacancies;
    }
    /**
     * Shows vacancies with filter applied for recruiter
     * @param request           Http Request
     * @param response          Http Response
     * @param vacanciesFilter   Vacancies Filter
     * @return model and view with list of vacancies,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/recruiter-vacancies-filter-ajax.json", method = RequestMethod.POST)
    public List<Map<String,String>> ajaxFilteredVacancies(
            final HttpServletRequest request,
            final HttpServletResponse response,
            @ModelAttribute("VacanciesFilter") final VacanciesFilter vacanciesFilter
    ) throws Exception {
        addFilterToSession(vacanciesFilter, request);
        try {
            User user = userUtils.getCurrentUser(request);
            List<Vacancy> vacancies = recruiterService.findFilteredVacanciesForRecruiter(
                    user.getRecruiterId(),
                    vacanciesFilter
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
     * @param vacanciesFilter    Vacancies Filter
     * @param request            Http Request
     */
    private void fillVacanciesFilter(
            final VacanciesFilter vacanciesFilter,
            final HttpServletRequest request
    ) {
        Object o = request.getSession().getAttribute(SESSION_FILTER_NAME);
        if (o instanceof VacanciesFilter) {
            vacanciesFilter.setHideVacancies(((VacanciesFilter) o).getHideVacancies());
            vacanciesFilter.setHideBids(((VacanciesFilter) o).getHideBids());
            vacanciesFilter.setHideDeals(((VacanciesFilter) o).getHideDeals());
            vacanciesFilter.setDate(((VacanciesFilter) o).getDate());
            vacanciesFilter.setSearchText(((VacanciesFilter) o).getSearchText());
        } else {
            // Default settings
            vacanciesFilter.setHideVacancies(false);
            vacanciesFilter.setHideBids(false);
            vacanciesFilter.setHideDeals(false);
        }
    }

    /**
     * Build link for list of vacancies on exact date
     * with filter with exact locale prefix
     * @param date               Date
     * @param vacanciesFilter    Vacancies Filter
     * @param locale             Locale
     * @return build uri for filtered list of vacancies
     */
    private String buildFilterLinkByDate(
            final Date date,
            final VacanciesFilter vacanciesFilter,
            final Locale locale
    ) {
        DateTimeUtils dateTimeUtils = new DateTimeUtils();
        VacanciesFilter vacanciesFilterNext = new VacanciesFilter(vacanciesFilter);
        vacanciesFilterNext.setDate(dateTimeUtils.dateUrlFormat(date));
        return urlResolver.buildRecruiterFilterUri(vacanciesFilterNext, locale);
    }

    /**
     * Add Filter to Session Attributes
     * @param vacanciesFilter    Vacancies Filter
     * @param request            Http Request
     */
    private void addFilterToSession(
            final VacanciesFilter vacanciesFilter,
            final HttpServletRequest request
    ) {
        request.getSession().setAttribute(SESSION_FILTER_NAME,vacanciesFilter);
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