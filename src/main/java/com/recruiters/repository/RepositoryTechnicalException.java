package com.recruiters.repository;

/**
 * Technical purposes exception for Repository Layer
 * is thrown when there are technical issues with data
 */
public class RepositoryTechnicalException extends Exception {

    public RepositoryTechnicalException(final Exception e) {
        super(e);
    }
}