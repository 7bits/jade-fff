package com.recruiters.repository;

import com.recruiters.model.Employer;
import com.recruiters.model.Recruiter;
import com.recruiters.repository.mapper.EmployerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Repository for working with employer
 */
@Repository
public class EmployerRepository {

    @Autowired
    EmployerMapper employerMapper = null;

    public Employer getById(final Long employerId) {
        return this.getEmployerMapper().getById(employerId);
    }

    /**
     * Returns Employer POJO by its User id
     * @param userId    User id
     * @return Employer POJO
     */
    public Employer getEmployerByUserId(final Long userId) {
        return this.getEmployerMapper().getByUserId(userId);
    }

    public EmployerMapper getEmployerMapper() {
        return employerMapper;
    }

    public void setEmployerMapper(final EmployerMapper employerMapper) {
        this.employerMapper = employerMapper;
    }
}
