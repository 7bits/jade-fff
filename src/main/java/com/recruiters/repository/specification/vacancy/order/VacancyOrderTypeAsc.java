package com.recruiters.repository.specification.vacancy.order;

import com.recruiters.model.Vacancy;
import com.recruiters.repository.specification.vacancy.VacancySpecification;

/**
 * Vacancy ordered by type (vacancy, bid or deal) ascending
 */
public class VacancyOrderTypeAsc extends VacancySpecification {
    public VacancyOrderTypeAsc() {}

    @Override
    public Boolean isSatisfiedBy(final Vacancy candidate) {
        return true;
    }

    @Override
    public String asSql() {
        return " (deal_id IS NOT NULL), (bid_id IS NOT NULL) ";
    }
}
