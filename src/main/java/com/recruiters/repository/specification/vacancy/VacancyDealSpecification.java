package com.recruiters.repository.specification.vacancy;

import com.recruiters.model.Vacancy;

/**
 * Vacancy is Deal Specification
 */
public class VacancyDealSpecification extends VacancySpecification {

    public VacancyDealSpecification() {}

    @Override
    public Boolean isSatisfiedBy(final Vacancy candidate) {
        return (candidate.getDealId() != 0L);
    }

    @Override
    public String asSql() {
        return " deal_id IS NOT NULL ";
    }
}
