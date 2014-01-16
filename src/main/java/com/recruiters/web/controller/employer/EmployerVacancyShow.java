package com.recruiters.web.controller.employer;

import com.recruiters.model.Deal;
import com.recruiters.model.User;
import com.recruiters.service.*;
import com.recruiters.service.SecurityException;
import com.recruiters.web.controller.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        } catch (SecurityException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

        return vacancyProgress;
    }

    /**
     * action for fire recruiter by employer
     * @param dealId Id of corresponding deal
     * @param request    Http request
     * @return model and view with single deal
     */
    @RequestMapping(value = "employer-fire-recruiter/{dealId}", method = RequestMethod.GET)
    public String fireRecruiter(
            @PathVariable final Long dealId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        try {
            User user = userUtils.getCurrentUser(request);
            employerService.fireRecruiter(dealId, user.getEmployerId());
        } catch (SecurityException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return "redirect:/";
    }


    public EmployerService getEmployerService() {
        return employerService;
    }

    public void setEmployerService(final EmployerService employerService) {
        this.employerService = employerService;
    }
}
