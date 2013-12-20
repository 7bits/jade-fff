package com.recruiters.web.controller.recruiter;

import com.recruiters.web.form.ApplicantForm;
import com.recruiters.web.validator.AddApplicantValidator;
import org.apache.log4j.Logger;
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

    protected final Logger LOG = Logger.getLogger(getClass());
    @Autowired
    AddApplicantValidator addApplicantValidator;

    /**
     * Controller for creating new applicant with method GET
     * @return model and view with empty applicant
     */
    @RequestMapping(value = "recruiter-employee-create/{vacancyId}", method = RequestMethod.GET)
    public ModelAndView addApplicant (@PathVariable Long vacancyId) {

        ModelAndView addApplicant = new ModelAndView ("recruiter-employee-create.jade");
        ApplicantForm applicantForm = new ApplicantForm();
        applicantForm.setVacancyId (vacancyId);
        addApplicant.addObject ("applicantForm", applicantForm);
        return addApplicant;
    }

    @RequestMapping(value = "recruiter-employee-create/{vacancyId}", method = RequestMethod.POST)
    public ModelAndView addApplicantValidation (@ModelAttribute("applicantForm")
                                                    ApplicantForm applicantForm,
                                                @PathVariable Long vacancyId,
                                                BindingResult bindingResult,
                                                RedirectAttributes redirectAttributes) {

        addApplicantValidator.validate (applicantForm, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView ("recruiter-employee-create.jade");
            LOG.debug ("Adding applicant was not successful");
            model.addObject("errors", bindingResult.getFieldError().getDefaultMessage());
            return model;
        }
        return new ModelAndView("redirect:/recruiter-progress-vacancy-show/" + vacancyId);
    }

    private void addApplicantToDataBase (){

    }

}
