package com.recruiters.repository.specification;

import com.recruiters.model.Vacancy;

/**
 * Abstract Vacancy Specification
 */
public abstract class VacancySpecification implements ISpecification<Vacancy> {

    public VacancySpecification() {
    }

    @Override
    public Boolean isSatisfiedBy(final Vacancy candidate) {
        return false;
    }

    @Override
    public String asSql() {
        return null;
    }

    @Override
    public VacancySpecification or(final ISpecification<Vacancy> other) {
        return new VacancyOrSpecification(this, other);
    }
}
