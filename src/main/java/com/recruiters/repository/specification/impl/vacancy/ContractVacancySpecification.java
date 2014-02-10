package com.recruiters.repository.specification.impl.vacancy;

import com.recruiters.model.Vacancy;

/**
 * Vacancy is Deal Specification
 */
public class ContractVacancySpecification extends VacancySpecification {

    public ContractVacancySpecification() {}

    @Override
    public Boolean isSatisfiedBy(final Vacancy candidate) {
        return (candidate.getDealId() != 0L);
    }

    @Override
    public String asSql() {
        return " deal_id IS NOT NULL ";
    }
}
