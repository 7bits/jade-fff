package com.recruiters.service;

import com.recruiters.model.Applicant;
import com.recruiters.model.Bid;
import com.recruiters.model.Deal;
import com.recruiters.model.Recruiter;
import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.repository.ApplicantRepository;
import com.recruiters.repository.BidRepository;
import com.recruiters.repository.DealRepository;
import com.recruiters.repository.EmployerRepository;
import com.recruiters.repository.FileRepository;
import com.recruiters.repository.RecruiterRepository;
import com.recruiters.repository.UserRepository;
import com.recruiters.repository.VacancyRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 */
@Service
public class RecruiterService {

    /** Files Repository provides files manipulation methods */
    @Autowired
    private FileRepository fileRepository = null;
    /** Vacancies Repository provides Vacancy DAO */
    @Autowired
    private VacancyRepository vacancyRepository = null;
    /** Vacancies Repository provides Deal DAO */
    @Autowired
    private DealRepository dealRepository = null;
    /** Vacancies Repository provides Applicant DAO */
    @Autowired
    private ApplicantRepository applicantRepository = null;
    /** Vacancies Repository provides Employer DAO */
    @Autowired
    private EmployerRepository employerRepository = null;
    /** Vacancies Repository provides User DAO */
    @Autowired
    private UserRepository userRepository = null;
    /** Vacancies Repository provides Recruiter DAO */
    @Autowired
    private RecruiterRepository recruiterRepository = null;
    /** Vacancies Repository provides Bid DAO */
    @Autowired
    private BidRepository bidRepository = null;
    /** Logger */
    private final Logger log = Logger.getLogger(RecruiterService.class);
    /** Default message for ServiceException */
    private static final String SERVICE_EXCEPTION_MESSAGE = "Recruiter Service general exception: ";
    /** Default message for NotAffiliatedException, part 1 */
    private static final String SECURITY_EXCEPTION_MESSAGE_PART1 = "Recruiter Service security exception: ";
    /** Default message for NotAffiliatedException, part 2 */
    private static final String SECURITY_EXCEPTION_MESSAGE_PART2 = " belongs to different recruiter";
    /** Default message for NotFoundException, part 1 */
    private static final String NOT_FOUND_EXCEPTION_MESSAGE_PART1 = "Recruiter Service not found exception: ";
    /** Default message for NotFoundException, part 2 */
    private static final String NOT_FOUND_EXCEPTION_MESSAGE_PART2 = " was not found";

    /**
     * Find and return Recruiter instance by its id
     * @param id    Id of Recruiter
     * @return Recruiter instance
     * @throws ServiceException if cannot obtain Recruiter instance from
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
     * Find and return all vacancies for certain recruiter
     * @param recruiterId    Id of recruiter
     * @return List of all Vacancies which is available for bidding by
     * this recruiter
     * @throws ServiceException if cannot obtain Vacancy instances from
     * repository or any other possible error
     */
    public List<Vacancy> findAvailableVacanciesForRecruiter(final Long recruiterId)
            throws ServiceException {
        try {
            return vacancyRepository.findAvailableVacanciesForRecruiter(recruiterId);
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Find and return Vacancy instance by its id
     * @param vacancyId    Id of vacancy for which we want to get
     *                     full description
     * @return Vacancy instance
     * @throws ServiceException if cannot obtain Vacancy instance from
     * repository or any other possible error
     */
    public Vacancy findVacancy(final Long vacancyId)
            throws ServiceException, NotFoundException {
        Vacancy vacancy;
        try {
            vacancy = vacancyRepository.findById(vacancyId);
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
     */
    public Bid findActiveBid(final Long bidId, final Long recruiterId)
            throws ServiceException, NotAffiliatedException, NotFoundException {
        Bid bid;
        try {
            bid = bidRepository.findActiveBidById(bidId, recruiterId);
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
     * Find and return list with all active deals for recruiter
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
     * Add new applicant to Deal, verifying deal belongs to recruiter
     * requested apply
     * @param applicant         Applicant instance
     * @param resumeFile        Applicant Resume File
     * @param testAnswerFile    Test Answers File
     * @param recruiterId       Id of recruiter
     * @return Applicant instance if applicant deal belongs to
     * recruiter requested apply and there were no any technical issues
     * @throws NotAffiliatedException if applicant deal not belongs to
     * recruiter requested method
     * @throws ServiceException if Repository cannot process request
     * or any other possible error
     */
    public Applicant saveApplicant(
            final Applicant applicant,
            final MultipartFile resumeFile,
            final MultipartFile testAnswerFile,
            final Long recruiterId
    ) throws NotAffiliatedException, ServiceException {
        Deal deal;
        try {
            deal = dealRepository.findById(applicant.getDeal().getId());
            if (deal.getRecruiter().getId().equals(recruiterId)) {
                /* TODO: Make FileService instead of FileRepository and use it in Web-layer.
                Use file names at this method */
                String fileNameForResume =  this.getFileRepository().saveFile(resumeFile);
                String fileNameForTestAnswers = this.getFileRepository().saveFile(testAnswerFile);
                applicant.setResumeFile(fileNameForResume);
                applicant.setTestAnswerFile(fileNameForTestAnswers);
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
     */
    public Long applyRecruiterToVacancy(
            final Long recruiterId,
            final Long vacancyId,
            final String message
    ) throws ServiceException {
        try {
            return bidRepository.create(recruiterId, vacancyId, message);
        } catch (Exception e) {
            log.error(SERVICE_EXCEPTION_MESSAGE, e);
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
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

    public FileRepository getFileRepository() {
        return fileRepository;
    }

    public void setFileRepository(final FileRepository fileRepository) {
        this.fileRepository = fileRepository;
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
}
