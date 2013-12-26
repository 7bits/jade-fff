package com.recruiters.model;

/**
 * List of statuses of deal
 */
public enum VacancyStatus {

    /** Status - active */
    ACTIVE (0, "Active"),
    /** Status - archived */
    ARCHIVED (1, "Archived");

    private final int order;
    private final String description;

    /**
     * Default constructor
     * @param order items order
     * @param description human readable description
     */
    VacancyStatus(final int order, final String description) {

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