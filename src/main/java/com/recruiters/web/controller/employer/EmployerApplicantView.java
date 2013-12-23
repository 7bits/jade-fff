package com.recruiters.web.controller.employer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for C71 "View applicant"
 */
@Controller
public class EmployerApplicantView {

    /**
     * Page controller for C71 "View applicant"
     * @return model and view with data
     */
    @RequestMapping(value = "employer-employee-show")
    public ModelAndView employeeShow() {
        return new ModelAndView("employer-employee-show");
    }
}
