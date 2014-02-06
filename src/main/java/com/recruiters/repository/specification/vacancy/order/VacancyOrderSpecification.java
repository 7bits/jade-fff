package com.recruiters.repository.specification.vacancy.order;

import com.recruiters.model.Vacancy;
import com.recruiters.repository.specification.ISpecification;
import com.recruiters.repository.specification.vacancy.VacancySpecification;

/**
 * Vacancy "ORDER BY" specification
 */
public class VacancyOrderSpecification extends VacancySpecification {
    private ISpecification<Vacancy> specification;
    private ISpecification<Vacancy> orderSpecification;
    /** Default string size, used for string builder */
    private static final Integer DEFAULT_STRING_SIZE = 1024;

    public VacancyOrderSpecification(final ISpecification<Vacancy> specification, final ISpecification<Vacancy> orderSpecification) {
        this.specification = specification;
        this.orderSpecification = orderSpecification;
    }

    @Override
    public Boolean isSatisfiedBy(final Vacancy candidate) {
        return specification.isSatisfiedBy(candidate);
    }

    @Override
    public String asSql() {
        StringBuilder sb = new StringBuilder(DEFAULT_STRING_SIZE);
        if (specification != null) {
            sb.append(specification.asSql());
        } else  {
            // TODO think how to solve inconsistency between SQL and Code language
            sb.append(" 0 ");
        }
        sb.append(" ORDER BY ");
        sb.append(orderSpecification.asSql());

        return sb.toString();
    }
}
