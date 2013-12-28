package com.recruiters.web.controller.recruiter;


import com.recruiters.model.Recruiter;
import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.service.RecruiterService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


/**
 * Controller Class for R2 "Show Vacancy"
 */
@Controller
public class ShowVacancy {

    @Autowired
    private RecruiterService recruiterService = null;

    /**
     * Controller for R2 "Show vacancy"
     * @return model and view with one vacancy
     */
    @RequestMapping(value = "recruiter-show-vacancy/{vacancyId}", method = RequestMethod.GET)
    public ModelAndView showVacancyById(@PathVariable final Long vacancyId, final HttpServletRequest request) {
        ModelAndView showVacancy = new ModelAndView("recruiter/recruiter-show-vacancy.jade");
        User currentUser = this.getRecruiterService().getCurrentUser(request);
        Recruiter recruiter = this.getRecruiterService().findRecruiterByUserId(currentUser.getId());
        if (recruiter != null) {
            Vacancy vacancy = this.getRecruiterService().getVacancyById(vacancyId);
            showVacancy.addObject("vacancy", vacancy);

        }

        return showVacancy;
    }

    /**
     * Controller for R2 "Show vacancy"
     * @return model and view with one vacancy
     */


    @RequestMapping(value = "recruiter-show-vacancy/{vacancyId}", method = RequestMethod.POST)
    public @ResponseBody Boolean applyToVacancy(
            @PathVariable final Long vacancyId,
            @RequestParam(value = "message", required = false) final String message,
            final HttpServletRequest request
    ) {
        User currentUser = this.getRecruiterService().getCurrentUser(request);
        Recruiter recruiter = this.getRecruiterService().findRecruiterByUserId(currentUser.getId());
        Vacancy vacancy = this.getRecruiterService().getVacancyById(vacancyId);
        Boolean successApplied = false;
        if (recruiter != null) {
            successApplied = this.getRecruiterService().applyRecruiterToVacancy(recruiter, vacancy, message);
        }

        return successApplied;
    }


    public RecruiterService getRecruiterService() {
        return recruiterService;
    }

    public void setRecruiterService(final RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }
}
