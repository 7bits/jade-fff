package com.recruiters.web.controller.recruiter;

import com.recruiters.model.Applicant;
import com.recruiters.model.Employer;
import com.recruiters.model.Recruiter;
import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.service.RecruiterService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controller Class for R6 "Show vacancy progress"
 */
@Controller
public class ShowVacancyProgress {

    @Autowired
    private RecruiterService recruiterService = null;

    @RequestMapping(value = "recruiter-progress-vacancy-show/{vacancyId}", method = RequestMethod.GET)
    public ModelAndView showVacancyInProgress(@PathVariable final Long vacancyId, final HttpServletRequest request) {

        ModelAndView vacancyInProgress = new ModelAndView("recruit-vacancy-show.jade");
        User currentUser = this.getRecruiterService().getCurrentUser(request);
        Recruiter recruiter = this.getRecruiterService().findRecruiterByUserId(currentUser.getId());
        if (recruiter != null) {
            Vacancy vacancy = this.getRecruiterService().getVacancyInProgressByRecruiterIdAndVacancy(recruiter.getId(), vacancyId);
            vacancyInProgress.addObject("vacancy", vacancy);
            Employer employer = this.getRecruiterService().getEmployerByVacancyId(vacancy.getId());
            vacancyInProgress.addObject("employer", employer);
            List<Applicant> applicantList = this.getRecruiterService().getApplicantListForVacancy(recruiter.getId(), vacancyId);
            vacancyInProgress.addObject("applicantList", applicantList);
        }
        return vacancyInProgress;
    }

    public RecruiterService getRecruiterService() {
        return recruiterService;
    }

    public void setRecruiterService(final RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }
}
