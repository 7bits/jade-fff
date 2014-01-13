package com.recruiters.service;

/**
 * General purpose exception for Service Layer
 * is thrown when no other Exceptions are suitable
 */
public class ServiceGeneralException extends Exception {

    /**
     * Exception with message only
     * @param message    String message
     */
    public ServiceGeneralException(final String message) {
        super(message);
    }

    /**
     * Exception with message and cause of Exception
     * @param message    String message
     * @param e          Exception
     */
    public ServiceGeneralException(final String message, final Exception e) {
        super(message, e);
    }
}
