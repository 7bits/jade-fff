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
 * Some business rules we could resolve using filled models
 */
@Component
public class BusinessRulesService {

    public BusinessRulesService(){}

    /**
     * Test if employer can fire recruiter from exact deal
     * @param deal   Deal
     * @return true if recruiter is fired, otherwise false
     */
    public Boolean canFireRecruiter(final Deal deal) {

        return  (deal.getStatus() == DealStatus.IN_PROGRESS);
    }

    /**
     * Test if exact applicant is new (unseen by employer)
     * @param applicant    Applicant
     * @return true if applicant was not viewed, otherwise false
     */
    public Boolean isApplicantNew(final Applicant applicant) {

        return  (!applicant.getViewed());
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
     * Test if it's allowed to send messages
     * @param deal    Deal
     * @return true if you can send messages to deal
     */
    public Boolean canSendMessage(final Deal deal) {

        return (deal.getStatus() == DealStatus.IN_PROGRESS);
    }

    /**
     * Test if feedback stage started for deal
     * @param deal    Deal
     * @return true if it's feedback stage
     */
    public Boolean isFeedbackStage(final Deal deal) {

        return (deal.getFeedback() != null);
    }

    /**
     * Test if recruiter left feedback
     * @param deal    Deal
     * @return true if recruiter left feedback
     */
    public Boolean recruiterLeftFeedback(final Deal deal) {

        return (deal.getFeedback().getRecruiterFeedback() != null);
    }

    /**
     * Test if employer left feedback
     * @param deal    Deal
     * @return true if employer left feedback
     */
    public Boolean employerLeftFeedback(final Deal deal) {

        return (deal.getFeedback().getEmployerFeedback() != null);
    }

    /**
     * Test if employer can apply bid
     * @param bid    Bid
     * @return true if employer can apply bid
     */
    public Boolean canApplyBid(final Bid bid) {

        return (bid.getStatus().equals(BidStatus.ACTIVE));
    }

    /**
     * Test if employer applied bid
     * @param bid    Bid
     * @return true if employer applied bid
     */
    public Boolean appliedBid(final Bid bid) {

        return (bid.getStatus().equals(BidStatus.APPROVED));
    }

    /**
     * Test if employer can decline bid
     * @param bid    Bid
     * @return true if employer can decline bid
     */
    public Boolean canDeclineBid(final Bid bid) {

        return (bid.getStatus().equals(BidStatus.ACTIVE));
    }


    /**
     * Test if employer declined bid
     * @param bid    Bid
     * @return true if employer declined bid
     */
    public Boolean declinedBid(final Bid bid) {

        return (bid.getStatus().equals(BidStatus.REJECTED));
    }


    /**
     * Test if employer can apply applicant
     * @param applicant    Applicant
     * @param deal         Deal
     * @return true if employer can apply applicant
     */
    public Boolean canApplyApplicant(final Applicant applicant, final Deal deal) {

        return (applicant.getStatus().equals(ApplicantStatus.IN_PROGRESS) &&
                deal.getStatus().equals(DealStatus.IN_PROGRESS));
    }

    /**
     * Test if employer applied applicant
     * @param applicant    Applicant
     * @return true if employer applied applicant
     */
    public Boolean appliedApplicant(final Applicant applicant) {

        return (applicant.getStatus().equals(ApplicantStatus.APPROVED));
    }

    /**
     * Test if employer can decline applicant
     * @param applicant    Applicant
     * @param deal         Deal
     * @return true if employer can decline applicant
     */
    public Boolean canDeclineApplicant(final Applicant applicant, final Deal deal) {

        return (applicant.getStatus().equals(ApplicantStatus.IN_PROGRESS) &&
                deal.getStatus().equals(DealStatus.IN_PROGRESS));
    }


    /**
     * Test if employer declined applicant
     * @param applicant    Applicant
     * @return true if employer declined applicant
     */
    public Boolean declinedApplicant(final Applicant applicant) {

        return (applicant.getStatus().equals(ApplicantStatus.REJECTED));
    }
}
