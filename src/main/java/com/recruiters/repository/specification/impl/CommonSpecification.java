package com.recruiters.repository.specification.impl;

import com.recruiters.repository.specification.ISpecification;

/**
 * Common specification
 */
public abstract class CommonSpecification implements ISpecification<Object> {

    @Override
    public Boolean isSatisfiedBy(final Object candidate) {
        return null;
    }

    @Override
    public String asSql() {
        return null;
    }

    @Override
    public ISpecification<Object> or(final ISpecification<Object> other) {
        return new CommonOrSpecification(this, other);
    }

    @Override
    public ISpecification<Object> and(final ISpecification<Object> other) {
        return new CommonAndSpecification(this, other);
    }
}
