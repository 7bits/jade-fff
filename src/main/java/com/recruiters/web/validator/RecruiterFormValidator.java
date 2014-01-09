package com.recruiters.web.validator;

import com.recruiters.model.Recruiter;
import com.recruiters.service.RecruiterService;
import com.recruiters.web.form.RecruiterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for "Recruiter Form"
 */
@Component
public class RecruiterFormValidator implements Validator {
    @Autowired
    private RecruiterService recruiterService = null;

    public RecruiterFormValidator() {}

    public boolean supports(final Class clazz) {
        return RecruiterForm.class.equals(clazz);
    }

    public void validate(final Object object, final Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "NotEmpty.recruiterForm.id");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.recruiterForm.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.recruiterForm.lastName");
        RecruiterForm recruiterForm = (RecruiterForm) object;
        Recruiter recruiter = recruiterService.findRecruiter(recruiterForm.getId());
        if (!recruiterForm.getNewPassword().isEmpty()) {
            if (recruiterForm.getOldPassword().isEmpty()) {
                errors.rejectValue("oldPassword", "NotEmpty.recruiterForm.oldPassword");
            } else if (!recruiterForm.getOldPassword().equals(recruiter.getUser().getPassword())) {
                errors.rejectValue("oldPassword", "NotEqual.recruiterForm.oldPassword");
            } else if (recruiterForm.getNewPassword().length() < 6){
                errors.rejectValue("newPassword", "Short.recruiterForm.newPassword");
            }
        }
    }
}
