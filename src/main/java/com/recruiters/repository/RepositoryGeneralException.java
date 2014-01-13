package com.recruiters.repository;

/**
 * General purpose exception for Repository Layer
 * is thrown when no other Exceptions are suitable
 */
public class RepositoryGeneralException extends Exception {

    /**
     * Exception with message only
     * @param message    String message
     */
    public RepositoryGeneralException(final String message) {
        super(message);
    }

    /**
     * Exception with message and cause of Exception
     * @param message    String message
     * @param e          Exception
     */
    public RepositoryGeneralException(final String message, final Exception e) {
        super(message, e);
    }
}
