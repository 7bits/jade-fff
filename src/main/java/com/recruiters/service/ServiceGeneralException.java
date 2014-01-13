package com.recruiters.service;

/**
 * General purpose exception for Service Layer
 * is thrown when no other Exceptions are suitable
 */
public class ServiceGeneralException extends Exception {

    public ServiceGeneralException(final String message) {
        super(message);
    }
}
