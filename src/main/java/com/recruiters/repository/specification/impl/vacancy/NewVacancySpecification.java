package com.recruiters.repository.specification.impl.vacancy;

import com.recruiters.model.Vacancy;

/**
 * Vacancy is clean Vacancy without Deal or Bid Specification
 */
public class NewVacancySpecification extends VacancySpecification {

    public NewVacancySpecification() {}

    @Override
    public Boolean isSatisfiedBy(final Vacancy candidate) {
        return (candidate.getBidId() == 0L) &&
                (candidate.getDealId() == 0L);
    }

    @Override
    public String asSql() {
        return " deal_id IS NULL AND bid_id IS NULL ";
    }
}
