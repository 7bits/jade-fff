package com.recruiters.service;

/**
 * Technical purposes exception for Service Layer
 * is thrown when there are technical issues
 * with Repository layer or with Transactions
 */
public class ServiceTechnicalException extends Exception {

    /**
     * Exception with message only
     * @param message    String message
     */
    public ServiceTechnicalException(final String message) {
        super(message);
    }

    /**
     * Exception with message and cause of Exception
     * @param message    String message
     * @param e          Exception
     */
    public ServiceTechnicalException(final String message, final Exception e) {
        super(message, e);
    }
}