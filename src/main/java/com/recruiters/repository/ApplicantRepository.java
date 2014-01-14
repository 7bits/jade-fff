package com.recruiters.repository;

import com.recruiters.model.Applicant;
import com.recruiters.model.ApplicantStatus;
import com.recruiters.repository.mapper.ApplicantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Repository for working with applicant
 */
@Repository
public class ApplicantRepository {

    @Autowired
    private ApplicantMapper applicantMapper = null;

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
     * Method must create Applicant model and add it to deal
     * @param applicant
     * @return <tt>true</tt> if model has been saved successful, <tt>false</tt> - otherwise
     */
    public Boolean create(final Applicant applicant) {
        try {
            applicantMapper.create(applicant);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Method must update Applicant model added to deal
     * @param applicant
     * @return <tt>true</tt> if model has been saved successful, <tt>false</tt> - otherwise
     */
    public Boolean update(final Applicant applicant) {
        try {
            applicantMapper.update(applicant);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Method update Applicant status
     * @param applicantId        Id of Applicant
     * @param applicantStatus    New status for Applicant
     * @return true if update was successful otherwise false
     */
    public Long updateStatus(
            final Long applicantId,
            final ApplicantStatus applicantStatus,
            final Long employerId
    ) throws RepositoryException {
        if (applicantId == null) {
            throw new RepositoryException("applicantId is null");
        }
        try {
            applicantMapper.updateStatus(applicantId, applicantStatus, employerId);

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
