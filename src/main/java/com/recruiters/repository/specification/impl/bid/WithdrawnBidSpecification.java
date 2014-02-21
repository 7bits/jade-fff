package com.recruiters.repository.specification.impl.bid;

import com.recruiters.model.Bid;
import com.recruiters.model.status.BidStatus;

/**
 * Bid is "Withdrawn" bid
 */
public class WithdrawnBidSpecification extends BidSpecification {

    public WithdrawnBidSpecification() {}

    @Override
    public Boolean isSatisfiedBy(final Bid candidate) {
        return candidate.getStatus().equals(BidStatus.WITHDRAWN);
    }

    @Override
    public String asSql() {
        return " status=\"WITHDRAWN\" ";
    }
}
