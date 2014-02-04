package com.recruiters.web.form;

import com.recruiters.repository.specification.VacancyBidSpecification;
import com.recruiters.repository.specification.VacancyCleanVacancySpecification;
import com.recruiters.repository.specification.VacancyDealSpecification;
import com.recruiters.repository.specification.VacancySpecification;

import java.io.Serializable;


/**
 * Vacancy Search Filter Form
 */
public class VacanciesFilter {
    private String searchText = "";
    private Boolean hideVacancies = false;
    private Boolean hideBids = false;
    private Boolean hideDeals = false;
    private String date = "";
    private String submit = null;

    public VacanciesFilter() {
    }

    /**
     * Copy constructor
     * @param vacanciesFilter    VacanciesFilter instance
     */
    public VacanciesFilter(final VacanciesFilter vacanciesFilter) {
        searchText = vacanciesFilter.getSearchText();
        hideVacancies = vacanciesFilter.getHideVacancies();
        hideBids = vacanciesFilter.getHideBids();
        hideDeals = vacanciesFilter.getHideDeals();
        date = vacanciesFilter.getDate();
    }

    /**
     * Specification builder
     * @return Vacancy Specification
     */
    public VacancySpecification getSpecifications() {
        if (!hideVacancies && hideBids && hideDeals) {
            return new VacancyCleanVacancySpecification();
        }
        if (hideVacancies && !hideBids && hideDeals) {
            return new VacancyBidSpecification();
        }
        if (hideVacancies && hideBids && !hideDeals) {
            return new VacancyDealSpecification();
        }
        if (!hideVacancies && !hideBids && hideDeals) {
            return new VacancyCleanVacancySpecification().or(new VacancyBidSpecification());
        }
        if (!hideVacancies && hideBids && !hideDeals) {
            return new VacancyCleanVacancySpecification().or(new VacancyDealSpecification());
        }
        if (hideVacancies && !hideBids && !hideDeals) {
            return new VacancyDealSpecification().or(new VacancyBidSpecification());
        }
        if (!hideVacancies && !hideDeals && !hideBids) {
            return new VacancyCleanVacancySpecification()
                    .or(new VacancyBidSpecification()
                            .or(new VacancyDealSpecification()));
        }
        return null;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(final String searchText) {
        this.searchText = searchText;
    }

    public Boolean getHideVacancies() {
        return hideVacancies;
    }

    public void setHideVacancies(final Boolean hideVacancies) {
        this.hideVacancies = hideVacancies;
    }

    public Boolean getHideBids() {
        return hideBids;
    }

    public void setHideBids(final Boolean hideBids) {
        this.hideBids = hideBids;
    }

    public Boolean getHideDeals() {
        return hideDeals;
    }

    public void setHideDeals(final Boolean hideDeals) {
        this.hideDeals = hideDeals;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getSubmit() {
        return submit;
    }

    public void setSubmit(final String submit) {
        this.submit = submit;
    }
}
