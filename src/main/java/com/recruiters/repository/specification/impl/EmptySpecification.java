package com.recruiters.repository.specification.impl;

import com.recruiters.repository.specification.ISpecification;

/**
 * Common empty specification
 */
public class EmptySpecification implements ISpecification<Object>{
    @Override
    public ISpecification<Object> and(final ISpecification<Object> other) {
        return new CommonAndSpecification(this, other);
    }

    @Override
    public ISpecification<Object> or(final ISpecification<Object> other) {
        return new CommonOrSpecification(this, other);
    }

    @Override
    public String asSql() {
        return " 0 ";
    }

    @Override
    public Boolean isSatisfiedBy(final Object candidate) {
        return false;
    }
}
