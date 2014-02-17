package com.recruiters.repository.specification.impl.vacancy;

import com.recruiters.model.Vacancy;
import com.recruiters.model.status.VacancyStatus;

/**
 * "Vacancy is unpublished" Specification
 */
public class UnpublishedVacancySpecification extends VacancySpecification {

    public UnpublishedVacancySpecification() {}

    @Override
    public Boolean isSatisfiedBy(final Vacancy candidate) {
        return (candidate.getStatus() != VacancyStatus.UNPUBLISHED);
    }

    @Override
    public String asSql() {
        return " status = \"UNPUBLISHED\" ";
    }
}
