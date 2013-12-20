package com.recruiters.web.controller.recruiter;

import com.recruiters.web.form.ApplicantForm;
import com.recruiters.web.validator.AddApplicantValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller Class fpr R61 "Edit or create applicant"
 */
@Controller
public class ModifyApplicant {
    /** Apache Logger for this class*/
    //protected final Logger logger = Logger.getLogger(getClass());
    /** Validator for add applicant */
    @Autowired
    private AddApplicantValidator addApplicantValidator;

    /**
     * Controller for creating new applicant with method GET
     * @return model and view with empty applicant
     */
    @RequestMapping(value = "recruiter-employee-create/{vacancyId}", method = RequestMethod.GET)
    public ModelAndView addApplicant(@PathVariable final Long vacancyId) {

        ModelAndView addApplicant = new ModelAndView("recruiter-employee-create.jade");
        ApplicantForm applicantForm = new ApplicantForm();
        applicantForm.setVacancyId(vacancyId);
        addApplicant.addObject("applicantForm", applicantForm);
        return addApplicant;
    }

    /**
     * Controller for creating new applicant with method POST
     * @param applicantForm         Form model attribute for applicant
     * @param vacancyId             Id of vacancy for corresponded applicant
     * @param bindingResult         BindingResult
     * @param redirectAttributes    RedirectAttributes
     * @return model and view for creating new applicant with errors if any,
     * otherwise redirects to R6 "Vacancy in progress of job searching"
     */
    @RequestMapping(value = "recruiter-employee-create/{vacancyId}", method = RequestMethod.POST)
    public ModelAndView addApplicantValidation(@ModelAttribute("applicantForm")
                                                   final ApplicantForm applicantForm,
                                               @PathVariable final Long vacancyId,
                                               final BindingResult bindingResult,
                                               final RedirectAttributes redirectAttributes) {

        addApplicantValidator.validate(applicantForm, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("recruiter-employee-create.jade");
            model.addObject("errors", bindingResult.getFieldError().getDefaultMessage());
            return model;
        }
        return new ModelAndView("redirect:/recruiter-progress-vacancy-show/" + vacancyId);
    }
//
//    private void addApplicantToDataBase (){
//
//    }

    public AddApplicantValidator getAddApplicantValidator() {
        return addApplicantValidator;
    }

    public void setAddApplicantValidator(final AddApplicantValidator addApplicantValidator) {
        this.addApplicantValidator = addApplicantValidator;
    }


}
