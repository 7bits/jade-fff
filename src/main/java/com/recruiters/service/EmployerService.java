package com.recruiters.service;

import com.recruiters.model.Applicant;
import com.recruiters.model.Attachment;
import com.recruiters.model.ChatMessage;
import com.recruiters.model.Feedback;
import com.recruiters.model.Recruiter;
import com.recruiters.model.status.ApplicantStatus;
import com.recruiters.model.Bid;
import com.recruiters.model.status.BidStatus;
import com.recruiters.model.Deal;
import com.recruiters.model.status.DealStatus;
import com.recruiters.model.Employer;
import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.model.status.VacancyStatus;
import com.recruiters.repository.ApplicantRepository;
import com.recruiters.repository.BidRepository;
import com.recruiters.repository.ChatRepository;
import com.recruiters.repository.DealRepository;
import com.recruiters.repository.EmployerRepository;
import com.recruiters.repository.AttachmentRepository;
import com.recruiters.repository.FeedbackRepository;
import com.recruiters.repository.RecruiterRepository;
import com.recruiters.repository.UserRepository;
import com.recruiters.repository.VacancyRepository;
import com.recruiters.service.exception.NotAffiliatedException;
import com.recruiters.service.exception.NotFoundException;
import com.recruiters.service.exception.ServiceException;
import com.recruiters.web.form.EmployerDealsFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Service for Employer
 */
@Service
public class EmployerService {

    /** Files Repository provides files manipulation methods */
    @Autowired
    private AttachmentRepository attachmentRepository = null;
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
    /** Recruiter Repository provides Recruiter DAO */
    @Autowired
    private RecruiterRepository recruiterRepository= null;
    /** User Repository provides User DAO */
    @Autowired
    private UserRepository userRepository = null;
    /** Chat Repository provides Chat DAO */
    @Autowired
    private ChatRepository chatRepository = null;
    /** Feedback Repository provides Feedback DAO */
    @Autowired
    private FeedbackRepository feedbackRepository = null;
    /** Platform transaction Manager */
    @Autowired
    private PlatformTransactionManager txManager = null;
    /** Message source */
    @Autowired
    private MessageSource messageSource = null;
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
     * Get filtered list of deals from DB for current employer
     * @param employerId    Id of Employer
     * @param dealsFilter   Deals filter
     * @return List of Deals for exact Employer
     * @throws com.recruiters.service.exception.ServiceException if cannot obtain list of Deals from
     * repository or any other possible error
     */
    public List<Deal> findDealsForEmployer(
            final Long employerId,
            final EmployerDealsFilter dealsFilter
    ) throws ServiceException {
        try {

            return dealRepository.findFilteredDealsByEmployerId(
                    employerId,
                    dealsFilter.getListSpecifications()
            );
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
     * @throws com.recruiters.service.exception.NotFoundException if Deal was not found
     * @throws com.recruiters.service.exception.NotAffiliatedException if deal requested not belongs to
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
                    bidRepository.setViewed(bid.getId());

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
     * Find and return attachment, verifying it belongs to employer
     * requested it
     * @param attachmentId    Id of attachment
     * @param employerId      Id of employer
     * @return Attachment instance if certain attachment belongs to
     * employer requested it and there were no any technical issues
     * @throws NotFoundException if attachment was not found
     * @throws NotAffiliatedException if attachment requested not belongs to
     * employer requested it
     * @throws ServiceException if cannot obtain Attachment instance from
     * repository or any other possible error
     */
    public Attachment findAttachment(final Long attachmentId, final Long employerId)
            throws NotAffiliatedException, ServiceException, NotFoundException {
        Attachment attachment;
        try {
            attachment = attachmentRepository.findById(attachmentId);
            if (attachment != null) {
                if (attachment.getEmployer() == null) {
                    return attachment;
                } else if (attachment.getEmployer().getId().equals(employerId)) {
                    return attachment;
                }
            }
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        if (attachment == null) {
            String notFoundMessage = NOT_FOUND_EXCEPTION_MESSAGE_PART1 + Attachment.class.getSimpleName() +
                    NOT_FOUND_EXCEPTION_MESSAGE_PART2;
            log.error(notFoundMessage);
            throw new NotFoundException(notFoundMessage);
        }
        String securityMessage = SECURITY_EXCEPTION_MESSAGE_PART1 + Attachment.class.getSimpleName() +
                SECURITY_EXCEPTION_MESSAGE_PART2;
        log.error(securityMessage);
        throw new NotAffiliatedException(securityMessage);
    }



    /**
     * Find and return messages from chat related to exact deal
     * @param dealId         Id of deal
     * @param messageId      Starting message id, can be null
     * @param employerId     Id of employer
     * @return list of messages if related deal belongs to
     * employer requested it and there were no any technical issues
     * @throws NotAffiliatedException if related deal not belongs to
     * employer requested it
     * @throws ServiceException if cannot obtain messages from
     * repository or any other possible error
     */
    public List<ChatMessage> findMessages(final Long dealId, final Long messageId, final Long employerId)
            throws NotAffiliatedException, ServiceException {
        Deal deal;
        try {
            // Performance optimization
            List<ChatMessage> messages = chatRepository.findByDealIdSinceId(dealId, messageId);
            if (messages.isEmpty()) {
                return messages;
            }
            deal = dealRepository.findById(dealId);
            if (deal.getVacancy().getEmployer().getId().equals(employerId)) {
                return messages;
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


    /**
     * Approve bid for Recruiter. Creates appropriate deal.
     * @param bidId         Id of bid
     * @param employerId    Id of employer
     * @return id of created deal
     * @throws NotAffiliatedException if bid not belongs to
     * employer requested method or bid is not active and there
     * are no any deal for related vacancy
     * @throws ServiceException if Repository cannot process request
     * or any other possible error
     */
    public Long approveBidForRecruiter(final Long bidId, final Long employerId)
            throws NotAffiliatedException, ServiceException {
        TransactionStatus status = null;
        Bid bid;
        try {
            bid = bidRepository.findById(bidId);
            if (bid.getVacancy().getEmployer().getId().equals(employerId) &&
                    bid.getStatus().equals(BidStatus.ACTIVE) &&
                    bid.getDealId().equals(0L)) {
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
     * Find all deals employer need to leave feedback for
     * @param employerId    Employer Id
     * @return List of deals
     * @throws ServiceException if cannot obtain vacancies from
     * repository or any other possible error
     */
    public List<Deal> findDealsForFeedback(final Long employerId)
            throws ServiceException {
        try {
            return dealRepository.findDealsForEmployerFeedback(employerId);
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Decline recruiter bid.
     * @param bidId         Id of bid
     * @param employerId    Id of employer
     * @return id of bid
     * @throws NotAffiliatedException if bid not belongs to
     * employer requested method or bid is not active and there are no deal for
     * related vacancy
     * @throws ServiceException if Repository cannot process request
     * or any other possible error
     */
    public Long declineBidForRecruiter(final Long bidId, final Long employerId)
            throws NotAffiliatedException, ServiceException {
        Bid bid;
        try {
            bid = bidRepository.findById(bidId);
            if (bid.getVacancy().getEmployer().getId().equals(employerId) &&
                    bid.getStatus().equals(BidStatus.ACTIVE) &&
                    bid.getDealId().equals(0L)) {
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
     * Finds Recruiter for Profile by its  id
     * @param recruiterId    Recruiter Id
     * @return Recruiter POJO instance
     * @throws ServiceException if Repository cannot process request
     * or any other possible error,
     * NotFoundException if recruiter with such id not exists
     */
    public Recruiter findRecruiterForProfile(final Long recruiterId)
            throws ServiceException, NotFoundException {
        try {
            Recruiter recruiter = recruiterRepository.findForProfileById(recruiterId);
            if (recruiter != null) {
                return recruiter;
            }
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        String notFoundMessage = NOT_FOUND_EXCEPTION_MESSAGE_PART1 + Recruiter.class.getSimpleName() +
                NOT_FOUND_EXCEPTION_MESSAGE_PART2;
        log.error(notFoundMessage);
        throw new NotFoundException(notFoundMessage);
    }

    /**
     * Apply Applicant, finishes deal
     * @param applicantId      Id of applicant
     * @param employerId       Id of employer
     * @return true if success, otherwise false
     * @throws NotAffiliatedException if applicant not belongs to
     * employer requested method or deal is not "IN PROGRESS"
     * @throws ServiceException if Repository cannot process request
     * or any other possible error
     */
    public Long applyApplicant(final Long applicantId, final Long employerId)
            throws NotAffiliatedException, ServiceException {
        TransactionStatus status = null;
        Applicant applicant;
        try {
            applicant = applicantRepository.findById(applicantId);
            if (applicant.getDeal().getVacancy().getEmployer().getId().equals(employerId) &&
                    applicant.getDeal().getStatus().equals(DealStatus.IN_PROGRESS)) {
                status = txManager.getTransaction(employerTx);
                applicantRepository.updateStatus(applicantId, ApplicantStatus.APPROVED);
                vacancyRepository.updateStatus(
                        applicant.getDeal().getVacancy().getId(),
                        VacancyStatus.ARCHIVED
                );
                dealRepository.updateStatus(applicant.getDeal().getId(), DealStatus.APPROVED);
                Feedback newFeedback = new Feedback(
                        applicant.getDeal(),
                        applicant.getDeal().getRecruiter(),
                        applicant.getDeal().getVacancy().getEmployer()
                );
                feedbackRepository.create(newFeedback);
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
     * employer requested method or deal is not "IN PROGRESS"
     * @throws ServiceException if Repository cannot process request
     * or any other possible error
     */
    public Long declineApplicant(final Long applicantId, final Long employerId)
            throws NotAffiliatedException, ServiceException {
        Applicant applicant;
        try {
            applicant = applicantRepository.findById(applicantId);
            if (applicant.getDeal().getVacancy().getEmployer().getId().equals(employerId) &&
                    applicant.getDeal().getStatus().equals(DealStatus.IN_PROGRESS)) {

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
     * employer requested method or deal is not "IN PROGRESS"
     * @throws ServiceException if Repository cannot process request
     * or any other possible error
     */
    public Long fireRecruiter(final Long dealId, final Long employerId)
            throws NotAffiliatedException, ServiceException {
        TransactionStatus status = null;
        Deal deal;
        try {
            deal = dealRepository.findById(dealId);
            if (deal.getVacancy().getEmployer().getId().equals(employerId) &&
                    deal.getStatus().equals(DealStatus.IN_PROGRESS)) {
                status = txManager.getTransaction(employerTx);
                vacancyRepository.updateStatus(deal.getVacancy().getId(), VacancyStatus.ARCHIVED);
                Feedback newFeedback = new Feedback(deal, deal.getRecruiter(), deal.getVacancy().getEmployer());
                feedbackRepository.create(newFeedback);
                Long statusId = dealRepository.fireRecruiter(dealId);
                txManager.commit(status);
                return statusId;
            }
        } catch (Exception e) {
            if (status != null) {
                txManager.rollback(status);
            }
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        String securityMessage = SECURITY_EXCEPTION_MESSAGE_PART1 + Deal.class.getSimpleName() +
                SECURITY_EXCEPTION_MESSAGE_PART2;
        log.error(securityMessage);
        throw new NotAffiliatedException(securityMessage);
    }

    /**
     * Save Vacancy no matter it's new or update already existed
     * First one have 0L id
     * @param vacancy     Vacancy instance
     * @param testFile    Test file
     * @param locale      Current locale
     * @return created or updated vacancy if no errors occurs
     * @throws ServiceException if Repository cannot process request
     * or any other possible error
     */
    public Vacancy saveVacancy(
            final Vacancy vacancy,
            final MultipartFile testFile,
            final Locale locale
    ) throws ServiceException {
        try {
            // Dealing with tests file
            if (!testFile.isEmpty()) {
                Integer extensionStart = testFile.getOriginalFilename().lastIndexOf(".");
                String filename = vacancy.getTitle() +
                        messageSource.getMessage("file.tests.suffix", null, locale) +
                        testFile.getOriginalFilename().substring(extensionStart);
                Attachment testsFile =  attachmentRepository.save(
                        testFile,
                        filename,
                        null,
                        vacancy.getEmployer().getId());
                vacancy.setTestFile(testsFile);
            }

            if (vacancy.getId().equals(0L)) {
                return vacancyRepository.create(vacancy);
            } else {
//                return vacancyRepository.update(vacancy);
                return null;
            }
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Send message
     * @param dealId         Chat related deal
     * @param message        Chat message
     * @param employerId     Id of employer who wants to send message
     * @return Id of saved message if there were no any technical issues
     * @throws NotAffiliatedException if related deal not belongs to
     * employer requested method
     * @throws ServiceException if Repository cannot process request
     * or any other possible error
     */
    public ChatMessage sendMessage(final Long dealId, final String message, final Long employerId)
            throws ServiceException, NotAffiliatedException {
        try {
            Deal deal = dealRepository.findById(dealId);
            if (deal.getVacancy().getEmployer().getId().equals(employerId) &&
                    deal.getStatus().equals(DealStatus.IN_PROGRESS)) {
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setDeal(deal);
                chatMessage.setEmployer(new Employer(employerId));
                chatMessage.setMessage(message);

                return chatRepository.create(chatMessage);
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


    /**
     * Leave feedback for exact deal
     * @param dealId                  Related deal
     * @param employerFeedback        Feedback
     * @param employerId              Id of employer which wants to leave feedback
     * @return Filled feedback if there were no any technical issues
     * @throws NotAffiliatedException if related deal not belongs to
     * employer requested method
     * @throws ServiceException if Repository cannot process request
     * or any other possible error
     */
    public Feedback leaveFeedback(final Long dealId, final String employerFeedback, final Long employerId)
            throws ServiceException, NotAffiliatedException {
        try {
            Feedback feedback = feedbackRepository.findByDealId(dealId);
            if (feedback.getEmployer().getId().equals(employerId) &&
                    feedback.getEmployerFeedback() == null) {
                feedbackRepository.updateEmployerFeedback(dealId, employerFeedback);
                feedback.setEmployerFeedback(employerFeedback);
                feedback.setEmployerTime(new Date());
                return feedback;
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

    public AttachmentRepository getAttachmentRepository() {
        return attachmentRepository;
    }

    public void setAttachmentRepository(final AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public ChatRepository getChatRepository() {
        return chatRepository;
    }

    public void setChatRepository(final ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public RecruiterRepository getRecruiterRepository() {
        return recruiterRepository;
    }

    public void setRecruiterRepository(final RecruiterRepository recruiterRepository) {
        this.recruiterRepository = recruiterRepository;
    }
}
