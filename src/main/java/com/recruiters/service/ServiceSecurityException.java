package com.recruiters.service;

/**
 * Security purposes exception for Service Layer
 * is thrown when there are any security issues
 * with performing service request
 */
public class ServiceSecurityException extends Exception {

    /**
     * Exception with message only
     * @param message    String message
     */
    public ServiceSecurityException(final String message) {
        super(message);
    }

    /**
     * Exception with message and cause of Exception
     * @param message    String message
     * @param e          Exception
     */
    public ServiceSecurityException(final String message, final Exception e) {
        super(message, e);
    }
}