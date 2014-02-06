package com.recruiters.web.form;

import com.recruiters.repository.specification.vacancy.VacancyListSpecification;
import com.recruiters.repository.specification.vacancy.order.VacancyListOrderCreated;
import com.recruiters.repository.specification.vacancy.order.VacancyListOrderDescription;
import com.recruiters.repository.specification.vacancy.order.VacancyListOrderTitle;
import com.recruiters.repository.specification.vacancy.order.VacancyListOrderType;
import com.recruiters.repository.specification.vacancy.VacancyBidSpecification;
import com.recruiters.repository.specification.vacancy.VacancyCleanVacancySpecification;
import com.recruiters.repository.specification.vacancy.VacancyDealSpecification;
import com.recruiters.repository.specification.vacancy.VacancySpecification;


/**
 * Vacancy Search Filter Form
 */
public class VacanciesFilter {
    private String searchText = "";
    private Boolean hideVacancies = false;
    private Boolean hideBids = false;
    private Boolean hideDeals = false;
    private String date = "";
    private String sortingOrder;
    private String sortColumn;
    private Boolean sortAsc;

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
    public VacancyListSpecification getListSpecifications() {
        VacancySpecification vacancySpecification = null;
        if (!hideVacancies && hideBids && hideDeals) {
            vacancySpecification = new VacancyCleanVacancySpecification();
        }
        if (hideVacancies && !hideBids && hideDeals) {
            vacancySpecification = new VacancyBidSpecification();
        }
        if (hideVacancies && hideBids && !hideDeals) {
            vacancySpecification = new VacancyDealSpecification();
        }
        if (!hideVacancies && !hideBids && hideDeals) {
            vacancySpecification = new VacancyCleanVacancySpecification().or(new VacancyBidSpecification());
        }
        if (!hideVacancies && hideBids && !hideDeals) {
            vacancySpecification = new VacancyCleanVacancySpecification().or(new VacancyDealSpecification());
        }
        if (hideVacancies && !hideBids && !hideDeals) {
            vacancySpecification = new VacancyDealSpecification().or(new VacancyBidSpecification());
        }
        if (!hideVacancies && !hideDeals && !hideBids) {
            vacancySpecification = new VacancyCleanVacancySpecification()
                    .or(new VacancyBidSpecification()
                            .or(new VacancyDealSpecification()));
        }
        if (sortColumn == null || sortAsc == null) {
            return new VacancyListSpecification(vacancySpecification, null);
        }
        if (sortColumn.equals("title")) {
            return new VacancyListSpecification(vacancySpecification, new VacancyListOrderTitle(sortAsc));
        }
        if (sortColumn.equals("desc")) {
            return new VacancyListSpecification(vacancySpecification, new VacancyListOrderDescription(sortAsc));
        }
        if (sortColumn.equals("date")) {
            return new VacancyListSpecification(vacancySpecification, new VacancyListOrderCreated(sortAsc));
        }
        if (sortColumn.equals("type")) {
            return new VacancyListSpecification(vacancySpecification, new VacancyListOrderType(sortAsc));
        }
        return new VacancyListSpecification(null, null);
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

    public String getSortingOrder() {
        return sortingOrder;
    }

    public void setSortingOrder(final String sortingOrder) {
        this.sortingOrder = sortingOrder;
    }

    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(final String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public Boolean getSortAsc() {
        return sortAsc;
    }

    public void setSortAsc(final Boolean sortAsc) {
        this.sortAsc = sortAsc;
    }
}
