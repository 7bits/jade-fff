package com.recruiters.web.form.filter;

import com.recruiters.repository.specification.ISpecification;
import com.recruiters.repository.specification.impl.OrderByParam;
import com.recruiters.repository.specification.impl.deal.ActiveDealSpecification;
import com.recruiters.repository.specification.impl.deal.ApprovedDealSpecification;
import com.recruiters.repository.specification.impl.deal.FiredDealSpecification;
import com.recruiters.repository.specification.impl.deal.RecruiterDealListSpecification;

/**
 * Filter for "My vacancies in work" recruiter page
 */
public class RecruiterDealsFilter {
    private Boolean hideFired = false;
    private Boolean hideApproved = false;
    private String sortColumn;
    private Boolean sortAsc;

    public RecruiterDealsFilter() {
    }

    public Boolean getHideFired() {
        return hideFired;
    }

    public RecruiterDealListSpecification getListSpecifications() {
        ISpecification dealSpecification = new ActiveDealSpecification();

        if (!hideFired) {
            dealSpecification = dealSpecification.or(new FiredDealSpecification());
        }
        if (!hideApproved) {
            dealSpecification = dealSpecification.or(new ApprovedDealSpecification());
        }

        if (sortColumn == null || sortAsc == null) {
            return new RecruiterDealListSpecification(dealSpecification, null);
        }

        if (sortColumn.equals("title") ||
                sortColumn.equals("creation_date") || sortColumn.equals("lastname") ||
                sortColumn.equals("status") || sortColumn.equals("description") ||
                sortColumn.equals("max_updated_date") || sortColumn.equals("unseen_applicants")) {
            return new RecruiterDealListSpecification(dealSpecification, new OrderByParam(sortColumn, sortAsc));
        }
        return new RecruiterDealListSpecification(dealSpecification, null);
    }

    public void setHideFired(final Boolean hideFired) {
        this.hideFired = hideFired;
    }

    public Boolean getHideApproved() {
        return hideApproved;
    }

    public void setHideApproved(final Boolean hideApproved) {
        this.hideApproved = hideApproved;
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
