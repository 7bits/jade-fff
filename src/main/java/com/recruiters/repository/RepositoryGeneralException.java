package com.recruiters.repository;

/**
 * General purpose exception for Repository Layer
 * is thrown when no other Exceptions are suitable
 */
public class RepositoryGeneralException extends Exception {

    public RepositoryGeneralException(final Exception e) {
        super(e);
    }
}
