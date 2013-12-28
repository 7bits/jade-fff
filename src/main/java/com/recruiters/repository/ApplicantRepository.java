package com.recruiters.repository;

import com.recruiters.model.Applicant;
import com.recruiters.model.Deal;
import com.recruiters.repository.mapper.ApplicantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository for working with applicant
 */
@Repository
public class ApplicantRepository {

    @Autowired
    private ApplicantMapper applicantMapper = null;

    public Applicant getApplicantById(final Long applicantId) {

        Applicant applicant = applicantMapper.getApplicantById(applicantId);
        return applicant;
    }

    /**
     *  Method must return list of applicants for given deal
     * @param dealId
     * @return
     */
    public List<Applicant> getApplicantByDealId(final Long dealId) {

        List<Applicant> applicantList = applicantMapper.getApplicantByDealId(dealId);
        return applicantList;
    }

    /**
     * Method must create Applicant model and add it to deal
     * @param applicant
     * @return <tt>true</tt> if model has been saved successful, <tt>false</tt> - otherwise
     */
    public Boolean createApplicant(final Applicant applicant) {
        return true;
    }

    /**
     * Method must update Applicant model added to deal
     * @param applicant
     * @return <tt>true</tt> if model has been saved successful, <tt>false</tt> - otherwise
     */
    public Boolean updateApplicant(final Applicant applicant) {
        return true;
    }

    public ApplicantMapper getApplicantMapper() {
        return applicantMapper;
    }

    public void setApplicantMapper(final ApplicantMapper applicantMapper) {
        this.applicantMapper = applicantMapper;
    }
}
