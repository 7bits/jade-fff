package com.recruiters.repository.specification;

import com.recruiters.model.Vacancy;

/**
 * Created by fairdev on 29.01.14.
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
