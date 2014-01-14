package com.recruiters.repository;

import com.recruiters.model.Employer;
import com.recruiters.repository.mapper.EmployerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Repository for working with employer
 */
@Repository
public class EmployerRepository {

    @Autowired
    private EmployerMapper employerMapper = null;

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

    public EmployerMapper getEmployerMapper() {
        return employerMapper;
    }

    public void setEmployerMapper(final EmployerMapper employerMapper) {
        this.employerMapper = employerMapper;
    }
}
