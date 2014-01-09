package com.recruiters.web.controller.employer;

import com.recruiters.model.Applicant;
import com.recruiters.model.Employer;
import com.recruiters.model.User;
import com.recruiters.service.EmployerService;
import com.recruiters.web.controller.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller for C71 "View applicant"
 */
@Controller
public class EmployerApplicantView {

    @Autowired
    private EmployerService employerService = null;
    @Autowired
    private UserUtils userUtils = null;

    /**
     * Page controller for C71 "View applicant"
     * @param applicantId    Id of applicant
     * @param request        Http request
     * @return model and view with applicant
     */
    @RequestMapping(value = "employer-applicant-show/{applicantId}", method = RequestMethod.GET)
    public ModelAndView employeeShow(
            @PathVariable final Long applicantId,
            final HttpServletRequest request
    ) {
        ModelAndView showApplicant = new ModelAndView("employer/employer-applicant-show.jade");
        User user = userUtils.getCurrentUser(request);
        if (user.getEmployerId() != null) {
            Applicant applicant = employerService.findApplicant(applicantId, user.getEmployerId());
            showApplicant.addObject("applicant", applicant);
        }

        return showApplicant;
    }

    /**
     * Agree with Applicant offer
     * @param applicantId    Id of applicant
     * @param request        Http request
     * @return model and view with applicant
     */
    @RequestMapping(value = "employer-applicant-show/apply/{applicantId}", method = RequestMethod.GET)
    public String applicantApply(
            @PathVariable final Long applicantId,
            final HttpServletRequest request
    ) {
        User user = userUtils.getCurrentUser(request);
        if (user.getEmployerId() != null) {
            employerService.applyApplicant(applicantId, user.getEmployerId());
        }

        return "redirect:/employer-progress-vacancies-list";
    }


    /**
     * Decline Applicant offer
     * @param applicantId    Id of applicant
     * @param request        Http request
     * @return model and view with applicant
     */
    @RequestMapping(value = "employer-applicant-show/decline/{applicantId}", method = RequestMethod.GET)
    public String applicantDecline(
            @PathVariable final Long applicantId,
            final HttpServletRequest request
    ) {
        User user = userUtils.getCurrentUser(request);
        if (user.getEmployerId() != null) {
            employerService.declineApplicant(applicantId, user.getEmployerId());
            Applicant applicant = employerService.findApplicant(applicantId, user.getEmployerId());
        }

        return "redirect:/employer-progress-vacancy-show";
    }

    public EmployerService getEmployerService() {
        return employerService;
    }

    public void setEmployerService(final EmployerService employerService) {
        this.employerService = employerService;
    }
}
