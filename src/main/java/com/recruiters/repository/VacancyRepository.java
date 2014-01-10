package com.recruiters.repository;

import com.recruiters.model.Vacancy;
import com.recruiters.repository.mapper.VacancyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Repository for working with vacancy
 */
@Repository
public class VacancyRepository {

    @Autowired
    private VacancyMapper vacancyMapper = null;

    /**
     * Method return vacancy by id
     * @param vacancyId
     * @return
     */
    public Vacancy findById(final Long vacancyId) {

        return vacancyMapper.findById(vacancyId);
    }

    /**
     * Method must return all vacancies for this recruiter
     * @return
     */
    public List<Vacancy> findAvailableVacanciesForRecruiter() {

        /*TODO: Use recruiterId to know bids and deals*/
        return vacancyMapper.findAvailableVacanciesForRecruiter();
    }

    /**
     * Find vacancies of exact employer by its id
     * with count of bids for each
     * @param employerId    Id of employer
     * @return List of vacancies
     */
    public List<Vacancy> findVacanciesByEmployerId(final Long employerId) {

        return vacancyMapper.findVacanciesByEmployerId(employerId);
    }

    public VacancyMapper getVacancyMapper() {
        return vacancyMapper;
    }

    public void setVacancyMapper(final VacancyMapper vacancyMapper) {

        this.vacancyMapper = vacancyMapper;
    }
}