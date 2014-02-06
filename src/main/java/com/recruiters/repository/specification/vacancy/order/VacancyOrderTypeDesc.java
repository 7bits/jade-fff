package com.recruiters.repository.specification.vacancy.order;

import com.recruiters.model.Vacancy;
import com.recruiters.repository.specification.vacancy.VacancySpecification;

/**
 * Vacancy ordered by type (vacancy, bid or deal) descending
 */
public class VacancyOrderTypeDesc extends VacancySpecification {
    public VacancyOrderTypeDesc() {}

    @Override
    public Boolean isSatisfiedBy(final Vacancy candidate) {
        return true;
    }

    @Override
    public String asSql() {
        return " (bid_id IS NULL), (deal_id IS NULL) ";
    }
}