package com.recruiters.repository.specification.impl;

import com.recruiters.repository.specification.ISpecification;

/**
 * Common "And" specification
 */
public class CommonAndSpecification extends CommonSpecification {
    private ISpecification specification1;
    private ISpecification specification2;
    /** Default string size, used for string builder */
    private static final Integer DEFAULT_STRING_SIZE = 1024;

    public CommonAndSpecification(final ISpecification specification1, final ISpecification specification2) {
        this.specification1 = specification1;
        this.specification2 = specification2;
    }

    @Override
    public Boolean isSatisfiedBy(final Object candidate) {
        return specification1.isSatisfiedBy(candidate) &&
                specification2.isSatisfiedBy(candidate);
    }

    @Override
    public String asSql() {
        StringBuilder sb = new StringBuilder(DEFAULT_STRING_SIZE);
        sb.append(" (");
        sb.append(specification1.asSql());
        sb.append(") AND (");
        sb.append(specification2.asSql());
        sb.append(") ");

        return sb.toString();
    }
}
