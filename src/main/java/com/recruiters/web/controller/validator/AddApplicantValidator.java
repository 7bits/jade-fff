package com.recruiters.web.controller.validator;

import com.recruiters.web.controller.form.ApplicantForm;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * Validator for "Applicant Form" if adding applicant
 */
@Service
public class AddApplicantValidator {
    public boolean supports(Class clazz) {
        return ApplicantForm.class.equals(clazz);
    }

    public void validate (Object object, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.applicantForm.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace (errors, "lastName", "NotEmpty.applicantForm.lastName");
        ValidationUtils.rejectIfEmptyOrWhitespace (errors, "description", "NotEmpty.applicantForm.description");
    }
}
