package com.recruiters.repository.specification.impl.bid;

import com.recruiters.model.Bid;
import com.recruiters.model.status.BidStatus;

/**
 * Bid is "Approved" bid
 */
public class ApprovedBidSpecification extends BidSpecification {

    public ApprovedBidSpecification() {}

    @Override
    public Boolean isSatisfiedBy(final Bid candidate) {
        return candidate.getStatus().equals(BidStatus.APPROVED);
    }

    @Override
    public String asSql() {
        return " status=\"APPROVED\" ";
    }
}
