package com.recruiters.repository;

import com.recruiters.model.Recruiter;
import com.recruiters.repository.mapper.RecruiterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Repository implementing all methods related to
 * Applicant Entity manipulations
 */
@Repository
public class RecruiterRepository {

    /** MyBatis Recruiter Mapper */
    @Autowired
    private RecruiterMapper recruiterMapper = null;

    /**
     * Find and return recruiter instance by its id
     * @param recruiterId    Id of recruiter
     * @return Recruiter instance
     * @throws RepositoryException if input parameters is incorrect or there
     * were any technical issues
     */
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
