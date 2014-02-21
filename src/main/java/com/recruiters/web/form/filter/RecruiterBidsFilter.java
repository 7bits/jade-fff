package com.recruiters.web.form.filter;

import com.recruiters.repository.specification.ISpecification;
import com.recruiters.repository.specification.impl.OrderByParam;
import com.recruiters.repository.specification.impl.bid.ActiveBidSpecification;
import com.recruiters.repository.specification.impl.bid.ApprovedBidSpecification;
import com.recruiters.repository.specification.impl.bid.BidListSpecification;
import com.recruiters.repository.specification.impl.bid.RejectedBidSpecification;
import com.recruiters.repository.specification.impl.bid.WithdrawnBidSpecification;

/**
 * Filter for "My bids" recruiter page
 */
public class RecruiterBidsFilter {
    private Boolean hideRejected = false;
    private Boolean hideApproved = false;
    private Boolean hideWithdrawn = false;
    private String sortColumn;
    private Boolean sortAsc;

    public RecruiterBidsFilter() {
    }

    public BidListSpecification getListSpecifications() {
        ISpecification bidSpecification = new ActiveBidSpecification();

        if (!hideRejected) {
            bidSpecification = bidSpecification.or(new RejectedBidSpecification());
        }
        if (!hideApproved) {
            bidSpecification = bidSpecification.or(new ApprovedBidSpecification());
        }
        if (!hideWithdrawn) {
            bidSpecification = bidSpecification.or(new WithdrawnBidSpecification());
        }

        if (sortColumn == null || sortAsc == null) {
            return new BidListSpecification(bidSpecification, null);
        }

        if (sortColumn.equals("title") ||
                sortColumn.equals("creation_date") || sortColumn.equals("lastname") ||
                sortColumn.equals("status") || sortColumn.equals("description") ||
                sortColumn.equals("viewed")) {
            return new BidListSpecification(bidSpecification, new OrderByParam(sortColumn, sortAsc));
        }
        return new BidListSpecification(bidSpecification, null);
    }

    public Boolean getHideRejected() {
        return hideRejected;
    }

    public void setHideRejected(final Boolean hideRejected) {
        this.hideRejected = hideRejected;
    }

    public Boolean getHideApproved() {
        return hideApproved;
    }

    public void setHideApproved(final Boolean hideApproved) {
        this.hideApproved = hideApproved;
    }

    public Boolean getHideWithdrawn() {
        return hideWithdrawn;
    }

    public void setHideWithdrawn(final Boolean hideWithdrawn) {
        this.hideWithdrawn = hideWithdrawn;
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
