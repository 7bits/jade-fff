package com.recruiters.repository;

/**
 * Technical purposes exception for Repository Layer
 * is thrown when there are technical issues with data
 */
public class RepositoryTechnicalException extends Exception {

    /**
     * Exception with message only
     * @param message    String message
     */
    public RepositoryTechnicalException(final String message) {
        super(message);
    }

    /**
     * Exception with message and cause of Exception
     * @param message    String message
     * @param e          Exception
     */
    public RepositoryTechnicalException(final String message, final Exception e) {
        super(message, e);
    }
}