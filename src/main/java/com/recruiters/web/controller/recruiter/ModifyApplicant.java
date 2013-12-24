package com.recruiters.web.controller.recruiter;

import com.recruiters.service.RecruiterService;
import com.recruiters.web.form.ApplicantForm;
import com.recruiters.web.validator.ApplicantValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;

/**
 * Controller Class fpr R61 "Edit or create applicant"
 */
@Controller
@SessionAttributes("applicantForm")
public class ModifyApplicant {
    /** Logger */
    protected final Logger log = Logger.getLogger(getClass());

    @Autowired
    private RecruiterService recruiterService = null;

    /** Validator for check applicant  form*/
    @Autowired
    private ApplicantValidator applicantValidator = null;

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
     * @param applicantForm model attribute for applicant
     * @param vacancyId             Id of vacancy for corresponded applicant
     * @param bindingResult         BindingResult
     * @param redirectAttributes    RedirectAttributes
     * @return model and view for creating new applicant with errors if any,
     * otherwise redirects to R6 "Vacancy in progress of job searching"
     */
    @RequestMapping(value = "recruiter-employee-create/{vacancyId}", method = RequestMethod.POST)
    public ModelAndView addApplicantValidation(
           @ModelAttribute("applicantForm") final ApplicantForm applicantForm,
           @PathVariable final Long vacancyId,
           final BindingResult bindingResult,
           final RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("recruiter-employee-create.jade");
            model.addObject("errors", bindingResult.getFieldError().getDefaultMessage());
            return model;
        }
        log.debug("ApplicantFormName:" + applicantForm.getFirstName());
        this.getRecruiterService().addApplicantToVacancy(
                applicantForm.getModel(), applicantForm.getResumeFile(), applicantForm.getTestAnswerFile()
        );
        return new ModelAndView("redirect:/recruiter-progress-vacancy-show/" + vacancyId);
    }

    @InitBinder("applicantForm")
    protected void initApplicantFormBinder(final WebDataBinder binder) {

        binder.setValidator(this.getApplicantValidator());
    }

    public ApplicantValidator getApplicantValidator() {
        return applicantValidator;
    }

    public void setApplicantValidator(final ApplicantValidator applicantValidator) {
        this.applicantValidator = applicantValidator;
    }

    public RecruiterService getRecruiterService() {
        return recruiterService;
    }

    public void setRecruiterService(final RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }
}
