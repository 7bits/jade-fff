package com.recruiters.repository.specification.vacancy.order;

import com.recruiters.model.Vacancy;
import com.recruiters.repository.specification.vacancy.VacancySpecification;

/**
 * Vacancy list ordered by creation date
 */
public class VacancyListOrderCreated extends VacancyListOrderSpecification {
    /** StringBuilder buffer size */
    private static final Integer STRING_BUFFER_SIZE = 1024;

    public VacancyListOrderCreated(final Boolean isAsc) {
        super(isAsc);
    }

    @Override
    public Boolean isAsc() {
        return super.isAsc();
    }

    @Override
    public String asSql() {
        StringBuilder sb = new StringBuilder(STRING_BUFFER_SIZE);
        sb.append(" ORDER BY creation_date ");
        if (isAsc()) {
            sb.append(" ASC ");
        } else {
            sb.append(" DESC ");
        }
        return sb.toString();
    }
}
