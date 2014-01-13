package com.recruiters.web.controller.employer;

import com.recruiters.model.Deal;
import com.recruiters.model.User;
import com.recruiters.service.EmployerService;
import com.recruiters.service.ServiceSecurityException;
import com.recruiters.service.ServiceTechnicalException;
import com.recruiters.web.controller.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {

        ModelAndView vacancyProgress =  new ModelAndView("employer/employer-progress-vacancy-show.jade");
        try {
            User user = userUtils.getCurrentUser(request);
            Deal deal = employerService.findDeal(dealId, user.getEmployerId());
            vacancyProgress.addObject("deal", deal);
        } catch (ServiceTechnicalException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ServiceSecurityException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return vacancyProgress;
    }

    /*TODO:: replace this and add make real*/
    @RequestMapping(value = "employer-vacancy-create", method = RequestMethod.GET)
    public ModelAndView createVacancyForEmployer() {

        return new ModelAndView("employer/vacancy-create.jade");
    }

    public EmployerService getEmployerService() {
        return employerService;
    }

    public void setEmployerService(final EmployerService employerService) {
        this.employerService = employerService;
    }
}
