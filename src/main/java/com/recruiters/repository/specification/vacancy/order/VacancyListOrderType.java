package com.recruiters.repository.specification.vacancy.order;

import com.recruiters.model.Vacancy;
import com.recruiters.repository.specification.vacancy.VacancySpecification;

/**
 * Vacancy ordered by type
 */
public class VacancyListOrderType extends VacancyListOrderSpecification {

    public VacancyListOrderType(final Boolean isAsc) {
        super(isAsc);
    }

    @Override
    public Boolean isAsc() {
        return super.isAsc();
    }

    @Override
    public String asSql() {
        if (isAsc()) {
            return " ORDER BY (deal_id IS NOT NULL), (bid_id IS NOT NULL) ";
        } else {
            return " ORDER BY (bid_id IS NULL), (deal_id IS NULL) ";
        }
    }
}
