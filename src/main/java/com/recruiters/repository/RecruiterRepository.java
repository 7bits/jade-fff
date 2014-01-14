package com.recruiters.repository;

import com.recruiters.model.Recruiter;
import com.recruiters.repository.mapper.RecruiterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Repository for working with recruiter
 */
@Repository
public class RecruiterRepository {

    @Autowired
    private RecruiterMapper recruiterMapper = null;

    public Recruiter findById(final Long recruiterId) throws RepositoryException {
        if (recruiterId == null) {
            throw new RepositoryException("recruiterId is null");
        }
        try {

            return recruiterMapper.findById(recruiterId);
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    public RecruiterMapper getRecruiterMapper() {
        return recruiterMapper;
    }

    public void setRecruiterMapper(final RecruiterMapper recruiterMapper) {
        this.recruiterMapper = recruiterMapper;
    }
}
