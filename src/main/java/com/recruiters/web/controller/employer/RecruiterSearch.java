package com.recruiters.web.controller.employer;

import com.recruiters.model.Bid;
import com.recruiters.model.Employer;
import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.service.EmployerService;
import com.recruiters.web.form.VacancyWithBidCount;
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
            List<Bid> bids = employerService.findEmployerBids(employer);
            // Populating map <Vacancy id, Count of Recruiter offers>
            Map<Long, Long> bidCount = new HashMap<Long, Long>();
            for (Bid bid: bids) {
                Long vacancyId = bid.getVacancy().getId();
                if (bidCount.get(vacancyId) == null) {
                    bidCount.put(vacancyId, 1L);
                } else {
                    bidCount.put(vacancyId, bidCount.get(vacancyId) + 1L);
                }
            }

            List<Vacancy> vacancies = employerService.findEmployerVacancies(employer);
            // Populating list of VacancyWithBidCount
            List<VacancyWithBidCount> vacanciesWithBids = new ArrayList<VacancyWithBidCount>();
            for (Vacancy vacancy: vacancies) {
                VacancyWithBidCount vacancyWithBidCount = new VacancyWithBidCount();
                vacancyWithBidCount.setVacancy(vacancy);
                if (bidCount.get(vacancy.getId()) != null) {
                    vacancyWithBidCount.setBidCount(bidCount.get(vacancy.getId()));
                }
                vacanciesWithBids.add(vacancyWithBidCount);
            }
            vacanciesBids.addObject("vacancies", vacanciesWithBids);
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
