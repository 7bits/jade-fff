package com.recruiters.repository.specification.impl.deal;

import com.recruiters.model.Deal;
import com.recruiters.repository.specification.ISpecification;
import com.recruiters.repository.specification.impl.CommonAndSpecification;
import com.recruiters.repository.specification.impl.CommonOrSpecification;

/**
 * Abstract Deal Specification
 */
public abstract class DealSpecification implements ISpecification<Deal> {

    public DealSpecification() {
    }

    @Override
    public Boolean isSatisfiedBy(final Deal candidate) {
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
