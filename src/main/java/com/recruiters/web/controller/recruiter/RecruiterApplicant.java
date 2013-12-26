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

import javax.validation.Valid;

/**
 * Controller Class for R61 "Edit or create applicant"
 */
@Controller
@SessionAttributes("applicantForm")
public class RecruiterApplicant {

    @Autowired
    private RecruiterService recruiterService = null;

    @Autowired
    private ApplicantFormValidator applicantFormValidator = null;

    /**
     * Controller for creating new applicant with method GET
     * @return model and view with empty applicant
     */
    @RequestMapping(value = "recruiter-add-applicant/{dealId}", method = RequestMethod.GET)
    public ModelAndView addApplicant(@PathVariable final Long dealId) {

        ModelAndView addApplicant = new ModelAndView("recruiter/recruiter-add-applicant.jade");
        ApplicantForm applicantForm = new ApplicantForm();
        applicantForm.setDealId(dealId);
        addApplicant.addObject("applicantForm", applicantForm);

        return addApplicant;
    }

    /**
     * Controller for creating new applicant with method POST
     * @param applicantForm model attribute for applicant
     * @param bindingResult         BindingResult
     * @return model and view for creating new applicant with errors if any,
     * otherwise redirects to R6 "Vacancy in progress of job searching"
     */
    @RequestMapping(value = "recruiter-add-applicant/{dealId}", method = RequestMethod.POST)
    public ModelAndView createApplicant(
            @Valid @ModelAttribute("applicantForm") final ApplicantForm applicantForm,
            final BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("recruiter/recruiter-add-applicant.jade");
            model.addObject("applicantForm", applicantForm);

            return model;
        }
        // need some time for research
        this.getRecruiterService().saveApplicantToVacancy(
                applicantForm.fillModel(), applicantForm.getResumeFile(), applicantForm.getTestAnswerFile()
        );
        Long dealId = applicantForm.getDealId();

        return new ModelAndView("redirect:/recruiter-show-in-progress-vacancy/" + dealId);
    }

    /**
     * Controller for editing applicant with method GET
     * @param applicantId    Id of applicant
     * @return Model and View filled with applicant data
     */
    @RequestMapping(value = "recruiter-edit-applicant/{applicantId}", method = RequestMethod.GET)
    public ModelAndView editApplicant(@PathVariable final Long applicantId) {
        ModelAndView editApplicant = new ModelAndView("recruiter/recruiter-edit-applicant.jade");
        ApplicantForm applicantForm = new ApplicantForm(this.getRecruiterService().getApplicantById(applicantId));

        editApplicant.addObject("applicantForm", applicantForm);

        return editApplicant;
    }

    /**
     * Controller for editing applicant with method POST
     * @param applicantForm model attribute for applicant
     * @param bindingResult         BindingResult
     * @return model and view for editing applicant with errors if any,
     * otherwise redirects to R6 "Vacancy in progress of job searching"
     */
    @RequestMapping(value = "recruiter-edit-applicant/{applicantId}", method = RequestMethod.POST)
    public ModelAndView updateApplicant(
            @Valid @ModelAttribute("applicantForm") final ApplicantForm applicantForm,
            final BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("recruiter/recruiter-edit-applicant.jade");
            model.addObject("applicantForm", applicantForm);
            return model;
        }
        // TODO
        // need some time for research
        this.getRecruiterService().saveApplicantToVacancy(
                applicantForm.fillModel(), applicantForm.getResumeFile(), applicantForm.getTestAnswerFile()
        );
        Long dealId = applicantForm.getDealId();

        return new ModelAndView("redirect:/recruiter-show-in-progress-vacancy/" + dealId);
    }

    @InitBinder("applicantForm")
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
