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
import com.recruiters.repository.RepositoryException;
import com.recruiters.repository.UserRepository;
import com.recruiters.repository.VacancyRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

/**
 * Service Class for Employer
 */
@Service
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
    @Autowired
    private PlatformTransactionManager txManager = null;
    /** Transaction settings object */
    private DefaultTransactionDefinition def = null;
    /** Logger */
    private final Logger log = Logger.getLogger(EmployerService.class);

    /**
     * Default constructor
     * Configuring default transaction settings here
     */
    public EmployerService() {
        def = new DefaultTransactionDefinition();
        def.setName("EmployerTxService");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    }

    /**
     * Get all active deals from DB for current employer
     * @param employerId    Employer ID
     * @return list of deals
     */
    public List<Deal> findDealsForEmployer(final Long employerId)
            throws ServiceException {
        try {

            return dealRepository.findActiveDealsByEmployerId(employerId);
        } catch (Exception e) {
            log.warn("Service general exception: " + e);
            throw new ServiceException("Service general exception: ", e);
        }
    }

    /**
     * Returns deal for employer by its Id, using Employer instance as security measures
     * @param dealId      Id of deal
     * @param employerId    Id of employer
     * @return POJO Deal instance
     */
    public Deal findDeal(final Long dealId, final Long employerId)
            throws SecurityException, ServiceException {
        try {
            Deal deal = dealRepository.findByIdAndEmployerId(dealId, employerId);
            if (deal != null) {
                return deal;
            }
        } catch (Exception e) {
            log.warn("Service general exception: " + e);
            throw new ServiceException("Service general exception: ", e);
        }
        log.warn("Service security exception: deal belongs to different employer");
        throw new SecurityException("Service security exception: " +
                " deal belongs to different employer");
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
    public List<Bid> findBidsForVacancy(final Long vacancyId, final Long employerId)
            throws  SecurityException, ServiceException {
        try {
            Vacancy vacancy = vacancyRepository.findById(vacancyId);
            if (vacancy.getEmployer().getId().equals(employerId)) {

                return bidRepository.findBidsByVacancyId(vacancyId);
            }
        } catch (Exception e) {
            log.warn("Service general exception: " + e);
            throw new ServiceException("Service general exception: ", e);
        }
        log.warn("Service security exception: bidId and vacancyId belongs to different employers");
        throw new SecurityException("Service security exception: employerId is null");
    }

    /**
     * Get bid by its id, employer verification required
     * @param bidId       Id of bid
     * @param employerId    Employer ID
     * @return Bid POJO instance
     */
    public Bid findBid(final Long bidId, final Long employerId)
            throws SecurityException, ServiceException {
        try {
            Bid bid = bidRepository.findById(bidId);
            if (bid.getVacancy().getEmployer().getId().equals(employerId)) {

                return bid;
            }
        } catch (Exception e) {
            log.warn("Service general exception: " + e);
            throw new ServiceException("Service general exception: ", e);
        }
        log.warn("Service security exception: bidId and employerId belongs to different employers");
        throw new SecurityException("Service security exception: employerId is null");
    }

    /**
     * Find all vacancies for exact employer with count of bids for each
     * @param employerId    Employer ID
     * @return List of vacancies
     */
    public List<Vacancy> findVacanciesForEmployer(final Long employerId)
            throws ServiceException {
        try {

            return vacancyRepository.findVacanciesByEmployerId(employerId);
        } catch (Exception e) {
            log.warn("Service general exception: " + e);
            throw new ServiceException("Service general exception: ", e);
        }
    }

    /**
     * Get Vacancy by its id if its related to exact employer
     * @param vacancyId    Id of vacancy
     * @param employerId     Employer ID
     * @return Vacancy POJO instance
     */
    public Vacancy findVacancy(final Long vacancyId, final Long employerId)
            throws SecurityException, ServiceException {
        try {
            Vacancy vacancy = vacancyRepository.findById(vacancyId);
            if (vacancy.getEmployer().getId().equals(employerId)) {

                return vacancy;
            }
        } catch (Exception e) {
            log.warn("Service general exception: " + e);
            throw new ServiceException("Service general exception: ", e);
        }
        log.warn("Service security exception: vacancyId and employerId belongs to different employers");
        throw new SecurityException("Service security exception: " +
                "vacancyId and employerId belongs to different employers");
    }

    public Long approveBidForRecruiter(final Long bidId, final Long employerId)
            throws SecurityException, ServiceException {
        TransactionStatus status = null;
        try {
            Bid bid = bidRepository.findById(bidId);
            if (bid.getVacancy().getEmployer().getId().equals(employerId)) {
                status = txManager.getTransaction(def);
                this.dealRepository.create(bidId);
                this.bidRepository.updateStatus(bidId, BidStatus.APPROVED);
                txManager.commit(status);
                return bidId;
            }
        } catch (Exception e) {
            if (status != null) {
                txManager.rollback(status);
            }
            log.warn("Service general exception: " + e);
            throw new ServiceException("Service general exception: ", e);
        }
        log.warn("Service security exception: bidId and employerId belongs to different employers");
        throw new SecurityException("Service security exception: " +
                "bidId and employerId belongs to different employers");
    }

    public Long declineBidForRecruiter(final Long bidId, final Long employerId)
            throws SecurityException, ServiceException {
        try {
            Bid bid = bidRepository.findById(bidId);
            if (bid.getVacancy().getEmployer().getId().equals(employerId)) {
                this.bidRepository.updateStatus(bidId, BidStatus.REJECTED);
                return bidId;
            }
        } catch (Exception e) {
            log.warn("Service general exception: " + e);
            throw new ServiceException("Service general exception: ", e);
        }
        log.warn("Service security exception: bidId and employerId belongs to different employers");
        throw new SecurityException("Service security exception: " +
                "bidId and employerId belongs to different employers");
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
