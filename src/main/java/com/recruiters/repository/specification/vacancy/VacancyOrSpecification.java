package com.recruiters.repository.specification.vacancy;

import com.recruiters.model.Vacancy;
import com.recruiters.repository.specification.ISpecification;

/**
 * Vacancy logic "OR" specification
 */
public class VacancyOrSpecification extends VacancySpecification {
    private ISpecification<Vacancy> specification1;
    private ISpecification<Vacancy> specification2;
    /** Default string size, used for string builder */
    private static final Integer DEFAULT_STRING_SIZE = 1024;

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
        StringBuilder sb = new StringBuilder(DEFAULT_STRING_SIZE);
        sb.append(" (");
        sb.append(specification1.asSql());
        sb.append(") OR (");
        sb.append(specification2.asSql());
        sb.append(") ");

        return sb.toString();
    }
}
