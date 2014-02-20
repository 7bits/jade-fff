package com.recruiters.repository.specification.impl.bid;

import com.recruiters.model.Bid;
import com.recruiters.model.status.BidStatus;

/**
 * Bid is "Rejected" bid
 */
public class RejectedBidSpecification extends BidSpecification {

    public RejectedBidSpecification() {}

    @Override
    public Boolean isSatisfiedBy(final Bid candidate) {
        return candidate.getStatus().equals(BidStatus.REJECTED);
    }

    @Override
    public String asSql() {
        return " status=\"REJECTED\" ";
    }
}
