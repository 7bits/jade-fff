package com.recruiters.repository.specification.impl.vacancy;

import com.recruiters.model.Vacancy;

/**
 * "Vacancy have new bids" Specification
 */
public class VacancyNewBidsSpecification extends VacancySpecification {

    public VacancyNewBidsSpecification() {}

    @Override
    public Boolean isSatisfiedBy(final Vacancy candidate) {
        return (candidate.getUnseenBidCount() != 0L);
    }

    @Override
    public String asSql() {
        return " unseen_bids != 0 AND status=\"ACTIVE\" ";
    }
}
