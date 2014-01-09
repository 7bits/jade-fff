package com.recruiters.web.controller.recruiter;

import com.recruiters.model.Recruiter;
import com.recruiters.model.User;
import com.recruiters.service.RecruiterService;
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
    public ModelAndView showRecruiterProfile(final HttpServletRequest request) {
        ModelAndView recruiterProfile = new ModelAndView("recruiter/recruiter-profile.jade");
        Long userId = userUtils.getCurrentUserId(request);
        Recruiter recruiter = recruiterService.findRecruiterByUserId(userId);
        RecruiterForm recruiterForm = new RecruiterForm(recruiter);
        recruiterProfile.addObject("recruiterForm", recruiterForm);
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
            @Valid @ModelAttribute("recruiterForm") final RecruiterForm recruiterForm,
            final BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("recruiter/recruiter-profile.jade");
            model.addObject("recruiterForm", recruiterForm);

            return model;
        }
        User currentUser = userUtils.getCurrentUser(request);
        Recruiter updatedRecruiter = recruiterForm.fillModel(currentUser);
        recruiterService.saveRecruiterProfile(updatedRecruiter);

        return new ModelAndView("redirect:/recruiter-active-deals");
    }


    @InitBinder("recruiterForm")
    protected void initSurveyListFormBinder(final WebDataBinder binder) {
        binder.setValidator(recruiterFormValidator);
    }

    public RecruiterService getRecruiterService() {
        return recruiterService;
    }

    public void setRecruiterService(final RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }
}