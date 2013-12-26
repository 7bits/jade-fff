package com.recruiters.web.controller.employer;

import com.recruiters.model.Bid;
import com.recruiters.model.Employer;
import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.service.EmployerService;
import com.recruiters.web.form.VacancyWithBidCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller Class showing bids for exact vacancy
 */
@Controller
public class ShowBidsForVacancy {

    @Autowired
    private EmployerService employerService = null;

    /**
     * Controller showing bids for exact vacancy
     * @param vacancyId  Id of vacancy
     * @param request    Http request
     * @return model and view with data
     */
    @RequestMapping(value = "employer-show-recruiter-bids/{vacancyId}")
    public ModelAndView employerVacanciesBids(@PathVariable final Long vacancyId,
                                              final HttpServletRequest request) {

        ModelAndView recruiterBids = new ModelAndView("employer/employer-show-recruiter-bids.jade");
        User currentUser = employerService.getCurrentUser(request);
        Employer employer = employerService.findEmployerByUserId(currentUser.getId());

        if (employer != null) {
            List<Bid> bids = employerService.findBidsForVacancy(vacancyId, employer);
            Vacancy vacancy = employerService.getVacancyById(vacancyId, employer);
            recruiterBids.addObject("bids", bids);
            recruiterBids.addObject("vacancy", vacancy);
        }

        return recruiterBids;
    }

    public EmployerService getEmployerService() {
        return employerService;
    }

    public void setEmployerService(final EmployerService employerService) {
        this.employerService = employerService;
    }
}
