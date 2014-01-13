package com.recruiters.web.controller.employer;

import com.recruiters.model.Deal;
import com.recruiters.model.Employer;
import com.recruiters.model.User;
import com.recruiters.service.EmployerService;
import com.recruiters.service.ServiceGeneralException;
import com.recruiters.service.ServiceSecurityException;
import com.recruiters.service.ServiceTechnicalException;
import com.recruiters.web.controller.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Controller Class for C3 "Vacancies with recruiter"
 */
@Controller
public class RecruiterVacancies {

    @Autowired
    private EmployerService employerService = null;
    @Autowired
    private UserUtils userUtils = null;

    /**
     * Controller method for C3 "Vacancies with recruiter" page
     * @param request  Http request
     * @return model and view with vacancies
     */
    @RequestMapping(value = "employer-progress-vacancies-list", method = RequestMethod.GET)
    public ModelAndView showEmployerDeals(final HttpServletRequest request) {

        ModelAndView myDeals = new ModelAndView("employer/employer-progress-vacancies-list.jade");
        try {
            User user = userUtils.getCurrentUser(request);
//        if (user.getEmployerId() != null) {
            List<Deal> deals = employerService.findDealsForEmployer(user.getEmployerId());
            myDeals.addObject("deals", deals);
//        }
        } catch (ServiceTechnicalException e) {
            // 500
        } catch (ServiceSecurityException e) {
            // 403
        } catch (Exception e) {
            // 500
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
