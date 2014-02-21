package com.recruiters.repository;

import com.recruiters.model.Applicant;
import com.recruiters.model.status.ApplicantStatus;
import com.recruiters.repository.exception.RepositoryException;
import com.recruiters.repository.mapper.ApplicantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository implementing all methods related to
 * Applicant Entity manipulations with MyBatis
 */
@Repository
public class ApplicantRepository {

    /** MyBatis Applicant Mapper */
    @Autowired
    private ApplicantMapper applicantMapper = null;

    /**
     * Find and return Applicant by its id
     * @param applicantId    Id of Applicant
     * @return Applicant POJO instance
     * @throws com.recruiters.repository.exception.RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public Applicant findById(final Long applicantId) throws RepositoryException {
        if (applicantId == null) {
            throw new RepositoryException("applicantId is null");
        }
        try {

            return applicantMapper.findById(applicantId);
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Find and return Applicants unseen but employer
     * @param employerId    Id of employer
     * @return list of matching applicants
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public List<Applicant> findNewApplicantsForEmployer(final Long employerId)
            throws RepositoryException
    {
        if (employerId == null) {
            throw new RepositoryException("employerId is null");
        }
        try {

            return applicantMapper.findNewApplicantsForEmployer(employerId);
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Create new Applicant
     * @param applicant    Applicant POJO instance
     * @return Applicant if it was successfully created
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public Applicant create(final Applicant applicant) throws RepositoryException {
        if (applicant == null) {
            throw new RepositoryException("applicant is null");
        }
        try {

            applicantMapper.create(applicant);
            return applicant;
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Update existing Applicant
     * @param applicant    Applicant POJO instance
     * @return Applicant if it was successfully updated
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public Applicant update(final Applicant applicant) throws RepositoryException {
        if (applicant == null) {
            throw new RepositoryException("applicant is null");
        }
        try {

            applicantMapper.update(applicant);
            return applicant;
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Update status of Applicant
     * @param applicantId        Id of Applicant
     * @param applicantStatus    New status of Applicant
     * @return Id of applicant, which status we've modified
     * @throws RepositoryException if input parameters are incorrect or there
     * were any technical issues
     */
    public Long updateStatus(
            final Long applicantId,
            final ApplicantStatus applicantStatus
    ) throws RepositoryException {
        if (applicantId == null) {
            throw new RepositoryException("applicantId is null");
        }
        try {
            applicantMapper.updateStatus(applicantId, applicantStatus);

            return applicantId;
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Set Applicant viewed state to true
     * @param applicantId    Applicant Id
     * @return Applicant Id if it was successfully updated
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public Long setViewed(final Long applicantId) throws RepositoryException {
        if (applicantId == null) {
            throw new RepositoryException("applicantId is null");
        }
        try {

            applicantMapper.setViewed(applicantId);
            return applicantId;
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    public ApplicantMapper getApplicantMapper() {
        return applicantMapper;
    }

    public void setApplicantMapper(final ApplicantMapper applicantMapper) {
        this.applicantMapper = applicantMapper;
    }
}
