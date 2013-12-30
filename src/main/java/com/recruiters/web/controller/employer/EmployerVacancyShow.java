package com.recruiters.web.controller.employer;

import com.recruiters.model.Deal;
import com.recruiters.model.Employer;
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
 * Controller for C70 "Show progress of vacancy "
 */
@Controller
public class EmployerVacancyShow {

    @Autowired
    private EmployerService employerService = null;
    @Autowired
    private UserUtils userUtils = null;

    /**
     * Page controller for C70 "Show progress of vacancy"
     * @param dealId Id of corresponding deal
     * @param request    Http request
     * @return model and view with single deal
     */
    @RequestMapping(value = "employer-progress-vacancy-show/{dealId}", method = RequestMethod.GET)
    public ModelAndView showVacancyProgressForEmployer(
            @PathVariable final Long dealId,
            final HttpServletRequest request
    ) {

        ModelAndView vacancyProgress =  new ModelAndView("employer/employer-progress-vacancy-show.jade");
        Long userId = userUtils.getCurrentUserId(request);
        vacancyProgress.addObject("deal", employerService.findDealById(dealId, userId));

        return vacancyProgress;
    }


    /*TODO:: replace this and add make real*/

    @RequestMapping(value = "employer-vacancy-create", method = RequestMethod.GET)
    public ModelAndView createVacancyForEmployer() {

        return new ModelAndView("employer/vacancy-create.jade");
    }
    /*************************************/


    public EmployerService getEmployerService() {
        return employerService;
    }

    public void setEmployerService(final EmployerService employerService) {
        this.employerService = employerService;
    }
}
