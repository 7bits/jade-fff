package com.recruiters.web.controller.employer;

import com.recruiters.model.Employer;
import com.recruiters.model.User;
import com.recruiters.service.*;
import com.recruiters.service.SecurityException;
import com.recruiters.web.controller.utils.UserUtils;
import com.recruiters.web.form.EmployerForm;
import com.recruiters.web.validator.EmployerFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Controller Class for "Employer Profile"
 */
@Controller
public class EmployerEditProfile {

    @Autowired
    private UserUtils userUtils = null;
    @Autowired
    private EmployerService employerService = null;
    @Autowired
    private EmployerFormValidator employerFormValidator = null;

    /**
     * Controller for "Employer Profile"
     * @return model and view with Employer Profile
     */
    @RequestMapping(value = "employer-profile", method = RequestMethod.GET)
    public ModelAndView showEmployerProfile(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        ModelAndView employerProfile = new ModelAndView("employer/employer-profile.jade");
        try {
            User user = userUtils.getCurrentUser(request);
            Employer employer = employerService.findEmployer(user.getEmployerId());
            EmployerForm employerForm = new EmployerForm(employer);
            employerProfile.addObject("employerForm", employerForm);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return employerProfile;
    }

    /**
     * Controller for creating editing employer with method POST
     * @param request               Http Request
     * @param employerForm          Model attribute for employer
     * @param bindingResult         BindingResult
     * @return model and view for editing employer with errors if any
     */
    @RequestMapping(value = "employer-profile", method = RequestMethod.POST)
    public ModelAndView editEmployer(
            final HttpServletRequest request,
            final HttpServletResponse response,
            @Valid @ModelAttribute("employerForm") final EmployerForm employerForm,
            final BindingResult bindingResult
    ) throws Exception {
        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("employer/employer-profile.jade");
            model.addObject("employerForm", employerForm);

            return model;
        }
        try {
            User user = userUtils.getCurrentUser(request);
            Employer updatedEmployer = employerForm.fillModel(user);
            employerService.saveProfileForEmployer(updatedEmployer);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return new ModelAndView("redirect:/employer-progress-vacancies-list");
    }


    @InitBinder("employerForm")
    protected void initSurveyListFormBinder(final WebDataBinder binder) {
        binder.setValidator(employerFormValidator);
    }

    public EmployerService getEmployerService() {
        return employerService;
    }

    public void setEmployerService(final EmployerService employerService) {
        this.employerService = employerService;
    }
}