package com.recruiters.web.controller.recruiter;

import com.recruiters.model.Employer;
import com.recruiters.service.RecruiterService;
import com.recruiters.service.exception.NotFoundException;
import com.recruiters.service.exception.ServiceException;
import com.recruiters.web.controller.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Show employer profile for Recruiter
 */
@Controller
public class RecruiterShowEmployerProfile {

    /** Recruiter Service provides all Recruiter related methods */
    @Autowired
    private RecruiterService recruiterService = null;
    /** User utils for obtaining any session user information */
    @Autowired
    private UserUtils userUtils = null;

    /**
     * Showing employer profile
     * @param employerId     Id of employer
     * @param request        Http Request
     * @param response       Http Response
     * @return model and view with employer profile,
     * Not Found page if there are no employer with such id,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/recruiter-show-employer-profile/{employerId}")
    public ModelAndView showRecruiterProfile(
            @PathVariable final Long employerId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {

        ModelAndView employerProfile = new ModelAndView("employer/employer-show-recruiter-profile.jade");
        try {
            Employer employer = recruiterService.findEmployer(employerId);
            employerProfile.addObject("recruiter", employer);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        } catch (NotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        return employerProfile;
    }

    public RecruiterService getRecruiterService() {
        return recruiterService;
    }

    public void setRecruiterService(final RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }

    public UserUtils getUserUtils() {
        return userUtils;
    }

    public void setUserUtils(final UserUtils userUtils) {
        this.userUtils = userUtils;
    }
}
