package com.recruiters.model;

/**
 * List of statuses of applicant
 */
public enum ApplicantStatus {

    /** Status - in progress */
    IN_PROGRESS (0, "In progress"),
    /** Status - rejected */
    REJECTED (1, "Rejected"),
    /** Status - approved */
    APPROVED (2, "Approved");


    private final int order;
    private final String description;

    /**
     * Default constructor
     * @param order items order
     * @param description human readable description
     */
    ApplicantStatus(final int order, final String description) {

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