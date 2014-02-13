package com.recruiters.repository;

import com.recruiters.model.Employer;
import com.recruiters.repository.mapper.EmployerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Repository implementing all methods related to
 * Employer Entity manipulations with MyBatis
 */
@Repository
public class EmployerRepository {

    /** MyBatis Employer Mapper */
    @Autowired
    private EmployerMapper employerMapper = null;

    /**
     * Find and return employer instance by its id
     * @param employerId    Id of recruiter
     * @return Employer instance
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public Employer findById(final Long employerId) throws RepositoryException {
        if (employerId == null) {
            throw new RepositoryException("employerId is null");
        }
        try {

            return employerMapper.findById(employerId);
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }


    /**
     * Find and return employer instance for profile by its id
     * Will contain feedback and will not contain password etc
     * @param employerId    Id of recruiter
     * @return Employer instance
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public Employer findForProfileById(final Long employerId) throws RepositoryException {
        if (employerId == null) {
            throw new RepositoryException("employerId is null");
        }
        try {

            return employerMapper.findForProfileById(employerId);
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    public EmployerMapper getEmployerMapper() {
        return employerMapper;
    }

    public void setEmployerMapper(final EmployerMapper employerMapper) {
        this.employerMapper = employerMapper;
    }
}
