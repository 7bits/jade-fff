package com.recruiters.web.controller.recruiter;

import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.service.RecruiterService;
import com.recruiters.service.ServiceException;
import com.recruiters.web.controller.utils.DateTimeUtils;
import com.recruiters.web.controller.utils.UserUtils;
import com.recruiters.web.form.VacanciesFilter;
import com.recruiters.web.helper.UrlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

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

    /**
     * Shows vacancies with filter applied for recruiter
     * @param request           Http Request
     * @param response          Http Response
     * @param locale            Url locale part
     * @param vacanciesFilter   Vacancies Filter
     * @return model and view with list of vacancies,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/{locale}/recruiter-find-new-vacancies", method = RequestMethod.GET)
    public ModelAndView showFilteredAvailableVacancies(
            final HttpServletRequest request,
            final HttpServletResponse response,
            @PathVariable final String locale,
            @ModelAttribute("VacanciesFilter") final VacanciesFilter vacanciesFilter
    ) throws Exception {
        ModelAndView filteredVacancies = new ModelAndView("recruiter/recruiter-find-new-vacancies.jade");
        DateTimeUtils dateTimeUtils = new DateTimeUtils();
        UrlBuilder urlBuilder = new UrlBuilder(request, locale);
        filteredVacancies.addObject("vacanciesFilter", vacanciesFilter);

        Date curDate;
        if (vacanciesFilter.getDate() == null) {
            curDate = new Date();
            vacanciesFilter.setDate(dateTimeUtils.dateUrlFormat(curDate));
        } else {
            curDate = dateTimeUtils.urlDateParse(vacanciesFilter.getDate());
        }

        // If requested date is not today
        if (!dateTimeUtils.isToday(curDate)) {
            Date nextDate = dateTimeUtils.nextDay(curDate);
            VacanciesFilter vacanciesFilterNext = new VacanciesFilter(vacanciesFilter);
            vacanciesFilterNext.setDate(dateTimeUtils.dateUrlFormat(nextDate));
            String nextDateLink = urlBuilder.recruiterFilterUrl(vacanciesFilterNext);
            filteredVacancies.addObject("nextDate", nextDate);
            filteredVacancies.addObject("nextDateLink", nextDateLink);
        }

        Date prevDate = dateTimeUtils.previousDay(curDate);
        VacanciesFilter vacanciesFilterPrev = new VacanciesFilter(vacanciesFilter);
        vacanciesFilterPrev.setDate(dateTimeUtils.dateUrlFormat(prevDate));
        String prevDateLink = urlBuilder.recruiterFilterUrl(vacanciesFilterPrev);
        filteredVacancies.addObject("curDate", curDate);
        filteredVacancies.addObject("prevDate", prevDate);
        filteredVacancies.addObject("prevDateLink", prevDateLink);

        try {
            User user = userUtils.getCurrentUser(request);
            List<Vacancy> vacancies = recruiterService.findFilteredVacanciesForRecruiter(
                    user.getRecruiterId(),
                    vacanciesFilter.getDate(),
                    vacanciesFilter.getSearchText(),
                    vacanciesFilter.getShowVacancies(),
                    vacanciesFilter.getShowBids(),
                    vacanciesFilter.getShowDeals()
            );
            filteredVacancies.addObject("vacancies", vacancies);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }

        return filteredVacancies;
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