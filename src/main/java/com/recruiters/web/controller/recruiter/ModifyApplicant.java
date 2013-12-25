package com.recruiters.web.controller.recruiter;

import com.recruiters.service.RecruiterService;
import com.recruiters.web.form.ApplicantForm;
import com.recruiters.web.validator.ApplicantFormValidator;
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

    @Autowired
    private RecruiterService recruiterService = null;

    @Autowired
    private ApplicantFormValidator applicantFormValidator = null;

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
           @Valid @ModelAttribute("applicantForm") final ApplicantForm applicantForm,
           @PathVariable final Long vacancyId,
           final BindingResult bindingResult,
           final RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("recruiter-employee-create.jade");
            model.addObject("applicantForm", applicantForm);

            return model;
        }
        this.getRecruiterService().saveApplicantToVacancy(
                applicantForm.getModel(), applicantForm.getResumeFile(), applicantForm.getTestAnswerFile()
        );

        return new ModelAndView("redirect:/recruiter-progress-vacancy-show/" + vacancyId);
    }

    /**
     * Controller for editing applicant with method GET
     * @param applicantId    Id of applicant
     * @return Model and View filled with applicant data
     */
    @RequestMapping(value = "recruiter-employee-edit/{applicantId}", method = RequestMethod.GET)
    public ModelAndView editApplicant(@PathVariable final Long applicantId) {
        ModelAndView editApplicant = new ModelAndView("recruiter-employee-edit.jade");
        ApplicantForm applicantForm = new ApplicantForm(this.getRecruiterService().getApplicantById(applicantId));

        editApplicant.addObject("applicantForm", applicantForm);

        return editApplicant;
    }

    /**
     * Controller for editing applicant with method POST
     * @param applicantForm model attribute for applicant
     * @param applicantId             Id of applicant
     * @param bindingResult         BindingResult
     * @param redirectAttributes    RedirectAttributes
     * @return model and view for editing applicant with errors if any,
     * otherwise redirects to R6 "Vacancy in progress of job searching"
     */
    @RequestMapping(value = "recruiter-employee-edit/{applicantId}", method = RequestMethod.POST)
    public ModelAndView editApplicantValidation(
            @Valid @ModelAttribute("applicantForm") final ApplicantForm applicantForm,
            @PathVariable final Long applicantId,
            final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("recruiter-employee-edit.jade");
            model.addObject("applicantForm", applicantForm);
            return model;
        }
        Long vacancyId = applicantForm.getVacancyId();
        this.getRecruiterService().saveApplicantToVacancy(
                applicantForm.getModel(), applicantForm.getResumeFile(), applicantForm.getTestAnswerFile()
        );

        return new ModelAndView("redirect:/recruiter-progress-vacancy-show/" + vacancyId);
    }

    @InitBinder("applicantFormValidator")
    protected void initSurveyListFormBinder(final WebDataBinder binder) {
        binder.setValidator(this.getApplicantFormValidator());
    }

    public RecruiterService getRecruiterService() {
        return recruiterService;
    }

    public void setRecruiterService(final RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }

    public ApplicantFormValidator getApplicantFormValidator() {
        return applicantFormValidator;
    }

    public void setApplicantFormValidator(final ApplicantFormValidator applicantFormValidator) {
        this.applicantFormValidator = applicantFormValidator;
    }
}
