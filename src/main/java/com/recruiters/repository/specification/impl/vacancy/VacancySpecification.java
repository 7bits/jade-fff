package com.recruiters.repository.specification.impl.vacancy;

import com.recruiters.model.Vacancy;
import com.recruiters.repository.specification.ISpecification;
import com.recruiters.repository.specification.impl.CommonAndSpecification;
import com.recruiters.repository.specification.impl.CommonOrSpecification;

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
    public ISpecification or(final ISpecification other) {
        return new CommonOrSpecification(this, other);
    }

    @Override
    public ISpecification and(final ISpecification other) {
        return new CommonAndSpecification(this, other);
    }
}
