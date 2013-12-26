package com.recruiters.web.form;

import com.recruiters.model.Vacancy;

/**
 * Vacancy with bid count object
 */
public class VacancyWithBidCount {
    private Vacancy vacancy;
    private Long bidCount = 0L;

    public VacancyWithBidCount() {
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(final Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    public Long getBidCount() {
        return bidCount;
    }

    public void setBidCount(final Long bidCount) {
        this.bidCount = bidCount;
    }
}
