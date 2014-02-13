package com.recruiters.web.controller.employer;

import com.recruiters.model.Employer;
import com.recruiters.model.Recruiter;
import com.recruiters.model.User;
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
 * Show control panel for Employer
 */
@Controller
public class EmployerControlPanel {

    /** Employer Service provides all Employer related methods */
    @Autowired
    private EmployerService employerService = null;
    /** User utils for obtaining any session user information */
    @Autowired
    private UserUtils userUtils = null;

    /**
     * Showing control panel
     * @param request        Http Request
     * @param response       Http Response
     * @return model and view with control panel
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/employer-control-panel")
    public ModelAndView showControlPanel(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {

        ModelAndView controlPanel = new ModelAndView("employer/employer-control-panel.jade");
        try {
            User user = userUtils.getCurrentUser(request);
            Employer employer = employerService.findEmployer(user.getEmployerId());
            controlPanel.addObject("employer", employer);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }

        return controlPanel;
    }

    public EmployerService getEmployerService() {
        return employerService;
    }

    public void setEmployerService(final EmployerService employerService) {
        this.employerService = employerService;
    }
}
