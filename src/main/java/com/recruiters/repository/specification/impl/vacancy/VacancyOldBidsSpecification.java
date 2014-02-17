package com.recruiters.repository.specification.impl.vacancy;

import com.recruiters.model.Vacancy;

/**
 * "Vacancy have no new bids or have not bids at all" Specification
 */
public class VacancyOldBidsSpecification extends VacancySpecification {

    public VacancyOldBidsSpecification() {}

    @Override
    public Boolean isSatisfiedBy(final Vacancy candidate) {
        return (candidate.getUnseenBidCount() == 0L);
    }

    @Override
    public String asSql() {
        return " unseen_bids = 0 ";
    }
}
