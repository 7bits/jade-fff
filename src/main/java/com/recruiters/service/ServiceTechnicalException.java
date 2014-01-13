package com.recruiters.service;

/**
 * Technical purposes exception for Service Layer
 * is thrown when there are technical issues
 * with Repository layer or with Transactions
 */
public class ServiceTechnicalException extends Exception {

    public ServiceTechnicalException(final String message) {
        super(message);
    }
}