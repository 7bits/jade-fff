package com.recruiters.repository.specification.vacancy.order;

import com.recruiters.model.Vacancy;
import com.recruiters.repository.specification.vacancy.VacancySpecification;

/**
 * Vacancy ordered by creation date ascending
 */
public class VacancyOrderCreatedAsc extends VacancySpecification {
    public VacancyOrderCreatedAsc() {}

    @Override
    public Boolean isSatisfiedBy(final Vacancy candidate) {
        return true;
    }

    @Override
    public String asSql() {
        return " creation_date ASC ";
    }
}
