package com.recruiters.service;

import com.recruiters.model.Applicant;
import com.recruiters.model.ApplicantStatus;
import com.recruiters.model.Bid;
import com.recruiters.model.BidStatus;
import com.recruiters.model.Deal;
import com.recruiters.model.DealStatus;
import com.recruiters.model.Employer;
import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.model.VacancyStatus;
import com.recruiters.repository.ApplicantRepository;
import com.recruiters.repository.BidRepository;
import com.recruiters.repository.DealRepository;
import com.recruiters.repository.EmployerRepository;
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
 * Service for Employer
 */
@Service
public class EmployerService {

    /** Vacancies Repository provides Vacancy DAO */
    @Autowired
    private VacancyRepository vacancyRepository = null;
    /** Deal Repository provides Deal DAO */
    @Autowired
    private DealRepository dealRepository = null;
    /** Bid Repository provides Bid DAO */
    @Autowired
    private BidRepository bidRepository = null;
    /** Applicant Repository provides Applicant DAO */
    @Autowired
    private ApplicantRepository applicantRepository = null;
    /** Employer Repository provides Employer DAO */
    @Autowired
    private EmployerRepository employerRepository = null;
    /** User Repository provides User DAO */
    @Autowired
    private UserRepository userRepository = null;
    /** Platform transaction Manager */
    @Autowired
    private PlatformTransactionManager txManager = null;
    /** Transaction settings object */
    private DefaultTransactionDefinition employerTx = null;
    /** Logger */
    private final Logger log = Logger.getLogger(EmployerService.class);
    /** Transaction settings name */
    private static final String TX_NAME = "EmployerTxService";
    /** Default message for ServiceException */
    private static final String SERVICE_EXCEPTION_MESSAGE = "Employer Service general exception: ";
    /** Default message for NotAffiliatedException, part 1 */
    private static final String SECURITY_EXCEPTION_MESSAGE_PART1 = "Employer Service security exception: ";
    /** Default message for NotAffiliatedException, part 2 */
    private static final String SECURITY_EXCEPTION_MESSAGE_PART2 = " belongs to different employer";
    /** Default message for NotFoundException, part 1 */
    private static final String NOT_FOUND_EXCEPTION_MESSAGE_PART1 = "Employer Service not found exception: ";
    /** Default message for NotFoundException, part 2 */
    private static final String NOT_FOUND_EXCEPTION_MESSAGE_PART2 = " was not found";


    /**
     * Default constructor
     * Configuring transaction settings here
     */
    public EmployerService() {
        employerTx = new DefaultTransactionDefinition();
        employerTx.setName(TX_NAME);
        employerTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    }

    /**
     * Get all active deals from DB for current employer
     * @param employerId    Id of Employer
     * @return List of Deals for exact Employer
     * @throws ServiceException if cannot obtain list of Deals from
     * repository or any other possible error
     */
    public List<Deal> findDealsForEmployer(final Long employerId)
            throws ServiceException {
        try {

            return dealRepository.findActiveDealsByEmployerId(employerId);
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Return deal for employer by its Id, using Employer instance as security measures
     * @param dealId        Id of deal
     * @param employerId    Id of employer
     * @return POJO Deal instance
     * @throws NotFoundException if Deal was not found
     * @throws NotAffiliatedException if deal requested not belongs to
     * employer requested it
     * @throws ServiceException if cannot obtain Deal instance from
     * repository or any other possible error
     */
    public Deal findDeal(final Long dealId, final Long employerId)
            throws NotAffiliatedException, ServiceException, NotFoundException {
        Deal deal;
        try {
            deal = dealRepository.findById(dealId);
            if (deal != null) {
                if (deal.getVacancy().getEmployer().getId().equals(employerId)) {
                    return deal;
                }
            }
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        if (deal == null) {
            String notFoundMessage = NOT_FOUND_EXCEPTION_MESSAGE_PART1 + Deal.class.getSimpleName() +
                    NOT_FOUND_EXCEPTION_MESSAGE_PART2;
            log.error(notFoundMessage);
            throw new NotFoundException(notFoundMessage);
        }
        String securityMessage = SECURITY_EXCEPTION_MESSAGE_PART1 + Deal.class.getSimpleName() +
                SECURITY_EXCEPTION_MESSAGE_PART2;
        log.error(securityMessage);
        throw new NotAffiliatedException(securityMessage);
    }

    /**
     * Get applicant by its id and return it if it's related to current employer
     * otherwise return null
     * @param applicantId    Id of applicant
     * @param employerId     Id of employer
     * @return Applicant POJO instance
     * @throws NotFoundException if Applicant was not found
     * @throws NotAffiliatedException if applicant requested not belongs to
     * employer requested it
     * @throws ServiceException if cannot obtain Applicant instance from
     * repository or any other possible error
     */
    public Applicant findApplicant(final Long applicantId, final Long employerId)
            throws NotAffiliatedException, ServiceException, NotFoundException {
        Applicant applicant;
        TransactionStatus status = null;
        try {
            status = txManager.getTransaction(employerTx);
            applicant = applicantRepository.findById(applicantId);
            if (applicant != null) {
                if (applicant.getDeal().getVacancy().getEmployer().getId().equals(employerId)) {
                    applicantRepository.setViewed(applicantId);
                    txManager.commit(status);

                    return applicant;
                }
            }
        } catch (Exception e) {
            if (status != null) {
                txManager.rollback(status);
            }
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        if (applicant == null) {
            String notFoundMessage = NOT_FOUND_EXCEPTION_MESSAGE_PART1 + Applicant.class.getSimpleName() +
                    NOT_FOUND_EXCEPTION_MESSAGE_PART2;
            log.error(notFoundMessage);
            throw new NotFoundException(notFoundMessage);
        }
        String securityMessage = SECURITY_EXCEPTION_MESSAGE_PART1 + Applicant.class.getSimpleName() +
                SECURITY_EXCEPTION_MESSAGE_PART2;
        log.error(securityMessage);
        throw new NotAffiliatedException(securityMessage);
    }

    /**
     * Get bids for exact vacancy from DB for current employer
     * @param vacancyId     Id of vacancy
     * @param employerId    Employer Id
     * @return list of bids
     * @throws NotAffiliatedException if Vacancy requested not belongs to
     * employer requested it
     * @throws ServiceException if cannot obtain Vacancy instance from
     * repository or any other possible error
     */
    public List<Bid> findBidsForVacancy(final Long vacancyId, final Long employerId)
            throws NotAffiliatedException, ServiceException {
        Vacancy vacancy;
        try {
            vacancy = vacancyRepository.findById(vacancyId);
            if (vacancy.getEmployer().getId().equals(employerId)) {

                return bidRepository.findBidsByVacancyId(vacancyId);
            }
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        String securityMessage = SECURITY_EXCEPTION_MESSAGE_PART1 + Vacancy.class.getSimpleName() +
                SECURITY_EXCEPTION_MESSAGE_PART2;
        log.error(securityMessage);
        throw new NotAffiliatedException(securityMessage);
    }

    /**
     * Get bid by its id, employer verification required
     * @param bidId         Id of bid
     * @param employerId    Employer Id
     * @return Bid POJO instance
     * @throws NotFoundException if bid was not found
     * @throws NotAffiliatedException if bid requested not belongs to
     * employer requested it
     * @throws ServiceException if cannot obtain Bid instance from
     * repository or any other possible error
     */
    public Bid findBid(final Long bidId, final Long employerId)
            throws NotAffiliatedException, ServiceException, NotFoundException {
        Bid bid;
        try {
            bid = bidRepository.findById(bidId);
            if (bid != null) {
                if (bid.getVacancy().getEmployer().getId().equals(employerId)) {

                    return bid;
                }
            }
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        if (bid == null) {
            String notFoundMessage = NOT_FOUND_EXCEPTION_MESSAGE_PART1 + Bid.class.getSimpleName() +
                    NOT_FOUND_EXCEPTION_MESSAGE_PART2;
            log.error(notFoundMessage);
            throw new NotFoundException(notFoundMessage);
        }
        String securityMessage = SECURITY_EXCEPTION_MESSAGE_PART1 + Bid.class.getSimpleName() +
                SECURITY_EXCEPTION_MESSAGE_PART2;
        log.error(securityMessage);
        throw new NotAffiliatedException(securityMessage);
    }

    /**
     * Find all vacancies for exact employer with count of bids for each
     * @param employerId    Employer Id
     * @return List of vacancies
     * @throws ServiceException if cannot obtain vacancies from
     * repository or any other possible error
     */
    public List<Vacancy> findVacanciesForEmployer(final Long employerId)
            throws ServiceException {
        try {
            return vacancyRepository.findVacanciesByEmployerId(employerId);
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Get Vacancy by its id if its related to exact employer
     * @param vacancyId      Id of vacancy
     * @param employerId     Employer Id
     * @return Vacancy POJO instance
     * @throws NotFoundException if Vacancy was not found
     * @throws NotAffiliatedException if Vacancy requested not belongs to
     * employer requested it
     * @throws ServiceException if cannot obtain Vacancy instance from
     * repository or any other possible error
     */
    public Vacancy findVacancy(final Long vacancyId, final Long employerId)
            throws NotAffiliatedException, ServiceException, NotFoundException {
        Vacancy vacancy;
        try {
            vacancy = vacancyRepository.findById(vacancyId);
            if (vacancy != null) {
                if (vacancy.getEmployer().getId().equals(employerId)) {
                    return vacancy;
                }
            }
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        if (vacancy == null) {
            String notFoundMessage = NOT_FOUND_EXCEPTION_MESSAGE_PART1 + Vacancy.class.getSimpleName() +
                    NOT_FOUND_EXCEPTION_MESSAGE_PART2;
            log.error(notFoundMessage);
            throw new NotFoundException(notFoundMessage);
        }
        String securityMessage = SECURITY_EXCEPTION_MESSAGE_PART1 + Vacancy.class.getSimpleName() +
                SECURITY_EXCEPTION_MESSAGE_PART2;
        log.error(securityMessage);
        throw new NotAffiliatedException(securityMessage);
    }

    /**
     * Approve bid for Recruiter. Creates appropriate deal.
     * @param bidId         Id of bid
     * @param employerId    Id of employer
     * @return id of created deal
     * @throws NotAffiliatedException if bid not belongs to
     * employer requested method
     * @throws ServiceException if Repository cannot process request
     * or any other possible error
     */
    public Long approveBidForRecruiter(final Long bidId, final Long employerId)
            throws NotAffiliatedException, ServiceException {
        TransactionStatus status = null;
        Bid bid;
        try {
            bid = bidRepository.findById(bidId);
            if (bid.getVacancy().getEmployer().getId().equals(employerId)) {
                status = txManager.getTransaction(employerTx);
                this.dealRepository.create(bidId);
                this.bidRepository.updateStatus(bidId, BidStatus.APPROVED);
                txManager.commit(status);
                return bidId;
            }
        } catch (Exception e) {
            if (status != null) {
                txManager.rollback(status);
            }
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        String securityMessage = SECURITY_EXCEPTION_MESSAGE_PART1 + Bid.class.getSimpleName() +
                SECURITY_EXCEPTION_MESSAGE_PART2;
        log.error(securityMessage);
        throw new NotAffiliatedException(securityMessage);
    }

    /**
     * Decline recruiter bid.
     * @param bidId         Id of bid
     * @param employerId    Id of employer
     * @return id of bid
     * @throws NotAffiliatedException if bid not belongs to
     * employer requested method
     * @throws ServiceException if Repository cannot process request
     * or any other possible error
     */
    public Long declineBidForRecruiter(final Long bidId, final Long employerId)
            throws NotAffiliatedException, ServiceException {
        Bid bid;
        try {
            bid = bidRepository.findById(bidId);
            if (bid.getVacancy().getEmployer().getId().equals(employerId)) {
                this.bidRepository.updateStatus(bidId, BidStatus.REJECTED);
                return bidId;
            }
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        String securityMessage = SECURITY_EXCEPTION_MESSAGE_PART1 + Bid.class.getSimpleName() +
                SECURITY_EXCEPTION_MESSAGE_PART2;
        log.error(securityMessage);
        throw new NotAffiliatedException(securityMessage);
    }

    /**
     * Saving employer profile
     * @param employer    Employer POJO instance
     * @return true if update is ok, otherwise false
     * @throws NotAffiliatedException if employer object not belongs to
     * employer requested method
     * @throws ServiceException if Repository cannot process request
     * or any other possible error
     */
    public User saveProfileForEmployer(final Employer employer, final Long employerId)
            throws ServiceException, NotAffiliatedException {
        try {
            if (employer.getId().equals(employerId)) {
                return userRepository.update(employer.getUser());
            }
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        String securityMessage = SECURITY_EXCEPTION_MESSAGE_PART1 + Employer.class.getSimpleName() +
                SECURITY_EXCEPTION_MESSAGE_PART2;
        log.error(securityMessage);
        throw new NotAffiliatedException(securityMessage);
    }

    /**
     * Finds Employer POJO instance by its  id
     * @param employerId    Employer Id
     * @return Employer POJO instance
     * @throws ServiceException if Repository cannot process request
     * or any other possible error
     */
    public Employer findEmployer(final Long employerId) throws ServiceException {
        try {
            return employerRepository.findById(employerId);
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Apply Applicant, finishes deal
     * @param applicantId      Id of applicant
     * @param employerId       Id of employer
     * @return true if success, otherwise false
     * @throws NotAffiliatedException if applicant not belongs to
     * employer requested method
     * @throws ServiceException if Repository cannot process request
     * or any other possible error
     */
    public Long applyApplicant(final Long applicantId, final Long employerId)
            throws NotAffiliatedException, ServiceException {
        TransactionStatus status = null;
        Applicant applicant;
        try {
            applicant = applicantRepository.findById(applicantId);
            if (applicant.getDeal().getVacancy().getEmployer().getId().equals(employerId)) {
                status = txManager.getTransaction(employerTx);
                applicantRepository.updateStatus(applicantId, ApplicantStatus.APPROVED);
                vacancyRepository.updateStatus(
                        applicant.getDeal().getVacancy().getId(),
                        VacancyStatus.ARCHIVED
                );
                dealRepository.updateStatus(applicant.getDeal().getId(), DealStatus.APPROVED);
                txManager.commit(status);
                return applicantId;
            }
        } catch (Exception e) {
            if (status != null) {
                txManager.rollback(status);
            }
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        String securityMessage = SECURITY_EXCEPTION_MESSAGE_PART1 + Applicant.class.getSimpleName() +
                SECURITY_EXCEPTION_MESSAGE_PART2;
        log.error(securityMessage);
        throw new NotAffiliatedException(securityMessage);
    }

    /**
     * Decline Applicant
     * @param applicantId      Id of applicant
     * @param employerId       Id of employer
     * @return true if success, otherwise false
     * @throws NotAffiliatedException if applicant not belongs to
     * employer requested method
     * @throws ServiceException if Repository cannot process request
     * or any other possible error
     */
    public Long declineApplicant(final Long applicantId, final Long employerId)
            throws NotAffiliatedException, ServiceException {
        Applicant applicant;
        try {
            applicant = applicantRepository.findById(applicantId);
            if (applicant.getDeal().getVacancy().getEmployer().getId().equals(employerId)) {

                return applicantRepository.updateStatus(applicantId, ApplicantStatus.REJECTED);
            }
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        String securityMessage = SECURITY_EXCEPTION_MESSAGE_PART1 + Applicant.class.getSimpleName() +
                SECURITY_EXCEPTION_MESSAGE_PART2;
        log.error(securityMessage);
        throw new NotAffiliatedException(securityMessage);
    }

    /**
     * Fire recruiter from vacancy
     * @param dealId     Id of deal
     * @param employerId Id of employer
     * @return true if success, otherwise false
     * @throws NotAffiliatedException if deal not belongs to
     * employer requested method
     * @throws ServiceException if Repository cannot process request
     * or any other possible error
     */
    public Long fireRecruiter(final Long dealId, final Long employerId)
            throws NotAffiliatedException, ServiceException {
        Deal deal;
        try {
            deal = dealRepository.findById(dealId);
            if (deal.getVacancy().getEmployer().getId().equals(employerId)) {
                return dealRepository.updateStatus(dealId, DealStatus.FIRED);
            }
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        String securityMessage = SECURITY_EXCEPTION_MESSAGE_PART1 + Deal.class.getSimpleName() +
                SECURITY_EXCEPTION_MESSAGE_PART2;
        log.error(securityMessage);
        throw new NotAffiliatedException(securityMessage);
    }

    public VacancyRepository getVacancyRepository() {
        return vacancyRepository;
    }

    public void setVacancyRepository(final VacancyRepository vacancyRepository) {
        this.vacancyRepository = vacancyRepository;
    }

    public DealRepository getDealRepository() {
        return dealRepository;
    }

    public void setDealRepository(final DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    public BidRepository getBidRepository() {
        return bidRepository;
    }

    public void setBidRepository(final BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    public ApplicantRepository getApplicantRepository() {
        return applicantRepository;
    }

    public void setApplicantRepository(final ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    public EmployerRepository getEmployerRepository() {
        return employerRepository;
    }

    public void setEmployerRepository(final EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
