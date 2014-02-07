package com.recruiters.repository.specification.vacancy;

import com.recruiters.model.Vacancy;
import com.recruiters.repository.specification.ISpecification;

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

    @Override
    public VacancySpecification and(final ISpecification<Vacancy> other) {
        return new VacancyAndSpecification(this, other);
    }
}
