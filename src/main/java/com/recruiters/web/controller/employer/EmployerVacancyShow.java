package com.recruiters.web.controller.employer;

import com.recruiters.model.Deal;
import com.recruiters.model.Employer;
import com.recruiters.model.User;
import com.recruiters.service.EmployerService;
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
 * Controller for C70 "Show progress of vacancy "
 */
@Controller
public class EmployerVacancyShow {

    @Autowired
    private EmployerService employerService = null;

    /**
     * Page controller for C70 "Show progress of vacancy"
     * @param dealId Id of corresponding deal
     * @param request    Http request
     * @return model and view with single deal
     */
    @RequestMapping(value = "employer-progress-vacancy-show/{dealId}", method = RequestMethod.GET)
    public ModelAndView showVacancyProgressForEmployer(@PathVariable final Long dealId,
                                                       final HttpServletRequest request) {

        ModelAndView vacancyProgress =  new ModelAndView("employer/employer-progress-vacancy-show.jade");
        User currentUser = employerService.getCurrentUser(request);
        Employer employer = employerService.findEmployerByUserId(currentUser.getId());

        if (employer != null) {
            Deal deal = employerService.getDealById(dealId, employer);
            vacancyProgress.addObject("deal", deal);
        }
        return vacancyProgress;
    }


    /*TODO:: replace this and add make real*/

    @RequestMapping(value = "employer-vacancy-create", method = RequestMethod.GET)
    public ModelAndView createVacancyForEmployer() {

        return new ModelAndView("employer/vacancy-create.jade");
    }
    @RequestMapping(value = "employer-profile", method = RequestMethod.GET)
    public ModelAndView employerProfile() {

        return new ModelAndView("employer/profile.jade");
    }
    /*************************************/


    public EmployerService getEmployerService() {
        return employerService;
    }

    public void setEmployerService(final EmployerService employerService) {
        this.employerService = employerService;
    }
}
