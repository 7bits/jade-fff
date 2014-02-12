package com.recruiters.repository.specification.impl.deal;

import com.recruiters.model.Deal;
import com.recruiters.model.Vacancy;
import com.recruiters.model.status.DealStatus;
import com.recruiters.repository.specification.impl.vacancy.VacancySpecification;

/**
 * Deal is with fired recruiter
 */
public class FiredDealSpecification extends DealSpecification {

    public FiredDealSpecification() {}

    @Override
    public Boolean isSatisfiedBy(final Deal candidate) {
        return candidate.getStatus().equals(DealStatus.FIRED);
    }

    @Override
    public String asSql() {
        return " status=\"FIRED\" ";
    }
}
