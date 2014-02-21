package com.recruiters.repository;

import com.recruiters.model.Vacancy;
import com.recruiters.model.status.VacancyStatus;
import com.recruiters.repository.exception.RepositoryException;
import com.recruiters.repository.mapper.VacancyMapper;
import com.recruiters.repository.specification.impl.vacancy.VacancyEmployerListSpecification;
import com.recruiters.repository.specification.impl.vacancy.VacancyRecruiterListSpecification;
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
     * @throws com.recruiters.repository.exception.RepositoryException if input parameter is incorrect or there
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
     * Find and return Vacancy instance by its id
     * and recruiter id (to assign bid id for this recruiter if any
     * and deal id for any recruiter if any)
     * @param vacancyId    Id of vacancy
     * @param recruiterId  Id of recruiter
     * @return Vacancy instance
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public Vacancy findByIdAndRecruiterId(
            final Long vacancyId,
            final Long recruiterId
    ) throws RepositoryException {
        if (vacancyId == null || recruiterId == null) {
            throw new RepositoryException("vacancyId or recruiterId is null");
        }
        try {
            return vacancyMapper.findByIdAndRecruiterId(vacancyId, recruiterId);
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Find and return List of Vacancies for Recruiter
     * Depends on filter properties.
     * @param recruiterId             Id of recruiter
     * @param date                    Date in "YYYY-MM-DD" format
     * @param searchText              Search text, can be empty, so will not be used
     * @param vacancyRecruiterListSpecification    Vacancy List specification
     * @return List of vacancies with certain filter applied
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public List<Vacancy> findFilteredVacanciesForRecruiter(
            final Long recruiterId,
            final String date,
            final String searchText,
            final VacancyRecruiterListSpecification vacancyRecruiterListSpecification
    ) throws RepositoryException {
        if (recruiterId == null || vacancyRecruiterListSpecification == null || date == null) {
            throw new RepositoryException("recruiterId or vacancyRecruiterListSpecification or date is null");
        }
        try {
            // prepare LIKE parameter
            String likeSearchText = null;
            if (searchText != null) {
                if (!searchText.isEmpty()) {
                    likeSearchText = "%" + searchText + "%";
                }
            }
            return vacancyMapper.findFilteredVacanciesForRecruiter(
                    recruiterId, date, likeSearchText, vacancyRecruiterListSpecification
            );
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
     * Find and return List of Vacancies for Employer
     * Depends on filter properties.
     * @param employerId             Id of employer
     * @param vacancyListSpecification    Vacancy List specification
     * @return List of vacancies with certain filter applied
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public List<Vacancy> findFilteredVacanciesForEmployer(
            final Long employerId,
            final VacancyEmployerListSpecification vacancyListSpecification
    ) throws RepositoryException {
        if (employerId == null || vacancyListSpecification == null) {
            throw new RepositoryException("recruiterId or vacancyListSpecification is null");
        }
        try {

            return vacancyMapper.findFilteredVacanciesForEmployer(
                    employerId, vacancyListSpecification
            );
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

    /**
     * Create new vacancy
     * @param vacancy    Vacancy instance
     * @return created vacancy instance if there were no any technical issues
     * @throws RepositoryException if input parameters are incorrect or there
     * were any technical issues
     */
    public Vacancy create(final Vacancy vacancy)
            throws RepositoryException {
        if (vacancy == null) {
            throw new RepositoryException("vacancy is null");
        }
        try {
            Long vacancyId = vacancyMapper.create(vacancy);
            vacancy.setId(vacancyId);

            return vacancy;
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Update existing vacancy
     * @param vacancy    Vacancy instance
     * @return updated vacancy instance if there were no any technical issues
     * @throws RepositoryException if input parameters are incorrect or there
     * were any technical issues
     */
    public Vacancy update(final Vacancy vacancy)
            throws RepositoryException {
        if (vacancy == null) {
            throw new RepositoryException("vacancy is null");
        }
        try {
            vacancyMapper.update(vacancy);

            return vacancy;
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