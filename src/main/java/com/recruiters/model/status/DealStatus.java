package com.recruiters.model.status;

/**
 * Deal Statuses
 */
public enum DealStatus {

    /** Status - in progress */
    IN_PROGRESS (0, "In progress"),
    /** Status - fired */
    FIRED (1, "Fired"),
    /** Status - closed */
    CLOSED (2, "Closed"),
    /** Status - applicant approved */
    APPROVED (3, "Approved");

    private final int order;
    private final String description;

    /**
     * Default constructor
     * @param order items order
     * @param description human readable description
     */
    DealStatus(final int order, final String description) {

        this.order = order;
        this.description = description;
    }

    public int getOrder() {
        return order;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return this.name();
    }

    @Override
    public String toString() {
        return this.getDescription();
    }
}