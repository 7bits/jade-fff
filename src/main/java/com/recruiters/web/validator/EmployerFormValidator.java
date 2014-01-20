package com.recruiters.web.validator;

import com.recruiters.model.Employer;
import com.recruiters.service.EmployerService;
import com.recruiters.service.ServiceException;
import com.recruiters.web.form.EmployerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for "Employer Form"
 */
@Component
public class EmployerFormValidator implements Validator {
    @Autowired
    private EmployerService employerService = null;
    /** Minimum password length, checked */
    private static final Integer MINIMUM_PASSWORD_LENGTH = 6;

    public EmployerFormValidator() {}

    public boolean supports(final Class clazz) {
        return EmployerForm.class.equals(clazz);
    }

    /**
     * Check if all necessary fields are not empty
     * If password is changing too, check if old password filled out properly
     * and new password is long enough
     * @param object    Employer Form object
     * @param errors    Errors (from binding result)
     */
    public void validate(final Object object, final Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "NotEmpty.employerForm.id");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.employerForm.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.employerForm.lastName");
        EmployerForm employerForm = (EmployerForm) object;
        Employer employer = null;
        try {
            employer = employerService.findEmployer(employerForm.getId());
        } catch (ServiceException e) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "ServiceException.employerForm.id");
        }
        if (!employerForm.getNewPassword().isEmpty() && employer != null) {
            if (employerForm.getOldPassword().isEmpty()) {
                errors.rejectValue("oldPassword", "NotEmpty.employerForm.oldPassword");
            } else if (!employerForm.getOldPassword().equals(employer.getUser().getPassword())) {
                errors.rejectValue("oldPassword", "NotEqual.employerForm.oldPassword");
            } else if (employerForm.getNewPassword().length() < MINIMUM_PASSWORD_LENGTH) {
                errors.rejectValue("newPassword", "Short.employerForm.newPassword");
            }
        }
    }
}
