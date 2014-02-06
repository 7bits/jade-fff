package com.recruiters.repository.specification.vacancy.order;

import com.recruiters.model.Vacancy;
import com.recruiters.repository.specification.vacancy.VacancySpecification;

/**
 * Vacancy ordered by title descending
 */
public class VacancyOrderTitleDesc extends VacancySpecification {
    public VacancyOrderTitleDesc() {}

    @Override
    public Boolean isSatisfiedBy(final Vacancy candidate) {
        return true;
    }

    @Override
    public String asSql() {
        return " title DESC ";
    }
}