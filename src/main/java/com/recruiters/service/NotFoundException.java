package com.recruiters.service;

/**
 * NotFound exception for Service Layer
 * is thrown when something is not found
 * when performing service request
 */
public class NotFoundException extends Exception {

    /**
     * Exception with message only
     * @param message    String message
     */
    public NotFoundException(final String message) {
        super(message);
    }

    /**
     * Exception with message and cause of Exception
     * @param message    String message
     * @param e          Exception
     */
    public NotFoundException(final String message, final Exception e) {
        super(message, e);
    }
}