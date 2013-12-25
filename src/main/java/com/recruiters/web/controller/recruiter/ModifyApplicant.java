package com.recruiters.web.controller.recruiter;

import com.recruiters.model.Applicant;
import com.recruiters.model.Vacancy;
import com.recruiters.service.RecruiterService;
import com.recruiters.web.form.ApplicantForm;
import com.recruiters.web.validator.AddApplicantValidator;
import com.recruiters.web.validator.EditApplicantValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    /** Validator for adding applicant with ApplicantForm */
    @Autowired
    private AddApplicantValidator addApplicantValidator = null;

    /** Validator for editing applicant with ApplicantForm */
    @Autowired
    private EditApplicantValidator editApplicantValidator = null;

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
           final RedirectAttributes redirectAttributes) {

        addApplicantValidator.validate(applicantForm, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("recruiter-employee-create.jade");
            model.addObject("applicantForm", applicantForm);
            return model;
        }
        log.debug("ApplicantFormName:" + applicantForm.getFirstName());
        this.getRecruiterService().addApplicantToVacancy(
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

        ModelAndView editApplicant = new ModelAndView("recruiter-employee-create.jade");
        Applicant currentApplicant = getApplicantInfo(applicantId);
        ApplicantForm applicantForm = new ApplicantForm();
        applicantForm.setVacancyId(1L);
        applicantForm.setId(currentApplicant.getId());
        applicantForm.setFirstName(currentApplicant.getFirstName());
        applicantForm.setLastName(currentApplicant.getLastName());
        applicantForm.setDescription(currentApplicant.getDescription());
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
            @ModelAttribute("applicantForm") final ApplicantForm applicantForm,
            @PathVariable final Long applicantId,
            final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {

        editApplicantValidator.validate(applicantForm, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("recruiter-employee-create.jade");
            model.addObject("applicantForm", applicantForm);
            return model;
        }
        Long vacancyId = applicantForm.getVacancyId();
        //
        // Saving applicant should be here
        //
        return new ModelAndView("redirect:/recruiter-progress-vacancy-show/" + vacancyId);
    }

    /**
     * Temporarily emulating Service method
     * @param applicantId Id of applicant we want to get info about
     * @return Applicant instance if any corresponding
     */
    private Applicant getApplicantInfo(final Long applicantId) {

        Applicant applicant1 = new Applicant(1L, null, "Иван", "Иванов", "Рублю по две за раз");
        Applicant applicant2 = new Applicant(2L, null, "Пётр", "Петров", "Рублю, аж щепки летят");
        Applicant applicant3 = new Applicant(3L, null, "Константин", "Константинопольский", "Рублю с двух рук");
        if (applicantId.equals(1L)) {
            return applicant1;
        } else if (applicantId.equals(2L)) {
            return applicant2;
        } else if (applicantId.equals(3L)) {
            return applicant3;
        } else {
            return null;
        }
    }

    public RecruiterService getRecruiterService() {
        return recruiterService;
    }

    public void setRecruiterService(final RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }
}
