package com.recruiters.web.controller.employer;

import com.recruiters.model.Recruiter;
import com.recruiters.service.EmployerService;
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
 * Show recruiter profile for Employer
 */
@Controller
public class EmployerShowRecruiterProfile {

    /** Employer Service provides all Employer related methods */
    @Autowired
    private EmployerService employerService = null;
    /** User utils for obtaining any session user information */
    @Autowired
    private UserUtils userUtils = null;

    /**
     * Showing recruiter profile
     * @param recruiterId    Id of recruiter
     * @param request        Http Request
     * @param response       Http Response
     * @return model and view with recruiter profile,
     * Not Found page if there are no recruiter with such id,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/employer-show-recruiter-profile/{recruiterId}")
    public ModelAndView showRecruiterProfile(
            @PathVariable final Long recruiterId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {

        ModelAndView recruiterProfile = new ModelAndView("employer/employer-show-recruiter-profile.jade");
        try {
            Recruiter recruiter = employerService.findRecruiterForProfile(recruiterId);
            recruiterProfile.addObject("recruiter", recruiter);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        } catch (NotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        return recruiterProfile;
    }

    public EmployerService getEmployerService() {
        return employerService;
    }

    public void setEmployerService(final EmployerService employerService) {
        this.employerService = employerService;
    }
}
