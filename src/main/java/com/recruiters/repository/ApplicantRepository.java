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

    public Applicant findById(final Long applicantId) {

        return applicantMapper.findById(applicantId);
    }

    /**
     * Method must create Applicant model and add it to deal
     * @param applicant
     * @return <tt>true</tt> if model has been saved successful, <tt>false</tt> - otherwise
     */
    public Boolean createApplicant(final Applicant applicant) {
        try {
            applicantMapper.createApplicant(applicant);
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
    public Boolean updateApplicant(final Applicant applicant) {
        try {
            applicantMapper.updateApplicant(applicant);
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
    public Boolean updateApplicantStatus(
            final Long applicantId,
            final ApplicantStatus applicantStatus,
            final Long employerId
    ) {
        try {
            applicantMapper.updateStatus(applicantId, applicantStatus, employerId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ApplicantMapper getApplicantMapper() {
        return applicantMapper;
    }

    public void setApplicantMapper(final ApplicantMapper applicantMapper) {
        this.applicantMapper = applicantMapper;
    }
}
