package com.recruiters.repository.specification.vacancy.order;

import com.recruiters.model.Vacancy;
import com.recruiters.repository.specification.IOrderSpecification;
import com.recruiters.repository.specification.ISpecification;
import com.recruiters.repository.specification.vacancy.VacancySpecification;

/**
 * Vacancy List "ORDER BY" specification
 */
public abstract class VacancyListOrderSpecification implements IOrderSpecification {
    private Boolean isAsc;

    public VacancyListOrderSpecification(final Boolean isAsc) {
        this.isAsc = isAsc;
    }
    @Override
    public Boolean isAsc() {
        return isAsc;
    }

    @Override
    public String asSql() {
        return null;
    }
}