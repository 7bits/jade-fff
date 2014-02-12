package com.recruiters.repository.specification.impl.deal;

import com.recruiters.model.Deal;
import com.recruiters.model.status.DealStatus;

/**
 * Deal is deal with approved candidate
 */
public class ApprovedDealSpecification extends DealSpecification {

    public ApprovedDealSpecification() {}

    @Override
    public Boolean isSatisfiedBy(final Deal candidate) {
        return candidate.getStatus().equals(DealStatus.APPROVED);
    }

    @Override
    public String asSql() {
        return " status=\"APPROVED\" ";
    }
}
