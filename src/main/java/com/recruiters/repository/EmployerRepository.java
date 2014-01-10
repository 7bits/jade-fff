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

        return employerMapper.findById(id);
    }

    public EmployerMapper getEmployerMapper() {
        return employerMapper;
    }

    public void setEmployerMapper(final EmployerMapper employerMapper) {
        this.employerMapper = employerMapper;
    }
}
