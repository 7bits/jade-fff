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
    EmployerMapper employerMapper = null;

    public Employer findById(final Long id) {

        return this.getEmployerMapper().findById(id);
    }

    /**
     * Returns Employer POJO by its User id
     * @param userId    User id
     * @return Employer POJO
     */
    public Employer findEmployerByUser(final Long userId) {

        return this.getEmployerMapper().getByUserId(userId);
    }

    public EmployerMapper getEmployerMapper() {
        return employerMapper;
    }

    public void setEmployerMapper(final EmployerMapper employerMapper) {
        this.employerMapper = employerMapper;
    }
}
