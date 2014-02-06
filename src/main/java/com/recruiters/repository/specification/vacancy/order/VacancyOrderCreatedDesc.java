package com.recruiters.repository.specification.vacancy.order;

import com.recruiters.model.Vacancy;
import com.recruiters.repository.specification.vacancy.VacancySpecification;

/**
 * Vacancy ordered by creation date descending
 */
public class VacancyOrderCreatedDesc extends VacancySpecification {
    public VacancyOrderCreatedDesc() {}

    @Override
    public Boolean isSatisfiedBy(final Vacancy candidate) {
        return true;
    }

    @Override
    public String asSql() {
        return " creation_date DESC ";
    }
}
