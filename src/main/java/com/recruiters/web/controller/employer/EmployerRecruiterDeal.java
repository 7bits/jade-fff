package com.recruiters.web.controller.employer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for C5 "Show recruiter info to employer"
 */
@Controller
public class EmployerRecruiterDeal {

    /**
     * Method for C5 "Show recruiter info to employer page"
     * @return model and view with data
     */
    @RequestMapping(value = "employer-recruiter-show")
    public ModelAndView customerRecruitShow() {
        return new ModelAndView("employer-recruiter-show");
    }
}
