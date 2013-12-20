package com.recruiters.web.controller.recruiter;

import com.recruiters.web.controller.form.ApplicantForm;
import com.recruiters.web.controller.validator.AddApplicantValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
    @RequestMapping(value = "/recruiter/addApplicant/{vacancyId}", method = RequestMethod.GET)
    public ModelAndView addApplicant () {

        ModelAndView addApplicant = new ModelAndView ("addApplicant");
        ApplicantForm applicantForm = new ApplicantForm();
        addApplicant.addObject ("applicantForm", applicantForm);
        return addApplicant;
    }

    @RequestMapping(value = "/recruiter/addApplicant/{vacancyId}", method = RequestMethod.POST)
    public ModelAndView addApplicantValidation (@ModelAttribute("applicantForm")
                                          ApplicantForm applicantForm,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {

        addApplicantValidator.validate (applicantForm, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView ("addApplicant");
            LOG.debug ("Adding applicant was not successful");
            model.addObject("errors", bindingResult.getFieldError().getDefaultMessage());
            return model;
        }
        return new ModelAndView("redirect:");
    }

    private void addApplicantToDataBase (){

    }

}
