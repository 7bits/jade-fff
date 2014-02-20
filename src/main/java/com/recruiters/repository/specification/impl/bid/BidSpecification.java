package com.recruiters.repository.specification.impl.bid;

import com.recruiters.model.Bid;
import com.recruiters.model.Deal;
import com.recruiters.repository.specification.ISpecification;
import com.recruiters.repository.specification.impl.CommonAndSpecification;
import com.recruiters.repository.specification.impl.CommonOrSpecification;

/**
 * Abstract Bid Specification
 */
public abstract class BidSpecification implements ISpecification<Bid> {

    public BidSpecification() {
    }

    @Override
    public Boolean isSatisfiedBy(final Bid candidate) {
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
