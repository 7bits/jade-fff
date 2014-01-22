package com.recruiters.repository;

import com.recruiters.model.Vacancy;
import com.recruiters.model.VacancyStatus;
import com.recruiters.repository.mapper.VacancyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Repository implementing all methods related to
 * Vacancy Entity manipulations with Mybatis
 */
@Repository
public class VacancyRepository {

    /** MyBatis Vacancy Mapper */
    @Autowired
    private VacancyMapper vacancyMapper = null;

    /**
     * Find and return Vacancy instance by its id
     * @param vacancyId    Id of vacancy
     * @return Vacancy instance
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public Vacancy findById(final Long vacancyId) throws RepositoryException {
        if (vacancyId == null) {
            throw new RepositoryException("vacancyId is null");
        }
        try {
            return vacancyMapper.findById(vacancyId);
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Find and return List of Vacancies for Recruiter
     * Vacancies that have already Bid or Deal included
     * @param recruiterId    Id of recruiter
     * @return List of vacancies with no bids and deals from certain recruiter
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public List<Vacancy> findAllVacanciesForRecruiter(final Long recruiterId)
            throws RepositoryException {
        if (recruiterId == null) {
            throw new RepositoryException("recruiterId is null");
        }
        try {

            return vacancyMapper.findAllVacanciesForRecruiter(recruiterId);
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Find and return List of Vacancy instances of certain employer
     * with filled number of bids for each of it
     * @param employerId    Id of employer
     * @return List of employers vacancies with count of bids
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public List<Vacancy> findVacanciesByEmployerId(final Long employerId)
            throws RepositoryException {
        if (employerId == null) {
            throw new RepositoryException("employerId is null");
        }
        try {

            return vacancyMapper.findVacanciesByEmployerId(employerId);
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Update Status of Vacancy
     * @param vacancyId    Id of vacancy
     * @param status       New status
     * @return Id of vacancy updated if there were no any technical issues
     * @throws RepositoryException if input parameters are incorrect or there
     * were any technical issues
     */
    public Long updateStatus(final Long vacancyId, final VacancyStatus status)
            throws RepositoryException {
        if (vacancyId == null || status == null) {
            throw new RepositoryException("vacancyId or status is null");
        }
        try {
            vacancyMapper.updateStatus(vacancyId, status);

            return vacancyId;
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    public VacancyMapper getVacancyMapper() {
        return vacancyMapper;
    }

    public void setVacancyMapper(final VacancyMapper vacancyMapper) {

        this.vacancyMapper = vacancyMapper;
    }
}