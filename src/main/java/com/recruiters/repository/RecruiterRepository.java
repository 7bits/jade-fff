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

    public Recruiter getById(final Long recruiterId) {
        Recruiter recruiter = this.getRecruiterMapper().getById(recruiterId);

        return recruiter;
    }

    public Recruiter getByUserId(final Long userId) {
        Recruiter recruiter = this.getRecruiterMapper().getByUserId(userId);

        return recruiter;
    }

    public RecruiterMapper getRecruiterMapper() {
        return recruiterMapper;
    }

    public void setRecruiterMapper(final RecruiterMapper recruiterMapper) {
        this.recruiterMapper = recruiterMapper;
    }
}
