package com.recruiters.web.controller.recruiter;

import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.service.RecruiterService;
import com.recruiters.service.ServiceException;
import com.recruiters.web.controller.utils.UserUtils;
import com.recruiters.web.form.VacancySearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Show Vacancies for recruiter with all available actions
 */
@Controller
public class AllAvailableVacancies {

    /** Recruiter Service provides all Recruiter related methods */
    @Autowired
    private RecruiterService recruiterService = null;
    /** User utils for obtaining any session user information */
    @Autowired
    private UserUtils userUtils = null;

    /**
     * Shows vacancies (including those with bids and deals) for recruiter
     * @param request    Http Request
     * @param response   Http Response
     * @return model and view with list of vacancies,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/{locale}/recruiter-find-new-vacancies", method = RequestMethod.GET)
    public ModelAndView showAllAvailableVacancies(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        ModelAndView allAvailableVacancies = new ModelAndView("recruiter/recruiter-find-new-vacancies.jade");
        try {
            User user = userUtils.getCurrentUser(request);
            List<Vacancy> vacancies = recruiterService
                    .findFilteredVacanciesForRecruiter(user.getRecruiterId(), null, true, true, true);
            allAvailableVacancies.addObject("vacancies", vacancies);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return allAvailableVacancies;
    }

    /**
     * Shows filtered vacancies for recruiter
     * @param request              Http Request
     * @param response             Http Response
     * @param vacancySearchForm    Search filled out object with search parameters
     * @return model and view with list of vacancies after filter applied,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/{locale}/recruiter-find-new-vacancies", method = RequestMethod.POST)
    public ModelAndView showFilteredVacancies(
            final HttpServletRequest request,
            final HttpServletResponse response,
            @ModelAttribute("vacancySearchForm") final VacancySearchForm vacancySearchForm
    ) throws Exception {
        ModelAndView filteredVacancies = new ModelAndView("recruiter/recruiter-find-new-vacancies.jade");
        filteredVacancies.addObject("vacancySearchForm", vacancySearchForm);
        try {
            User user = userUtils.getCurrentUser(request);
            List<Vacancy> vacancies = recruiterService.findFilteredVacanciesForRecruiter(
                    user.getRecruiterId(),
                    vacancySearchForm.getSearch(),
                    vacancySearchForm.getShowVacancies(),
                    vacancySearchForm.getShowBids(),
                    vacancySearchForm.getShowDeals()
            );
            filteredVacancies.addObject("vacancies", vacancies);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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