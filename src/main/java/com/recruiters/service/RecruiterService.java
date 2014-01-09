package com.recruiters.service;

import com.recruiters.model.Applicant;
import com.recruiters.model.Bid;
import com.recruiters.model.Deal;
import com.recruiters.model.Recruiter;
import com.recruiters.model.Vacancy;
import com.recruiters.repository.ApplicantRepository;
import com.recruiters.repository.BidRepository;
import com.recruiters.repository.DealRepository;
import com.recruiters.repository.EmployerRepository;
import com.recruiters.repository.FileRepository;
import com.recruiters.repository.RecruiterRepository;
import com.recruiters.repository.UserRepository;
import com.recruiters.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 */
@Service
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

    /**
     * Method must return recruiter by given id
     * @param id
     * @return
     */
    public Recruiter findRecruiterById(final Long id) {

        return this.getRecruiterRepository().findById(id);
    }

    /**
     * Method must return recruiter by given user id
     * @param userId
     * @return
     */
    public Recruiter findRecruiterByUserId(final Long userId) {

        return this.getRecruiterRepository().findByUser(userId);
    }

    /**
     * Method must return recruiter id by given user id
     * @param userId
     * @return
     */
    public Long findRecruiterIdByUserId(final Long userId) {

        return this.getRecruiterRepository().findIdByUser(userId);
    }

    /**
     * Method must return all vacancies for this recruiter
     * @return
     */
    public List<Vacancy> findAvailableVacanciesForRecruiter() {

        return this.getVacancyRepository().findAvailableVacanciesForRecruiter();
    }

    /**
     * @param vacancyId    Id of vacancy for which we want to get full description
     * @return vacancy
     */
    public Vacancy getVacancyById(final Long vacancyId) {

        return this.getVacancyRepository().findById(vacancyId);
    }

    /**
     * Method must return list of recruiter bids.
     * @return
     */
    public List<Bid> findRecruiterBids(final Long recruiterId) {

        return this.getBidRepository().findRecruiterBids(recruiterId);
    }

    /**
     * Method must return list of vacancies. Each of vacancies has deal for this recruiter
     * @param userId
     * @return
     */
    public List<Deal> findActiveDealsForRecruiter(final Long userId) {

        return this.getDealRepository().findActiveDealsForRecruiter(userId);
    }

    /**
     * Method must return vacancy if it has deal for this recruiter
     * @param dealId  Id of deal
     * @return vacancy description
     */
    public Deal findDealForRecruiter(final Long dealId, final Long userId) {

        return this.getDealRepository().findDealForRecruiter(dealId, userId);
    }

    public Applicant getApplicantById(final Long applicantId) {

        return this.getApplicantRepository().findById(applicantId);
    }

    /**
     * Method must add new applicant to current vacancy by recruiter
     * @param applicant
     * @param resumeFile
     * @param testAnswerFile
     * @return
     */
    public Boolean saveApplicantToVacancy(
            final Applicant applicant,
            final MultipartFile resumeFile,
            final MultipartFile testAnswerFile
    ) {
        /*TODO: Make FileService instead of FileRepository and use it in Web-layer. Use file names at this method */
        String fileNameForResume =  this.getFileRepository().saveFile(resumeFile);
        String fileNameForTestAnswers = this.getFileRepository().saveFile(testAnswerFile);
        applicant.setResumeFile(fileNameForResume);
        applicant.setTestAnswerFile(fileNameForTestAnswers);

        if (applicant.getId().equals(0L)) {
            return this.getApplicantRepository().createApplicant(applicant);
        } else {
            return this.getApplicantRepository().updateApplicant(applicant);
        }
    }

    /**
     * Method must return true if vacancy has been updated successful
     * @param recruiterId
     * @param vacancyId
     * @param message
     * @return
     */
    public Boolean applyRecruiterToVacancy(final Long recruiterId, final Long vacancyId, final String message) {

        return this.getBidRepository().createBid(recruiterId, vacancyId, message);
    }

    /**
     * Saving recruiter profile
     * @param recruiter    Recruiter POJO instance
     * @return true if update is ok, otherwise false
     */
    public Boolean saveRecruiterProfile(final Recruiter recruiter) {

        return userRepository.update(recruiter.getUser());
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
