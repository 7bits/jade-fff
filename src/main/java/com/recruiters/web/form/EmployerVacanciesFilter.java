package com.recruiters.web.form;

import com.recruiters.repository.specification.ISpecification;
import com.recruiters.repository.specification.impl.OrderByParam;
import com.recruiters.repository.specification.impl.vacancy.UnpublishedVacancySpecification;
import com.recruiters.repository.specification.impl.vacancy.VacancyEmployerListSpecification;
import com.recruiters.repository.specification.impl.vacancy.VacancyNewBidsSpecification;
import com.recruiters.repository.specification.impl.vacancy.VacancyOldBidsSpecification;

/**
 * Filter for "My vacancies" employer page
 */
public class EmployerVacanciesFilter {
    private Boolean hideUnpublished = false;
    private Boolean hideOldBids = false;
    private String sortColumn;
    private Boolean sortAsc;

    public EmployerVacanciesFilter() {
    }

    public VacancyEmployerListSpecification getListSpecifications() {
        ISpecification vacancySpecification = new VacancyNewBidsSpecification();

        if (!hideUnpublished) {
            vacancySpecification = vacancySpecification.or(new UnpublishedVacancySpecification());
        }
        if (!hideOldBids) {
            vacancySpecification = vacancySpecification.or(new VacancyOldBidsSpecification());
        }

        if (sortColumn == null || sortAsc == null) {
            return new VacancyEmployerListSpecification(vacancySpecification, null);
        }

        if (sortColumn.equals("title") ||
                sortColumn.equals("creation_date") || sortColumn.equals("status") ||
                sortColumn.equals("max_updated_date") || sortColumn.equals("unseen_bids")) {
            return new VacancyEmployerListSpecification(vacancySpecification, new OrderByParam(sortColumn, sortAsc));
        }
        return new VacancyEmployerListSpecification(vacancySpecification, null);
    }

    public Boolean getHideUnpublished() {
        return hideUnpublished;
    }

    public void setHideUnpublished(final Boolean hideUnpublished) {
        this.hideUnpublished = hideUnpublished;
    }

    public Boolean getHideOldBids() {
        return hideOldBids;
    }

    public void setHideOldBids(final Boolean hideOldBids) {
        this.hideOldBids = hideOldBids;
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
