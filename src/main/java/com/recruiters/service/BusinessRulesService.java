package com.recruiters.service;

import com.recruiters.model.Applicant;
import com.recruiters.model.status.ApplicantStatus;
import com.recruiters.model.Bid;
import com.recruiters.model.status.BidStatus;
import com.recruiters.model.Deal;
import com.recruiters.model.status.DealStatus;
import com.recruiters.model.Vacancy;
import org.springframework.stereotype.Component;

/**
 * Helper implementing checking some conditions for use in templates
 */
@Component
public class BusinessRulesService {

    public BusinessRulesService(){}

    /**
     * Test if recruiter status is fired for exact deal
     * @param deal    Deal
     * @return true if recruiter is fired, otherwise false
     */
    public Boolean isRecruiterFired(final Deal deal) {

        return  (deal.getStatus() == DealStatus.FIRED);
    }

    /**
     * Test if employer can fire recruiter from exact deal
     * @param deal   Deal
     * @return true if recruiter is fired, otherwise false
     */
    public Boolean canFireRecruiter(final Deal deal) {

        return  (deal.getStatus() == DealStatus.IN_PROGRESS);
    }

    /**
     * Test if exact applicant in deal is editable
     * @param applicant    Applicant
     * @param deal         Deal
     * @return true if it's allowed to edit applicant, otherwise false
     */
    public Boolean isApplicantEditable(final Applicant applicant, final Deal deal) {

        Boolean isApplicantActive = false;
        if (applicant.getStatus() == ApplicantStatus.IN_PROGRESS) {
            isApplicantActive = true;
        }
        Boolean isDealActive = false;
        if (deal.getStatus() == DealStatus.IN_PROGRESS) {
            isDealActive = true;
        }

        return  (isApplicantActive && isDealActive);
    }

    /**
     * Test if it's allowed to add new applicants for exact deal
     * @param deal    Deal
     * @return true if you can add new applicants to this deal,
     * otherwise false
     */
    public Boolean canAddApplicant(final Deal deal) {

        return (deal.getStatus() == DealStatus.IN_PROGRESS);
    }

    /**
     * Test if recruiter can apply to this vacancy
     * @param vacancy     Vacancy
     * @return if recruiter can apply to vacancy
     */
    public Boolean canApplyToVacancy(final Vacancy vacancy) {
        Boolean bidIdIsNull = vacancy.getBidId().equals(0L);
        Boolean dealIdIsNull = vacancy.getDealId().equals(0L);

        return (bidIdIsNull && dealIdIsNull);
    }

    /**
     * Test if employer could apply or decline recruiter bid
     * @param bid    bid
     * @return whether employer can modify recruiter state or not
     */
    public Boolean canModifyRecruiterBid(final Bid bid) {

        Boolean bidIsActive = bid.getStatus() == BidStatus.ACTIVE;
        Boolean thereAreNoDeal = bid.getDealId().equals(0L);
        return (bidIsActive && thereAreNoDeal);
    }
}
