package com.recruiters.web.controller.employer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller Class for C4 "Choosing recruiter"
 */
@Controller
public class RecruiterSearch {

    /**
     * Controller for C4 "Choosing recruiter"
     * @return model and view with data
     */
    @RequestMapping(value = "employer-recruiter-search")
    public ModelAndView customerRecruitSearch() {
        return new ModelAndView("employer-recruiter-search");
    }

}
