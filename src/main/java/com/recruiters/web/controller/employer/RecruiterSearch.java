package com.recruiters.web.controller.employer;

import com.recruiters.model.Bid;
import com.recruiters.model.Employer;
import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller Class for C4 "Choosing recruiter"
 */
@Controller
public class RecruiterSearch {

    @Autowired
    private EmployerService employerService = null;

    /**
     * Controller for C4 "Choosing recruiter" displays only
     * vacancies with recruiter offers (bids)
     * @param request    Http request
     * @return model and view with data
     */
    @RequestMapping(value = "employer-recruiter-search")
    public ModelAndView employerVacanciesBids(final HttpServletRequest request) {

        ModelAndView vacanciesBids = new ModelAndView("employer/employer-recruiter-search");
        User currentUser = employerService.getCurrentUser(request);
        Employer employer = employerService.findEmployerByUserId(currentUser.getId());

        if (employer != null) {
            List<Vacancy> vacancies = employerService.findEmployerVacanciesWithBidCount(employer);
            vacanciesBids.addObject("vacancies", vacancies);
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
