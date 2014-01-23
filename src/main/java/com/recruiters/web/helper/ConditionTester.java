package com.recruiters.web.helper;

import com.recruiters.model.ApplicantStatus;
import com.recruiters.model.DealStatus;
import org.springframework.stereotype.Component;

/**
 * Helper implementing checking some conditions for use in templates
 */
@Component
public class ConditionTester {

    public ConditionTester(){}

    /**
     * Test if recruiter status is fired for exact deal
     * @param dealStatus    Deal status
     * @return true if recruiter is fired, otherwise false
     */
    public Boolean isRecruiterFired(final DealStatus dealStatus) {

        return  (dealStatus == DealStatus.FIRED);
    }

    /**
     * Test if exact applicant in deal is editable
     * @param applicantStatus    Status of applicant
     * @param dealStatus         Status of deal
     * @return true if it's allowed to edit applicant, otherwise false
     */
    public Boolean isApplicantEditable(final ApplicantStatus applicantStatus, final DealStatus dealStatus) {

        Boolean isApplicantActive = false;
        if (applicantStatus == ApplicantStatus.IN_PROGRESS) {
            isApplicantActive = true;
        }
        Boolean isDealActive = false;
        if (dealStatus == DealStatus.IN_PROGRESS) {
            isDealActive = true;
        }
        return  (isApplicantActive && isDealActive);
    }

    /**
     * Test if it's allowed to add new applicants for exact deal
     * @param dealStatus    Status of deal
     * @return true if you can add new applicants to this deal,
     * otherwise false
     */
    public Boolean canAddApplicant(final DealStatus dealStatus) {

        return (dealStatus == DealStatus.IN_PROGRESS);
    }
}
