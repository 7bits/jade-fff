package com.recruiters.web.controller.employer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for C70 "Show progress of vacancy "
 */
@Controller
public class EmployerVacancyShow {

    /**
     * Page controller for C70 "Show progress of vacancy"
     * @return model and view with data
     */
    @RequestMapping(value = "employer-progress-vacancy-show")
    public ModelAndView customerVacancyShow() {
        return new ModelAndView("employer-progress-vacancy-show.jade");
    }
}
