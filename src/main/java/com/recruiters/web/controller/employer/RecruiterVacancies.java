package com.recruiters.web.controller.employer;

import com.recruiters.model.Deal;
import com.recruiters.model.User;
import com.recruiters.service.*;
import com.recruiters.web.controller.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Show list of deals
 */
@Controller
public class RecruiterVacancies {

    @Autowired
    private EmployerService employerService = null;
    @Autowired
    private UserUtils userUtils = null;

    /**
     * Show deals (vacancies with recruiter)
     * @param request     Http Request
     * @param response    Http Response
     * @return model and view with list of deals for current recruiter,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/{locale}/employer-progress-vacancies-list", method = RequestMethod.GET)
    public ModelAndView showEmployerDeals(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {

        ModelAndView myDeals = new ModelAndView("employer/employer-progress-vacancies-list.jade");
        try {
            User user = userUtils.getCurrentUser(request);
            List<Deal> deals = employerService.findDealsForEmployer(user.getEmployerId());
            myDeals.addObject("deals", deals);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return myDeals;
    }

    public EmployerService getEmployerService() {
        return employerService;
    }

    public void setEmployerService(final EmployerService employerService) {
        this.employerService = employerService;
    }
}
