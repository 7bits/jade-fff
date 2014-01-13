package com.recruiters.service;

/**
 * Security purposes exception for Service Layer
 * is thrown when there are any security issues
 * with performing service request
 */
public class ServiceSecurityException extends Exception {

    public ServiceSecurityException(final String message) {
        super(message);
    }
}