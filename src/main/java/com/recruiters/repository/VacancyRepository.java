package com.recruiters.repository;

import com.recruiters.model.Vacancy;
import com.recruiters.model.VacancyStatus;
import com.recruiters.repository.mapper.VacancyMapper;
import org.mybatis.spring.MyBatisSystemException;
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
     * Method return vacancy with active bid by id and recruiter id
     * @param vacancyId
     * @param recruiterId
     * @return
     */
    public Vacancy findWithActiveBidByIdAndRecruiterId(
            final Long vacancyId,
            final Long recruiterId
    ) throws RepositoryException {
        if (vacancyId == null || recruiterId == null) {
            throw new RepositoryException("vacancyId or recruiterId is null");
        }
        try {

            return vacancyMapper.findWithActiveBidByIdAndRecruiterId(vacancyId, recruiterId);
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Method must return all vacancies for this recruiter
     * @return
     */
    public List<Vacancy> findAvailableVacanciesForRecruiter(final Long recruiterId)
            throws RepositoryException {
        if (recruiterId == null) {
            throw new RepositoryException("recruiterId is null");
        }
        try {

            return vacancyMapper.findAvailableVacanciesForRecruiter(recruiterId);
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Find vacancies of exact employer by its id
     * with count of bids for each
     * @param employerId    Id of employer
     * @return List of vacancies
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