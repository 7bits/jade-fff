package com.recruiters.repository.specification.impl.deal;

import com.recruiters.model.Deal;
import com.recruiters.model.status.DealStatus;

/**
 * Deal is "In progress" deal
 */
public class ActiveDealSpecification extends DealSpecification {

    public ActiveDealSpecification() {}

    @Override
    public Boolean isSatisfiedBy(final Deal candidate) {
        return candidate.getStatus().equals(DealStatus.IN_PROGRESS);
    }

    @Override
    public String asSql() {
        return " status=\"IN_PROGRESS\" ";
    }
}
