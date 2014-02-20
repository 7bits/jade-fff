package com.recruiters.repository.specification.impl.bid;

import com.recruiters.model.Bid;
import com.recruiters.model.Deal;
import com.recruiters.model.status.BidStatus;
import com.recruiters.model.status.DealStatus;
import com.recruiters.repository.specification.impl.deal.DealSpecification;

/**
 * Bid is "Active" bid
 */
public class ActiveBidSpecification extends BidSpecification {

    public ActiveBidSpecification() {}

    @Override
    public Boolean isSatisfiedBy(final Bid candidate) {
        return candidate.getStatus().equals(BidStatus.ACTIVE);
    }

    @Override
    public String asSql() {
        return " status=\"ACTIVE\" ";
    }
}
