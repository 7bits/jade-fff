package com.recruiters.web.validator;

import com.recruiters.model.Recruiter;
import com.recruiters.service.RecruiterService;
import com.recruiters.service.ServiceException;
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
    /** Minimum password length, checked */
    private static final Integer MINIMUM_PASSWORD_LENGTH = 6;

    public RecruiterFormValidator() {}

    public boolean supports(final Class clazz) {
        return RecruiterForm.class.equals(clazz);
    }

    /**
     * Check if all necessary fields are not empty
     * If password is changing too, check if old password filled out properly
     * and new password is long enough
     * @param object    Recruiter Form object
     * @param errors    Errors binding result
     */
    public void validate(final Object object, final Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "NotEmpty.recruiterForm.id");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.recruiterForm.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.recruiterForm.lastName");
        RecruiterForm recruiterForm = (RecruiterForm) object;
        Recruiter recruiter = null;
        try {
            recruiter = recruiterService.findRecruiter(recruiterForm.getId());
        } catch (ServiceException e) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "ServiceException.recruiterForm.id");
        }
        if (!recruiterForm.getNewPassword().isEmpty() && recruiter != null) {
            if (recruiterForm.getOldPassword().isEmpty()) {
                errors.rejectValue("oldPassword", "NotEmpty.recruiterForm.oldPassword");
            } else if (!recruiterForm.getOldPassword().equals(recruiter.getUser().getPassword())) {
                errors.rejectValue("oldPassword", "NotEqual.recruiterForm.oldPassword");
            } else if (recruiterForm.getNewPassword().length() < MINIMUM_PASSWORD_LENGTH) {
                errors.rejectValue("newPassword", "Short.recruiterForm.newPassword");
            }
        }
    }
}
