package com.recruiters.repository.specification;

import com.recruiters.model.Vacancy;

/**
 * Created by fairdev on 29.01.14.
 */
public class VacancyBidSpecification extends VacancySpecification {

    public VacancyBidSpecification() {}

    @Override
    public Boolean isSatisfiedBy(final Vacancy candidate) {
        return (candidate.getBidId() != 0L) &&
                (candidate.getDealId() == 0L);
    }

    @Override
    public String asSql() {
        return " (deal_id IS NULL AND bid_id IS NOT NULL) ";
    }
}
