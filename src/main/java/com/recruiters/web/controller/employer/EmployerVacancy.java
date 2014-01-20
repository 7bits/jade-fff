package com.recruiters.web.controller.employer;

import com.recruiters.service.EmployerService;
import com.recruiters.web.controller.utils.UserUtils;
import com.recruiters.web.form.VacancyForm;
import com.recruiters.web.validator.VacancyFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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


    /**
     * Controller for creating new vacancy with method GET
     * @return model and view with empty vacancy
     */
    @RequestMapping(value = "employer-vacancy-create", method = RequestMethod.GET)
    public ModelAndView createVacancy() {

        ModelAndView createVacancy = new ModelAndView("employer/vacancy-create.jade");
        VacancyForm vacancyForm = new VacancyForm();
        createVacancy.addObject("vacancyForm", vacancyForm);

        return createVacancy;
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
