package com.recruiters.service;

import com.recruiters.model.Applicant;
import com.recruiters.model.ApplicantStatus;
import com.recruiters.model.Bid;
import com.recruiters.model.BidStatus;
import com.recruiters.model.Deal;
import com.recruiters.model.Employer;
import com.recruiters.model.Vacancy;
import com.recruiters.repository.ApplicantRepository;
import com.recruiters.repository.BidRepository;
import com.recruiters.repository.DealRepository;
import com.recruiters.repository.EmployerRepository;
import com.recruiters.repository.UserRepository;
import com.recruiters.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Class for Employer
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class EmployerService {

    @Autowired
    private EmployerRepository employerRepository = null;
    @Autowired
    private DealRepository dealRepository = null;
    @Autowired
    private ApplicantRepository applicantRepository = null;
    @Autowired
    private BidRepository bidRepository = null;
    @Autowired
    private VacancyRepository vacancyRepository = null;
    @Autowired
    private UserRepository userRepository = null;

    /**
     * Get all active deals from DB for current employer
     * @param employerId    Employer ID
     * @return list of deals
     */
    public List<Deal> findDealsForEmployer(final Long employerId) {

        return dealRepository.findActiveDealsByEmployerId(employerId);
    }

    /**
     * Returns deal for employer by its Id, using Employer instance as security measures
     * @param dealId      Id of deal
     * @param employerId    Id of employer
     * @return POJO Deal instance
     */
    public Deal findDeal(final Long dealId, final Long employerId) {

        return dealRepository.findByIdAndEmployerId(dealId, employerId);
    }

    /**
     * Get applicant by its id and return it if it's related to current employer
     * otherwise return null
     * @param applicantId    Id of applicant
     * @param employerId     Id of employer
     * @return Applicant POJO instance
     */
    public Applicant findApplicant(final Long applicantId, final Long employerId) {

        Applicant applicant = applicantRepository.findById(applicantId);
        if (applicant.getDeal().getVacancy().getEmployer().getId().equals(employerId)) {
            return applicant;
        }
        return null;
    }

    /**
     * Get bids for exact vacancy from DB for current employer
     * @param vacancyId   Id of vacancy
     * @param employerId    Employer ID
     * @return list of bids
     */
    public List<Bid> findBidsForVacancy(final Long vacancyId, final Long employerId) {

        Vacancy vacancy = vacancyRepository.findById(vacancyId);
        if (vacancy.getEmployer().getId().equals(employerId)) {
            return bidRepository.findBidsByVacancyId(vacancyId);
        }
        return null;
    }

    /**
     * Get bid by its id, employer verification required
     * @param bidId       Id of bid
     * @param employerId    Employer ID
     * @return Bid POJO instance
     */
    public Bid findBid(final Long bidId, final Long employerId) {

        Bid bid = bidRepository.findById(bidId);
        if (bid.getVacancy().getEmployer().getId().equals(employerId)) {
            return bid;
        }
        return null;
    }

    /**
     * Find all vacancies for exact employer with count of bids for each
     * @param employerId    Employer ID
     * @return List of vacancies
     */
    public List<Vacancy> findVacanciesForEmployer(final Long employerId) {

        return vacancyRepository.findVacanciesByEmployerId(employerId);
    }

    /**
     * Get Vacancy by its id if its related to exact employer
     * @param vacancyId    Id of vacancy
     * @param employerId     Employer ID
     * @return Vacancy POJO instance
     */
    public Vacancy findVacancy(final Long vacancyId, final Long employerId) {
        Vacancy vacancy = vacancyRepository.findById(vacancyId);
        if (vacancy.getEmployer().getId().equals(employerId)) {
            return vacancy;
        }
        return null;
    }

    public Boolean approveBidForRecruiter(final Long bidId) {
        Boolean success = this.dealRepository.create(bidId);
        if (success) {
                success = this.bidRepository.updateStatus(bidId, BidStatus.APPROVED);
        }

        return success;
    }

    public Boolean declineBidForRecruiter(final Long bidId) {
        try {
            this.bidRepository.updateStatus(bidId, BidStatus.REJECTED);
        } catch (Exception e) {

        }

        return true;
    }

    /**
     * Saving employer profile
     * @param employer    Employer POJO instance
     * @return true if update is ok, otherwise false
     */
    public Boolean saveProfileForEmployer(final Employer employer) {

        return userRepository.update(employer.getUser());
    }

    /**
     * Finds Employer POJO instance by its  id
     * @param employerId    Employer Id
     * @return Employer POJO instance
     */
    public Employer findEmployer(final Long employerId) {

        return employerRepository.findById(employerId);
    }

    /**
     * Apply Applicant
     * @param applicantId      Id of applicant
     * @param employerId       Id of employer
     * @return true if success, otherwise false
     */
    public Boolean applyApplicant(final Long applicantId, final Long employerId) {
        //TODO deal should change its state or smth.

        return applicantRepository.updateStatus(applicantId, ApplicantStatus.APPROVED, employerId);
    }

    /**
     * Decline Applicant
     * @param applicantId      Id of applicant
     * @param employerId       Id of employer
     * @return true if success, otherwise false
     */
    public Boolean declineApplicant(final Long applicantId, final Long employerId) {

        return applicantRepository.updateStatus(applicantId, ApplicantStatus.REJECTED, employerId);
    }
}
