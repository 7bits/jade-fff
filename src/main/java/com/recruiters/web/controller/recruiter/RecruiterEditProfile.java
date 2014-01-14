package com.recruiters.web.controller.recruiter;

import com.recruiters.model.Recruiter;
import com.recruiters.model.User;
import com.recruiters.service.*;
import com.recruiters.service.SecurityException;
import com.recruiters.web.controller.utils.UserUtils;
import com.recruiters.web.form.RecruiterForm;
import com.recruiters.web.validator.RecruiterFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Controller Class for "Recruiter Profile"
 */
@Controller
public class RecruiterEditProfile {

    @Autowired
    private UserUtils userUtils = null;
    @Autowired
    private RecruiterService recruiterService = null;
    @Autowired
    private RecruiterFormValidator recruiterFormValidator = null;

    /**
     * Controller for "Recruiter Profile"
     * @return model and view with Recruiter Profile
     */
    @RequestMapping(value = "recruiter-profile", method = RequestMethod.GET)
    public ModelAndView showRecruiterProfile(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        ModelAndView recruiterProfile = new ModelAndView("recruiter/recruiter-profile.jade");
        try {
            User user = userUtils.getCurrentUser(request);
            Recruiter recruiter = recruiterService.findRecruiter(user.getRecruiterId());
            RecruiterForm recruiterForm = new RecruiterForm(recruiter);
            recruiterProfile.addObject("recruiterForm", recruiterForm);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return recruiterProfile;
    }

    /**
     * Controller for creating editing recruiter with method POST
     * @param request               Http Request
     * @param recruiterForm         Model attribute for recruiter
     * @param bindingResult         BindingResult
     * @return model and view for editing recruiter with errors if any
     */
    @RequestMapping(value = "recruiter-profile", method = RequestMethod.POST)
    public ModelAndView editRecruiter(
            final HttpServletRequest request,
            final HttpServletResponse response,
            @Valid @ModelAttribute("recruiterForm") final RecruiterForm recruiterForm,
            final BindingResult bindingResult
    ) throws Exception {
        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("recruiter/recruiter-profile.jade");
            model.addObject("recruiterForm", recruiterForm);

            return model;
        }
        try {
            User user = userUtils.getCurrentUser(request);
            Recruiter updatedRecruiter = recruiterForm.fillModel(user);
            recruiterService.saveProfileForRecruiter(updatedRecruiter, user.getRecruiterId());
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (SecurityException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }

        return new ModelAndView("redirect:/recruiter-active-deals");
    }


    @InitBinder("recruiterForm")
    protected void recruiterFormBinder(final WebDataBinder binder) {
        binder.setValidator(recruiterFormValidator);
    }

    public RecruiterService getRecruiterService() {
        return recruiterService;
    }

    public void setRecruiterService(final RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }
}