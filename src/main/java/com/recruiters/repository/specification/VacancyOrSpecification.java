package com.recruiters.repository.specification;

import com.recruiters.model.Vacancy;

/**
 * Created by fairdev on 30.01.14.
 */
public class VacancyOrSpecification extends VacancySpecification {
    private ISpecification<Vacancy> specification1;
    private ISpecification<Vacancy> specification2;

    public VacancyOrSpecification(final ISpecification<Vacancy> specification1, final ISpecification<Vacancy> specification2) {
        this.specification1 = specification1;
        this.specification2 = specification2;
    }

    @Override
    public Boolean isSatisfiedBy(final Vacancy candidate) {
        return specification1.isSatisfiedBy(candidate) ||
                specification2.isSatisfiedBy(candidate);
    }

    @Override
    public String asSql() {
        return specification1.asSql() + " OR " + specification2.asSql();
    }
}
