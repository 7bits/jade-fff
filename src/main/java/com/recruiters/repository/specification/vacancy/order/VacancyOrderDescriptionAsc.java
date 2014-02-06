package com.recruiters.repository.specification.vacancy.order;

import com.recruiters.model.Vacancy;
import com.recruiters.repository.specification.vacancy.VacancySpecification;

/**
 * Vacancy ordered by description ascending
 */
public class VacancyOrderDescriptionAsc extends VacancySpecification {
    public VacancyOrderDescriptionAsc() {}

    @Override
    public Boolean isSatisfiedBy(final Vacancy candidate) {
        return true;
    }

    @Override
    public String asSql() {
        return " description ASC ";
    }
}
