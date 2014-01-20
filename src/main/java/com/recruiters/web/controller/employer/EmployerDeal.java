package com.recruiters.web.controller.employer;

import com.recruiters.model.Deal;
import com.recruiters.model.User;
import com.recruiters.service.*;
import com.recruiters.service.NotAffiliatedException;
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
 * Show Deal for Employer with all corresponding actions
 */
@Controller
public class EmployerDeal {

    /** Employer Service provides all Employer related methods */
    @Autowired
    private EmployerService employerService = null;
    /** User utils for obtaining any session user information */
    @Autowired
    private UserUtils userUtils = null;

    /**
     * Show progress of vacancy (Deal)
     * @param dealId      Id of deal
     * @param request     Http request
     * @param response    Http response
     * @return model and view with deal details,
     * Forbidden page if requested deal does not belong to this employer,
     * Not Found page if there is no deal with such id,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
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
        } catch (NotAffiliatedException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

        return vacancyProgress;
    }

    /**
     * Fire Recruiter for Employer
     * @param dealId      Id of deal
     * @param request     Http request
     * @param response    Http response
     * @return redirect to "vacancies list" page if everything goes fine,
     * Forbidden page if this deal does not belong to this employer,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
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
        } catch (NotAffiliatedException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return "redirect:/employer-progress-vacancies-list";
    }


    public EmployerService getEmployerService() {
        return employerService;
    }

    public void setEmployerService(final EmployerService employerService) {
        this.employerService = employerService;
    }
}
