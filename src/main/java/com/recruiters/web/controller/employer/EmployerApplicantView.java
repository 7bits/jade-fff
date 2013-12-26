package com.recruiters.web.controller.employer;

import com.recruiters.model.Applicant;
import com.recruiters.model.Employer;
import com.recruiters.model.User;
import com.recruiters.service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller for C71 "View applicant"
 */
@Controller
public class EmployerApplicantView {

    @Autowired
    private EmployerService employerService = null;

    /**
     * Page controller for C71 "View applicant"
     * @param applicantId    Id of applicant
     * @param request        Http request
     * @return model and view with applicant
     */
    @RequestMapping(value = "employer-employee-show/{applicantId}")
    public ModelAndView employeeShow(@PathVariable final Long applicantId,
                                     final HttpServletRequest request) {

        ModelAndView showApplicant = new ModelAndView("employer/employer-employee-show.jade");
        User currentUser = employerService.getCurrentUser(request);
        Employer employer = employerService.findEmployerByUserId(currentUser.getId());

        if (employer != null) {
            Applicant applicant = employerService.getApplicantById(applicantId, employer);
            showApplicant.addObject("applicant", applicant);
        }
        return showApplicant;
    }

    public EmployerService getEmployerService() {
        return employerService;
    }

    public void setEmployerService(final EmployerService employerService) {
        this.employerService = employerService;
    }
}
