package com.recruiters.web.validator;

import com.recruiters.web.form.VacancyForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Validator for "Vacancy Form"
 */
@Component
public class VacancyFormValidator implements Validator {

    public VacancyFormValidator() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public boolean supports(final Class clazz) {
        return VacancyForm.class.equals(clazz);
    }

    public void validate(final Object object, final Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty.vacancyForm.title");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty.vacancyForm.description");
        VacancyForm vacancyForm = (VacancyForm) object;
        if (vacancyForm.getSalaryFrom() < 0L || vacancyForm.getSalaryTo() < 0L) {
            errors.rejectValue("salaryFrom", "Positive.vacancyForm.salaryFrom");
        } else if (vacancyForm.getSalaryFrom() > vacancyForm.getSalaryTo()) {
            errors.rejectValue("salaryFrom", "LessThanTo.vacancyForm.salaryFrom");
        }

    }
}
