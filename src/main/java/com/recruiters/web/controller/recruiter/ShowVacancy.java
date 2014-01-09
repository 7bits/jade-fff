package com.recruiters.web.controller.recruiter;


import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.service.RecruiterService;
import com.recruiters.web.controller.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * Controller Class for R2 "Show Vacancy"
 */
@Controller
public class ShowVacancy {

    @Autowired
    private UserUtils userUtils = null;
    @Autowired
    private RecruiterService recruiterService = null;

    /**
     * Controller for R2 "Show vacancy"
     * @return model and view with one vacancy
     */
    @RequestMapping(value = "recruiter-show-vacancy/{vacancyId}", method = RequestMethod.GET)
    public ModelAndView showVacancyById(@PathVariable final Long vacancyId, final HttpServletRequest request) {
        ModelAndView showVacancy = new ModelAndView("recruiter/recruiter-show-vacancy.jade");
        Vacancy vacancy = this.getRecruiterService().findVacancy(vacancyId);
        showVacancy.addObject("vacancy", vacancy);

        return showVacancy;
    }

    /**
     * Controller for R2 "Show vacancy"
     * @return model and view with one vacancy
     */


    @RequestMapping(value = "recruiter-show-vacancy/{vacancyId}", method = RequestMethod.POST)
    public String applyToVacancy(
            @PathVariable final Long vacancyId,
            @RequestParam(value = "message", required = false) final String message,
            final HttpServletRequest request
    ) {
        User user = userUtils.getCurrentUser(request);
        Boolean successApplied = false;
        if (user.getRecruiterId() != null) {
            successApplied = this.getRecruiterService().applyRecruiterToVacancy(user.getRecruiterId(), vacancyId, message);
        }

        return "redirect:/recruiter-find-new-vacancies";
    }


    public RecruiterService getRecruiterService() {
        return recruiterService;
    }

    public void setRecruiterService(final RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }
}
