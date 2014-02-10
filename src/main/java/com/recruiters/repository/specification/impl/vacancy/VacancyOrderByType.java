package com.recruiters.repository.specification.impl.vacancy;

import com.recruiters.repository.specification.IOrderSpecification;

/**
 * Vacancy ordered by type
 */
public class VacancyOrderByType implements IOrderSpecification {
    private Boolean isAsc;

    public VacancyOrderByType(final Boolean isAsc) {
        this.isAsc = isAsc;
    }

    @Override
    public Boolean isAsc() {
        return isAsc;
    }

    @Override
    public String asSql() {
        if (isAsc) {
            return " ORDER BY (deal_id IS NOT NULL), (bid_id IS NOT NULL) ";
        } else {
            return " ORDER BY (bid_id IS NULL), (deal_id IS NULL) ";
        }
    }
}
