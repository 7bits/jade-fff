package com.recruiters.web.controller.employer;

import com.recruiters.model.Deal;
import com.recruiters.model.Employer;
import com.recruiters.model.User;
import com.recruiters.service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controller Class for C3 "Vacancies with recruiter"
 */
@Controller
public class RecruiterVacancies {

    @Autowired
    private EmployerService employerService = null;

    /**
     * Controller method for C3 "Vacancies with recruiter" page
     * @param request  Http request
     * @return model and view with vacancies
     */
    @RequestMapping(value = "employer-progress-vacancies-list", method = RequestMethod.GET)
    public ModelAndView showEmployerDeals(final HttpServletRequest request) {

        ModelAndView myDeals = new ModelAndView("employer/employer-progress-vacancies-list.jade");
        User currentUser = employerService.getCurrentUser(request);
        Employer employer = employerService.findEmployerByUserId(currentUser.getId());

        if (employer != null) {
            List<Deal> deals = employerService.findEmployerDeals(employer);
            myDeals.addObject("deals", deals);
        }

        return myDeals;
    }

    public EmployerService getEmployerService() {
        return employerService;
    }

    public void setEmployerService(final EmployerService employerService) {
        this.employerService = employerService;
    }
}
