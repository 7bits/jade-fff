package com.recruiters.repository.specification.vacancy;

import com.recruiters.model.Vacancy;
import com.recruiters.repository.specification.IListSpecification;
import com.recruiters.repository.specification.IOrderSpecification;
import com.recruiters.repository.specification.ISpecification;
import com.recruiters.repository.specification.vacancy.order.VacancyListOrderSpecification;

/**
 * Specification for Vacancy List
 */
public class VacancyListSpecification implements IListSpecification<Vacancy> {
    private VacancySpecification vacancySpecification;
    private VacancyListOrderSpecification vacancyListOrderSpecification;

    public VacancyListSpecification(
            final VacancySpecification vacancySpecification,
            final VacancyListOrderSpecification vacancyListOrderSpecification
    ) {
        this.vacancySpecification = vacancySpecification;
        this.vacancyListOrderSpecification = vacancyListOrderSpecification;
    }

    @Override
    public ISpecification<Vacancy> getEntitySpecification() {
        return vacancySpecification;
    }

    @Override
    public IOrderSpecification getOrderSpecification() {
        return vacancyListOrderSpecification;
    }

    @Override
    public String asSql() {
        if (vacancySpecification == null) {
            return " WHERE 0 ";
        }
        if (vacancyListOrderSpecification == null) {
            return " WHERE " + vacancySpecification.asSql();
        }
        return " WHERE " + vacancySpecification.asSql() +
                vacancyListOrderSpecification.asSql();
    }
}
