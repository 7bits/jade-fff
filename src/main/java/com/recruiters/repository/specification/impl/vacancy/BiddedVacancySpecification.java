package com.recruiters.repository.specification.impl.vacancy;

import com.recruiters.model.Vacancy;

/**
 * Vacancy is Bid specification
 */
public class BiddedVacancySpecification extends VacancySpecification {

    public BiddedVacancySpecification() {}

    @Override
    public Boolean isSatisfiedBy(final Vacancy candidate) {
        return (candidate.getBidId() != 0L) &&
                (candidate.getDealId() == 0L);
    }

    @Override
    public String asSql() {
        return " deal_id IS NULL AND bid_id IS NOT NULL ";
    }
}
