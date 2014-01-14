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
import com.recruiters.repository.RepositoryException;
import com.recruiters.repository.UserRepository;
import com.recruiters.repository.VacancyRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class RecruiterService {

    @Autowired
    private FileRepository fileRepository = null;
    @Autowired
    private VacancyRepository vacancyRepository = null;
    @Autowired
    private DealRepository dealRepository = null;
    @Autowired
    private ApplicantRepository applicantRepository = null;
    @Autowired
    private EmployerRepository employerRepository = null;
    @Autowired
    private UserRepository userRepository = null;
    @Autowired
    private RecruiterRepository recruiterRepository = null;
    @Autowired
    private BidRepository bidRepository = null;
    /** Logger */
    private final Logger log = Logger.getLogger(RecruiterService.class);

    /**
     * Method must return recruiter by given id
     * @param id
     * @return
     */
    public Recruiter findRecruiter(final Long id) {

        return this.getRecruiterRepository().findById(id);
    }

    /**
     * Method must return all vacancies for this recruiter
     * @return
     */
    public List<Vacancy> findAvailableVacanciesForRecruiter(final Long recruiterId)
            throws ServiceException {
        try {

            return vacancyRepository.findAvailableVacanciesForRecruiter(recruiterId);
        } catch (Exception e) {
            log.warn("Recruiter Service general exception: " + e);
            throw new ServiceException("Recruiter Service general exception: ", e);
        }
    }

    /**
     * @param vacancyId    Id of vacancy for which we want to get full description
     * @return vacancy
     */
    public Vacancy findVacancy(final Long vacancyId) {
        try {
            return this.getVacancyRepository().findById(vacancyId);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Method must return list of recruiter bids.
     * @return
     */
    public List<Bid> findBidsForRecruiter(final Long recruiterId)
        throws ServiceException {
            try {

                return bidRepository.findBidsByRecruiterId(recruiterId);
            } catch (Exception e) {
                log.warn("Recruiter Service general exception: " + e);
                throw new ServiceException("Recruiter Service general exception: ", e);
            }
    }

    /**
     * Method must return list of vacancies. Each of vacancies has deal for this recruiter
     * @param recruiterId
     * @return
     */
    public List<Deal> findActiveDealsForRecruiter(final Long recruiterId)
            throws ServiceException {
        try {

            return dealRepository.findActiveDealsByRecruiterId(recruiterId);
        } catch (Exception e) {
            log.warn("Recruiter Service general exception: " + e);
            throw new ServiceException("Recruiter Service general exception: ", e);
        }
    }

    /**
     * Method must return vacancy if it has deal for this recruiter
     * @param dealId  Id of deal
     * @return vacancy description
     */
    public Deal findDealForRecruiter(final Long dealId, final Long recruiterId) {

        return this.getDealRepository().findByDealIdAndRecruiterId(dealId, recruiterId);
    }

    public Applicant findApplicant(final Long applicantId, final Long recruiterId)
            throws SecurityException, ServiceException {
        try {
            Applicant applicant = applicantRepository.findById(applicantId);
            if (applicant.getDeal().getRecruiter().getId().equals(recruiterId)) {
                return applicant;
            }
        } catch (Exception e) {
            log.warn("Recruiter Service general exception: " + e);
            throw new ServiceException("Recruiter Service general exception: ", e);
        }
        log.warn("Recruiter Service security exception: " +
                "applicantId and recruiterId belongs to different recruiter");
        throw new SecurityException("Recruiter Service security exception: " +
                "applicantId and recruiterId belongs to different recruiter");
    }

    /**
     * Method must add new applicant to current vacancy by recruiter
     * @param applicant
     * @param resumeFile
     * @param testAnswerFile
     * @return
     */
    public Applicant saveApplicant(
            final Applicant applicant,
            final MultipartFile resumeFile,
            final MultipartFile testAnswerFile,
            final Long recruiterId
    ) throws SecurityException, ServiceException {
        try {
            Deal deal = dealRepository.findById(applicant.getDeal().getId());
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
            log.warn("Recruiter Service general exception: " + e);
            throw new ServiceException("Recruiter Service general exception: ", e);
        }
        log.warn("Recruiter Service security exception: deal belongs to different recruiter");
        throw new SecurityException("Recruiter Service security exception: " +
                " deal belongs to different recruiter");
    }

    /**
     * Method must return true if vacancy has been updated successful
     * @param recruiterId
     * @param vacancyId
     * @param message
     * @return
     */
    public Boolean applyRecruiterToVacancy(final Long recruiterId, final Long vacancyId, final String message) {

        return this.getBidRepository().create(recruiterId, vacancyId, message);
    }

    /**
     * Saving recruiter profile
     * @param recruiter    Recruiter POJO instance
     * @return true if update is ok, otherwise false
     */
    public User saveProfileForRecruiter(final Recruiter recruiter) {
        try {
            return userRepository.update(recruiter.getUser());
        } catch (RepositoryException e) {
            return null;
        }
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
