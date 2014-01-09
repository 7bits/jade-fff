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

    public Recruiter findById(final Long recruiterId) {

        return this.getRecruiterMapper().findById(recruiterId);
    }

    public RecruiterMapper getRecruiterMapper() {
        return recruiterMapper;
    }

    public void setRecruiterMapper(final RecruiterMapper recruiterMapper) {
        this.recruiterMapper = recruiterMapper;
    }
}
