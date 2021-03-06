package com.recruiters.web.controller.employer;

import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.service.EmployerService;
import com.recruiters.service.exception.NotAffiliatedException;
import com.recruiters.service.exception.NotFoundException;
import com.recruiters.service.exception.ServiceException;
import com.recruiters.web.controller.utils.UserUtils;
import com.recruiters.web.form.VacancyForm;
import com.recruiters.web.helper.UrlResolver;
import com.recruiters.web.validator.VacancyFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Locale;

/**
 * Show Vacancy for Employer with all related actions
 */
@Controller
public class EmployerVacancy {


    /** User utils for obtaining any session user information */
    @Autowired
    private UserUtils userUtils = null;
    /** Employer Service provides all Employer related methods */
    @Autowired
    private EmployerService employerService = null;
    /** Validator for Vacancy Form */
    @Autowired
    private VacancyFormValidator vacancyFormValidator = null;
    /** Url Builder */
    @Autowired
    private UrlResolver urlResolver;


    /**
     * Creating new vacancy initial page
     * @return model and view with empty vacancy
     */
    @RequestMapping(value = "/employer-vacancy-create", method = RequestMethod.GET)
    public ModelAndView newVacancy() {
        ModelAndView createVacancy = new ModelAndView("employer/employer-vacancy-create.jade");
        VacancyForm vacancyForm = new VacancyForm();
        createVacancy.addObject("vacancyForm", vacancyForm);

        return createVacancy;
    }

    /**
     * Validate and create new vacancy
     * @param request               Http Request
     * @param response              Http Response
     * @param vacancyForm           Model for Vacancy Form
     * @param bindingResult         BindingResult
     * @return model and view for editing vacancy with errors if any,
     * otherwise redirects to employer deals page,
     * Internal Server Error page if something is wrong with obtaining
     * data due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/employer-vacancy-create", method = RequestMethod.POST)
    public ModelAndView createVacancy(
            final HttpServletRequest request,
            final HttpServletResponse response,
            @Valid @ModelAttribute("vacancyForm") final VacancyForm vacancyForm,
            final BindingResult bindingResult
    ) throws Exception {
        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("employer/employer-vacancy-create.jade");
            model.addObject("vacancyForm", vacancyForm);

            return model;
        }
        Locale locale = RequestContextUtils.getLocale(request);
        try {
            User user = userUtils.getCurrentUser(request);
            Vacancy vacancy = vacancyForm.fillModel(user);
            employerService.saveVacancy(vacancy, vacancyForm.getTestFile(), locale);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }

        return new ModelAndView(
                urlResolver.buildRedirectUri("/employer-recruiter-search", locale)
        );
    }

    /**
     * Creating new vacancy initial page
     * @param request               Http Request
     * @param response              Http Response
     * @param vacancyId             Vacancy id
     * @return model and view with exact vacancy
     * Forbidden page if vacancy requested is not related to current employer,
     * Not found page if such vacancy not exists,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/employer-vacancy-edit/{vacancyId}", method = RequestMethod.GET)
    public ModelAndView showEditVacancy(
            final HttpServletRequest request,
            final HttpServletResponse response,
            @PathVariable final Long vacancyId
    ) throws Exception {
        ModelAndView editVacancy = new ModelAndView("employer/employer-vacancy-edit.jade");
        try {
            User user = userUtils.getCurrentUser(request);
            Vacancy vacancy = employerService.findVacancy(vacancyId, user.getEmployerId());
            VacancyForm vacancyForm = new VacancyForm(vacancy);
            editVacancy.addObject("vacancyForm", vacancyForm);
        } catch (NotAffiliatedException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        } catch (NotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }

        return editVacancy;
    }

    /**
     * Validate and create new vacancy
     * @param request               Http Request
     * @param response              Http Response
     * @param vacancyForm           Model for Vacancy Form
     * @param bindingResult         BindingResult
     * @return model and view for editing vacancy with errors if any,
     * otherwise redirects to employer deals page,
     * Internal Server Error page if something is wrong with obtaining
     * data due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/employer-vacancy-edit/{vacancyId}", method = RequestMethod.POST)
    public ModelAndView saveEditVacancy(
            final HttpServletRequest request,
            final HttpServletResponse response,
            @Valid @ModelAttribute("vacancyForm") final VacancyForm vacancyForm,
            final BindingResult bindingResult
    ) throws Exception {
        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("employer/employer-vacancy-edit.jade");
            model.addObject("vacancyForm", vacancyForm);

            return model;
        }
        Locale locale = RequestContextUtils.getLocale(request);
        try {
            User user = userUtils.getCurrentUser(request);
            Vacancy vacancy = vacancyForm.fillModel(user);
            employerService.saveVacancy(vacancy, vacancyForm.getTestFile(), locale);
        } catch (NotAffiliatedException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }

        return new ModelAndView(
                urlResolver.buildRedirectUri("/employer-recruiter-search", locale)
        );
    }

    /**
     * Binding default validator to "Vacancy Form" validator
     * @param binder    Spring Web Data Binder
     */
    @InitBinder("vacancyForm")
    protected void applicantFormBinder(final WebDataBinder binder) {
        binder.setValidator(vacancyFormValidator);
    }


}
