package com.recruiters.service;

import com.recruiters.model.Applicant;
import com.recruiters.model.Attachment;
import com.recruiters.model.Bid;
import com.recruiters.model.ChatMessage;
import com.recruiters.model.Deal;
import com.recruiters.model.status.DealStatus;
import com.recruiters.model.Recruiter;
import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.repository.ApplicantRepository;
import com.recruiters.repository.AttachmentRepository;
import com.recruiters.repository.BidRepository;
import com.recruiters.repository.ChatRepository;
import com.recruiters.repository.DealRepository;
import com.recruiters.repository.EmployerRepository;
import com.recruiters.repository.RecruiterRepository;
import com.recruiters.repository.UserRepository;
import com.recruiters.repository.VacancyRepository;
import com.recruiters.service.exception.NotAffiliatedException;
import com.recruiters.service.exception.NotFoundException;
import com.recruiters.service.exception.ServiceException;
import com.recruiters.web.form.VacanciesFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Locale;

/**
 * Service for Recruiter
 */
@Service
public class RecruiterService {

    /** Files Repository provides files manipulation methods */
    @Autowired
    private AttachmentRepository attachmentRepository = null;
    /** Vacancies Repository provides Vacancy DAO */
    @Autowired
    private VacancyRepository vacancyRepository = null;
    /** Deal Repository provides Deal DAO */
    @Autowired
    private DealRepository dealRepository = null;
    /** Applicant Repository provides Applicant DAO */
    @Autowired
    private ApplicantRepository applicantRepository = null;
    /** Employer Repository provides Employer DAO */
    @Autowired
    private EmployerRepository employerRepository = null;
    /** User Repository provides User DAO */
    @Autowired
    private UserRepository userRepository = null;
    /** Recruiter Repository provides Recruiter DAO */
    @Autowired
    private RecruiterRepository recruiterRepository = null;
    /** Bid Repository provides Bid DAO */
    @Autowired
    private BidRepository bidRepository = null;
    /** Chat Repository provides Chat DAO */
    @Autowired
    private ChatRepository chatRepository = null;
    /** Message source */
    @Autowired
    private MessageSource messageSource = null;
    /** Logger */
    private final Logger log = Logger.getLogger(RecruiterService.class);
    /** Default message for ServiceException */
    private static final String SERVICE_EXCEPTION_MESSAGE = "Recruiter Service general exception: ";
    /** Default message for NotAffiliatedException, part 1 */
    private static final String SECURITY_EXCEPTION_MESSAGE_PART1 = "Recruiter Service security exception: ";
    /** Default message for NotAffiliatedException, part 2 */
    private static final String SECURITY_EXCEPTION_MESSAGE_PART2 = " belongs to different recruiter or not allowed";
    /** Default message for NotFoundException, part 1 */
    private static final String NOT_FOUND_EXCEPTION_MESSAGE_PART1 = "Recruiter Service not found exception: ";
    /** Default message for NotFoundException, part 2 */
    private static final String NOT_FOUND_EXCEPTION_MESSAGE_PART2 = " was not found";

    /**
     * Find and return Recruiter instance by its id
     * @param id    Id of Recruiter
     * @return Recruiter instance
     * @throws com.recruiters.service.exception.ServiceException if cannot obtain Recruiter instance from
     * repository or any other possible error
     */
    public Recruiter findRecruiter(final Long id) throws ServiceException {
        try {
            return recruiterRepository.findById(id);
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Find and return filtered vacancies list for certain recruiter
     * @param recruiterId        Id of recruiter
     * @param vacanciesFilter    Vacancies Filter object
     * @return List of filtered Vacancies which is available for bidding by
     * this recruiter
     * @throws ServiceException if cannot obtain Vacancy instances from
     * repository or any other possible error
     */
    public List<Vacancy> findFilteredVacanciesForRecruiter(
            final Long recruiterId,
            final VacanciesFilter vacanciesFilter
    ) throws ServiceException {
        try {
            return vacancyRepository.findFilteredVacanciesForRecruiter(
                    recruiterId,
                    vacanciesFilter.getDate(),
                    vacanciesFilter.getSearchText(),
                    vacanciesFilter.getListSpecifications()
            );
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Find and return Vacancy instance by its id and recruiter id
     * to assign bids of this recruiter or deals of any recruiter if found
     * @param vacancyId    Id of vacancy for which we want to get
     *                     full description
     * @param recruiterId  Id of recruiter
     * @return Vacancy instance
     * @throws ServiceException if cannot obtain Vacancy instance from
     * repository or any other possible error
     * @throws com.recruiters.service.exception.NotFoundException if Vacancy was not found
     */
    public Vacancy findVacancy(final Long vacancyId, final Long recruiterId)
            throws ServiceException, NotFoundException {
        Vacancy vacancy;
        try {
            vacancy = vacancyRepository.findByIdAndRecruiterId(vacancyId, recruiterId);
            if (vacancy != null) {
                return vacancy;
            }
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        String notFoundMessage = NOT_FOUND_EXCEPTION_MESSAGE_PART1 + Vacancy.class.getSimpleName() +
                NOT_FOUND_EXCEPTION_MESSAGE_PART2;
        log.error(notFoundMessage);
        throw new NotFoundException(notFoundMessage);
    }

    /**
     * Find and return active Bid by id and recruiter id
     * @param bidId Id of bid
     * @param recruiterId Id of recruiter requesting information
     * @return Bid instance
     * @throws ServiceException if cannot obtain Bid instance from
     * repository or any other possible error
     * @throws NotFoundException if Bid was not found
     * @throws com.recruiters.service.exception.NotAffiliatedException if Bid does not belong to exact recruiter
     */
    public Bid findActiveBid(final Long bidId, final Long recruiterId)
            throws ServiceException, NotAffiliatedException, NotFoundException {
        Bid bid;
        try {
            bid = bidRepository.findById(bidId);
            if (bid != null) {
                if (bid.getRecruiter().getId().equals(recruiterId)) {
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
     * Find and return list of all recruiter bids
     * @param recruiterId    Id of recruiter
     * @return List with all Bid instances made by recruiter
     * @throws ServiceException if cannot obtain Bids instances from
     * repository or any other possible error
     */
    public List<Bid> findBidsForRecruiter(final Long recruiterId)
            throws ServiceException {
        try {
            return bidRepository.findBidsByRecruiterId(recruiterId);
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Find and return list with all deals for recruiter
     * @param recruiterId    Id of recruiter
     * @return list with all active deals for certain recruiter
     * @throws ServiceException if cannot obtain Deals from repository
     * or any other possible error
     */
    public List<Deal> findActiveDealsForRecruiter(final Long recruiterId)
            throws ServiceException {
        try {
            return dealRepository.findActiveDealsByRecruiterId(recruiterId);
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Find and return certain deal, verifying it belongs to recruiter
     * requested it
     * @param dealId         Id of deal
     * @param recruiterId    Id of recruiter obtaining information
     * @return Deal instance if Deal requested belongs to recruiter
     * requested it and there were no technical issues
     * @throws NotFoundException if Deal was not found
     * @throws NotAffiliatedException if deal requested not belongs to
     * recruiter requested it
     * @throws ServiceException if cannot obtain Deal instance from
     * repository or any other possible error
     */
    public Deal findDealForRecruiter(final Long dealId, final Long recruiterId)
            throws NotAffiliatedException, ServiceException, NotFoundException {
        Deal deal;
        try {
            deal = dealRepository.findById(dealId);
            if (deal != null) {
                if (deal.getRecruiter().getId().equals(recruiterId)) {
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
     * Find and return applicant, verifying it belongs to recruiter
     * requested it
     * @param applicantId    Id of applicant
     * @param recruiterId    Id of recruiter
     * @return Applicant instance if certain applicant belongs to
     * recruiter requested it and there were no any technical issues
     * @throws NotFoundException if applicant was not found
     * @throws NotAffiliatedException if applicant requested not belongs to
     * recruiter requested it
     * @throws ServiceException if cannot obtain Applicant instance from
     * repository or any other possible error
     */
    public Applicant findApplicant(final Long applicantId, final Long recruiterId)
            throws NotAffiliatedException, ServiceException, NotFoundException {
        Applicant applicant;
        try {
            applicant = applicantRepository.findById(applicantId);
            if (applicant != null) {
                if (applicant.getDeal().getRecruiter().getId().equals(recruiterId)) {
                    return applicant;
                }
            }
        } catch (Exception e) {
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
     * Find and return attachment, verifying it belongs to recruiter
     * requested it
     * @param attachmentId    Id of attachment
     * @param recruiterId     Id of recruiter
     * @return Attachment instance if certain attachment belongs to
     * recruiter requested it and there were no any technical issues
     * @throws NotFoundException if attachment was not found
     * @throws NotAffiliatedException if attachment requested not belongs to
     * recruiter requested it
     * @throws ServiceException if cannot obtain Attachment instance from
     * repository or any other possible error
     */
    public Attachment findAttachment(final Long attachmentId, final Long recruiterId)
            throws NotAffiliatedException, ServiceException, NotFoundException {
        Attachment attachment;
        try {
            attachment = attachmentRepository.findById(attachmentId);
            if (attachment != null) {
                if (attachment.getRecruiter() == null) {
                    return attachment;
                } else if (attachment.getRecruiter().getId().equals(recruiterId)) {
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
     * @param recruiterId    Id of recruiter
     * @return list of messages if related deal belongs to
     * recruiter requested it and there were no any technical issues
     * @throws NotAffiliatedException if related deal not belongs to
     * recruiter requested it
     * @throws ServiceException if cannot obtain messages from
     * repository or any other possible error
     */
    public List<ChatMessage> findMessages(final Long dealId, final Long messageId, final Long recruiterId)
            throws NotAffiliatedException, ServiceException {
        Deal deal;
        try {
            deal = dealRepository.findById(dealId);
            if (deal.getRecruiter().getId().equals(recruiterId)) {
                return chatRepository.findByDealIdSinceId(dealId, messageId);
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
     * Add new applicant to Deal, verifying deal belongs to recruiter
     * requested apply
     * @param applicant         Applicant instance
     * @param resumeFile        Applicant Resume File
     * @param testAnswerFile    Test Answers File
     * @param recruiterId       Id of recruiter
     * @param locale            Locale
     * @return Applicant instance if applicant deal belongs to
     * recruiter requested apply and there were no any technical issues
     * @throws NotAffiliatedException if applicant deal not belongs to
     * recruiter requested method or deal is not "IN_PROGRESS"
     * @throws ServiceException if Repository cannot process request
     * or any other possible error
     */
    public Applicant saveApplicant(
            final Applicant applicant,
            final MultipartFile resumeFile,
            final MultipartFile testAnswerFile,
            final Long recruiterId,
            final Locale locale
    ) throws NotAffiliatedException, ServiceException {
        Deal deal;
        Applicant oldApplicant;
        try {
            deal = dealRepository.findById(applicant.getDeal().getId());

            // We should keep old files if recruiter have not changed it
            if (!applicant.getId().equals(0L)) {
                oldApplicant = applicantRepository.findById(applicant.getId());
                if (oldApplicant.getResumeFile() != null) {
                    applicant.setResumeFile(oldApplicant.getResumeFile());
                }
                if (oldApplicant.getTestAnswerFile() != null) {
                    applicant.setTestAnswerFile(oldApplicant.getTestAnswerFile());
                }
            }

            if (deal.getRecruiter().getId().equals(recruiterId) &&
                    deal.getStatus().equals(DealStatus.IN_PROGRESS)) {

                // Dealing with resume file
                if (!resumeFile.isEmpty()) {
                    Integer extensionStart = resumeFile.getOriginalFilename().lastIndexOf(".");
                    String filename = applicant.getFirstName().substring(0, 1) +
                            "." + applicant.getLastName() +
                            messageSource.getMessage("file.resume.suffix", null, locale) +
                            resumeFile.getOriginalFilename().substring(extensionStart);
                    Attachment fileNameForResume =  this.getAttachmentRepository().save(
                            resumeFile,
                            filename,
                            recruiterId,
                            deal.getVacancy().getEmployer().getId());
                    applicant.setResumeFile(fileNameForResume);
                }

                // Dealing with test anser file
                if (!testAnswerFile.isEmpty()) {
                    Integer extensionStart = testAnswerFile.getOriginalFilename().lastIndexOf(".");
                    String filename = applicant.getFirstName().substring(0, 1) +
                            "." + applicant.getLastName() +
                            messageSource.getMessage("file.testAnswers.suffix", null, locale) +
                            testAnswerFile.getOriginalFilename().substring(extensionStart);
                    Attachment fileNameForTestAnswers = this.getAttachmentRepository().save(
                            testAnswerFile,
                            filename,
                            recruiterId,
                            deal.getVacancy().getEmployer().getId());
                    applicant.setTestAnswerFile(fileNameForTestAnswers);
                }

                if (applicant.getId().equals(0L)) {
                    return this.getApplicantRepository().create(applicant);
                } else {
                    return this.getApplicantRepository().update(applicant);
                }
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
     * Apply recruiter to certain vacancy
     * @param recruiterId    Id of recruiter
     * @param vacancyId      Id of vacancy
     * @param message        Message recruiter is applied with
     * @return Id of bid created, if there were not technical issues
     * @throws ServiceException if Repository cannot process request
     * or any other possible error
     * @throws NotAffiliatedException if this recruiter cannot be applied
     * to this vacancy because there exists deal for this vacancy already
     */
    public Long applyRecruiterToVacancy(
            final Long recruiterId,
            final Long vacancyId,
            final String message
    ) throws ServiceException, NotAffiliatedException {
        Vacancy vacancy;
        try {
            vacancy = vacancyRepository.findByIdAndRecruiterId(vacancyId, recruiterId);
            if (vacancy.getDealId().equals(0L)) {
                return bidRepository.create(recruiterId, vacancyId, message);
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
     * Save recruiter profile
     * @param recruiter      Recruiter instance
     * @param recruiterId    Id of recruiter which wants to save recruiter instance
     * @return recruiter User if recruiter instance belongs to
     * recruiter requested save and there were no any technical issues
     * @throws NotAffiliatedException if recruiter instance not belongs to
     * recruiter requested method
     * @throws ServiceException if Repository cannot process request
     * or any other possible error
     */
    public User saveProfileForRecruiter(final Recruiter recruiter, final Long recruiterId)
            throws ServiceException, NotAffiliatedException {
        try {
            if (recruiter.getId().equals(recruiterId)) {
                return userRepository.update(recruiter.getUser());
            }
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        String securityMessage = SECURITY_EXCEPTION_MESSAGE_PART1 + Recruiter.class.getSimpleName() +
                SECURITY_EXCEPTION_MESSAGE_PART2;
        log.error(securityMessage);
        throw new NotAffiliatedException(securityMessage);
    }

    /**
     * Clear deals (move to archive) in which current recruiter was fired
     * @param recruiterId    Id of recruiter
     * @return recruiter Id
     * @throws ServiceException if cannot perform command with repository
     * or any other possible error
     */
    public Long clearFiredDealsForRecruiter(final Long recruiterId)
            throws ServiceException {
        try {
            return dealRepository.clearFiredByRecruiterId(recruiterId);
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Clear deals (move to archive) in which applicant of current recruiter
     * was approved
     * @param recruiterId    Id of recruiter
     * @return recruiter Id
     * @throws ServiceException if cannot perform command with repository
     * or any other possible error
     */
    public Long clearApprovedDealsForRecruiter(final Long recruiterId)
            throws ServiceException {
        try {
            return dealRepository.clearApprovedByRecruiterId(recruiterId);
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Clear bids (move to archive) in which current recruiter was rejected
     * @param recruiterId    Id of recruiter
     * @return recruiter Id
     * @throws ServiceException if cannot perform command with repository
     * or any other possible error
     */
    public Long clearRejectedBidsForRecruiter(final Long recruiterId)
            throws ServiceException {
        try {
            return bidRepository.clearRejectedByRecruiterId(recruiterId);
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Clear bids (move to archive) in which current recruiter was approved
     * @param recruiterId    Id of recruiter
     * @return recruiter Id
     * @throws ServiceException if cannot perform command with repository
     * or any other possible error
     */
    public Long clearApprovedBidsForRecruiter(final Long recruiterId)
            throws ServiceException {
        try {
            return bidRepository.clearApprovedByRecruiterId(recruiterId);
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Send message
     * @param dealId         Chat related deal
     * @param message        Chat message
     * @param recruiterId    Id of recruiter which wants to send message
     * @return Id of saved message if there were no any technical issues
     * @throws NotAffiliatedException if related deal not belongs to
     * recruiter requested method
     * @throws ServiceException if Repository cannot process request
     * or any other possible error
     */
    public ChatMessage sendMessage(final Long dealId, final String message, final Long recruiterId)
            throws ServiceException, NotAffiliatedException {
        try {
            Deal deal = dealRepository.findById(dealId);
            if (deal.getRecruiter().getId().equals(recruiterId)) {
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setDeal(deal);
                chatMessage.setRecruiter(new Recruiter(recruiterId));
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

    public AttachmentRepository getAttachmentRepository() {
        return attachmentRepository;
    }

    public void setAttachmentRepository(final AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public ApplicantRepository getApplicantRepository() {
        return applicantRepository;
    }

    public void setApplicantRepository(final ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
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

    public RecruiterRepository getRecruiterRepository() {
        return recruiterRepository;
    }

    public void setRecruiterRepository(final RecruiterRepository recruiterRepository) {
        this.recruiterRepository = recruiterRepository;
    }

    public BidRepository getBidRepository() {
        return bidRepository;
    }

    public void setBidRepository(final BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    public ChatRepository getChatRepository() {
        return chatRepository;
    }

    public void setChatRepository(final ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }
}
