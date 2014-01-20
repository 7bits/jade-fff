package com.recruiters.web.controller.employer;

import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.service.*;
import com.recruiters.web.controller.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Controller Class for C4 "Choosing recruiter"
 */
@Controller
public class RecruiterSearch {

    @Autowired
    private EmployerService employerService = null;
    @Autowired
    private UserUtils userUtils = null;

    /**
     * Controller for C4 "Choosing recruiter" displays only
     * vacancies with recruiter offers (bids)
     * @param request    Http request
     * @return model and view with data
     */
    @RequestMapping(value = "employer-recruiter-search")
    public ModelAndView employerVacanciesBids(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {

        ModelAndView vacanciesBids = new ModelAndView("employer/employer-recruiter-search");
        try {
            User user = userUtils.getCurrentUser(request);
            List<Vacancy> vacancies = employerService.findVacanciesForEmployer(user.getEmployerId());
            vacanciesBids.addObject("vacancies", vacancies);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }


        return vacanciesBids;
    }

    public EmployerService getEmployerService() {
        return employerService;
    }

    public void setEmployerService(final EmployerService employerService) {
        this.employerService = employerService;
    }
}
