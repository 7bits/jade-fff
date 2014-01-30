package com.recruiters.web.controller.employer;

import com.recruiters.model.Employer;
import com.recruiters.model.User;
import com.recruiters.service.*;
import com.recruiters.service.exception.NotAffiliatedException;
import com.recruiters.service.exception.ServiceException;
import com.recruiters.web.controller.utils.UserUtils;
import com.recruiters.web.form.EmployerForm;
import com.recruiters.web.helper.UrlResolver;
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
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Locale;

/**
 * Employer profile view and edit
 */
@Controller
public class EmployerEditProfile {

    /** User utils for obtaining any session user information */
    @Autowired
    private UserUtils userUtils = null;
    /** Employer Service provides all Employer related methods */
    @Autowired
    private EmployerService employerService = null;
    /** Validator for Employer Form */
    @Autowired
    private EmployerFormValidator employerFormValidator = null;
    /** Url Builder */
    @Autowired
    private UrlResolver urlResolver;

    /**
     * Controller for "Employer Profile" with method GET
     * Display employer profile with ability to edit it
     * @param request        Http Request
     * @param response       Http Response
     * @return model and view with Employer Profile, Internal Server Error
     * page if something is wrong with obtaining data due to technical or
     * any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/employer-profile", method = RequestMethod.GET)
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
            return null;
        }

        return employerProfile;
    }

    /**
     * Controller for editing employer profile with method POST
     * @param request               Http Request
     * @param response              Http Response
     * @param employerForm          Model attribute for employer
     * @param bindingResult         BindingResult
     * @return model and view for editing employer with errors if any,
     * otherwise redirects to employer deals page, Forbidden page if form
     * saved is not related to current recruiter, Internal Server Error
     * page if something is wrong with obtaining data due to technical or
     * any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/employer-profile", method = RequestMethod.POST)
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
            employerService.saveProfileForEmployer(updatedEmployer, user.getEmployerId());
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        } catch (NotAffiliatedException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }
        Locale locale = RequestContextUtils.getLocale(request);

        return new ModelAndView(
                urlResolver.buildRedirectUri("employer-progress-vacancies-list", locale)
        );
    }

    /**
     * Binding default validator to "Employer Form" validator
     * @param binder    Spring Web Data Binder
     */
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