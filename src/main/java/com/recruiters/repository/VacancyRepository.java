package com.recruiters.repository;

import com.recruiters.model.Bid;
import com.recruiters.model.Recruiter;
import com.recruiters.model.Vacancy;
import com.recruiters.repository.mapper.VacancyMapper;
import org.apache.commons.lang3.time.DateUtils;
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
    public Vacancy getById(final Long vacancyId) {

        return vacancyMapper.getById(vacancyId);
    }

    /**
     * Method must return all vacancies for this recruiter
     * @return
     */
    public List<Vacancy> findListOfAvailableVacancies() {

        return vacancyMapper.findListOfAvailableVacancies();
    }

    /**
     * Find vacancies of exact employer by its id
     * @param employerId    Id of employer
     * @return List of vacancies
     */
    public List<Vacancy> findEmployerVacancies(final Long employerId) {

        return vacancyMapper.findEmployerVacancies(employerId);
    }

    /**
     * Find vacancies of exact employer by its id
     * with count of bids for each
     * @param employerId    Id of employer
     * @return List of vacancies
     */
    public List<Vacancy> findEmployerVacanciesWithBidCount(final Long employerId) {

        return vacancyMapper.findEmployerVacanciesWithBidCount(employerId);
    }

    public VacancyMapper getVacancyMapper() {
        return vacancyMapper;
    }

    public void setVacancyMapper(final VacancyMapper vacancyMapper) {

        this.vacancyMapper = vacancyMapper;
    }
}