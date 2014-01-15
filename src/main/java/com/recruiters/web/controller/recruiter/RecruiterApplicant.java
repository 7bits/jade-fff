package com.recruiters.web.controller.recruiter;

import com.recruiters.model.Applicant;
import com.recruiters.model.User;
import com.recruiters.service.*;
import com.recruiters.service.SecurityException;
import com.recruiters.web.controller.utils.UserUtils;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Controller Class for R61 "Edit or create applicant"
 * Edit Applicant, Create Applicant controllers here
 */
@Controller
public class RecruiterApplicant {

    /** Recruiter Service provides all Recruiter related methods */
    @Autowired
    private RecruiterService recruiterService = null;
    /** User utils for obtaining any session user information */
    @Autowired
    private UserUtils userUtils = null;
    /** Validator for Applicant Form */
    @Autowired
    private ApplicantFormValidator applicantFormValidator = null;

    /**
     * Controller for creating new applicant with method GET
     * @param dealId    Id of deal we assign applicant to
     * @return model and view with empty applicant
     */
    @RequestMapping(value = "recruiter-add-applicant/{dealId}", method = RequestMethod.GET)
    public ModelAndView addApplicant(
            @PathVariable final Long dealId
    ) {

        ModelAndView addApplicant = new ModelAndView("recruiter/recruiter-add-applicant.jade");
        ApplicantForm applicantForm = new ApplicantForm();
        applicantForm.setDealId(dealId);
        addApplicant.addObject("applicantForm", applicantForm);

        return addApplicant;
    }

    /**
     * Controller for creating new applicant with method POST
     * @param applicantForm         Model attribute for applicant
     * @param bindingResult         BindingResult
     * @param request               Http Request
     * @param response              Http Response
     * @return model and view for creating new applicant with errors if any,
     * otherwise redirects to R6 "Vacancy in progress of job searching",
     * Forbidden page if deal requested is not related to current recruiter,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "recruiter-add-applicant/{dealId}", method = RequestMethod.POST)
    public ModelAndView createApplicant(
            @Valid @ModelAttribute("applicantForm") final ApplicantForm applicantForm,
            final BindingResult bindingResult,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("recruiter/recruiter-add-applicant.jade");
            model.addObject("applicantForm", applicantForm);

            return model;
        }
        try {
            User user = userUtils.getCurrentUser(request);
            // TODO
            // need some time for research
            this.getRecruiterService().saveApplicant(
                    applicantForm.fillModel(),
                    applicantForm.getResumeFile(),
                    applicantForm.getTestAnswerFile(),
                    user.getRecruiterId()
            );
            Long dealId = applicantForm.getDealId();

            return new ModelAndView("redirect:/recruiter-show-in-progress-vacancy/" + dealId);
        } catch (SecurityException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return null;
    }

    /**
     * Controller for editing applicant with method GET
     * @param applicantId    Id of applicant we edit
     * @param request        Http Request
     * @param response       Http Response
     * @return Model and View filled with applicant data, Forbidden page
     * if deal requested is not related to current recruiter, Internal
     * Server Error page if something is wrong with obtaining data due to
     * technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "recruiter-edit-applicant/{applicantId}", method = RequestMethod.GET)
    public ModelAndView editApplicant(
            @PathVariable final Long applicantId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        ModelAndView editApplicant = new ModelAndView("recruiter/recruiter-edit-applicant.jade");
        try {
            User user = userUtils.getCurrentUser(request);
            Applicant applicant = recruiterService.findApplicant(applicantId, user.getRecruiterId());
            ApplicantForm applicantForm = new ApplicantForm(applicant);
            editApplicant.addObject("applicantForm", applicantForm);
        } catch (SecurityException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return editApplicant;
    }

    /**
     * Controller for editing applicant with method POST
     * @param applicantForm         Model attribute for applicant
     * @param bindingResult         BindingResult
     * @param request               Http Request
     * @param response              Http Response
     * @return model and view for editing applicant with errors if any,
     * otherwise redirects to R6 "Vacancy in progress of job searching",
     * Forbidden page if deal requested is not related to current
     * recruiter, Internal Server Error page if something is wrong with
     * obtaining data due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "recruiter-edit-applicant/{applicantId}", method = RequestMethod.POST)
    public ModelAndView updateApplicant(
            @Valid @ModelAttribute("applicantForm") final ApplicantForm applicantForm,
            final BindingResult bindingResult,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("recruiter/recruiter-edit-applicant.jade");
            model.addObject("applicantForm", applicantForm);
            return model;
        }
        try {
            User user = userUtils.getCurrentUser(request);
            // TODO
            // need some time for research
            this.getRecruiterService().saveApplicant(
                    applicantForm.fillModel(),
                    applicantForm.getResumeFile(),
                    applicantForm.getTestAnswerFile(),
                    user.getRecruiterId()
            );
            Long dealId = applicantForm.getDealId();
            return new ModelAndView("redirect:/recruiter-show-in-progress-vacancy/" + dealId);
        } catch (SecurityException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return null;
    }

    /**
     * Binding default validator to "Applicant Form" validator
     * @param binder    Spring Web Data Binder
     */
    @InitBinder("applicantForm")
    protected void applicantFormBinder(final WebDataBinder binder) {
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

    public UserUtils getUserUtils() {
        return userUtils;
    }

    public void setUserUtils(final UserUtils userUtils) {
        this.userUtils = userUtils;
    }
}
