package com.recruiters.web.validator;

import com.recruiters.model.Employer;
import com.recruiters.service.EmployerService;
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

    public EmployerFormValidator() {}

    public boolean supports(final Class clazz) {
        return EmployerForm.class.equals(clazz);
    }

    public void validate(final Object object, final Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "NotEmpty.employerForm.id");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.employerForm.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.employerForm.lastName");
        EmployerForm employerForm = (EmployerForm) object;
        Employer employer = employerService.findEmployer(employerForm.getId());
        if (!employerForm.getNewPassword().isEmpty()) {
            if (employerForm.getOldPassword().isEmpty()) {
                errors.rejectValue("oldPassword", "NotEmpty.employerForm.oldPassword");
            } else if (!employerForm.getOldPassword().equals(employer.getUser().getPassword())) {
                errors.rejectValue("oldPassword", "NotEqual.employerForm.oldPassword");
            } else if (employerForm.getNewPassword().length() < 6){
                errors.rejectValue("newPassword", "Short.employerForm.newPassword");
            }
        }
    }
}
