package com.recruiters.web.controller.recruiter;

import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.service.RecruiterService;
import com.recruiters.service.ServiceException;
import com.recruiters.web.controller.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Controller Class for R11 "Show active vacancies"
 * Should show only vacancies without bids and deals
 */
@Controller
public class AllAvailableVacancies {

    /** Recruiter Service provides all Recruiter related methods */
    @Autowired
    private RecruiterService recruiterService = null;
    /** User utils for obtaining any user information */
    @Autowired
    private UserUtils userUtils = null;

    /**
     * Controller for R11 "Active vacancies list"
     * @param request    Http Request
     * @param response   Http Response
     * @return model and view with list of vacancies available for bidding,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "recruiter-find-new-vacancies", method = RequestMethod.GET)
    public ModelAndView showAllAvailableVacancies(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        ModelAndView allAvailableVacancies = new ModelAndView("recruiter/recruiter-find-new-vacancies.jade");
        try {
            User user = userUtils.getCurrentUser(request);
            List<Vacancy> vacancies = recruiterService
                    .findAvailableVacanciesForRecruiter(user.getRecruiterId());
            allAvailableVacancies.addObject("vacancies", vacancies);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return allAvailableVacancies;
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