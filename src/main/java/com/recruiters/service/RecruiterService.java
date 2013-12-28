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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
     * Method must return all vacancies for this recruiter
     * @return
     */
    public List<Vacancy> findListOfAllAvailableVacancies() {

        return this.getVacancyRepository().findListOfAvailableVacancies();
    }

    /**
     * Method must return list of vacancies. Each of vacancies has deal for this recruiter
     * @param recruiterId
     * @return
     */
    public List<Deal> findListOfDealsForRecruiterByRecruiterId(final Long recruiterId) {

        return this.getDealRepository().findAllActiveByRecruiterId(recruiterId);
    }

    /**
     * Method must return list of recruiter bids.
     * @return
     */
    public List<Bid> findListOfActiveBidsForRecruiterByRecruiterId(final Long recruiterId) {

        return this.getBidRepository().findListOfRecruiterBids(recruiterId);
    }

    /**
     * @param vacancyId    Id of vacancy for which we want to get full description
     * @return vacancy
     */
    public Vacancy getVacancyById(final Long vacancyId) {

        return this.getVacancyRepository().getById(vacancyId);
    }

    /**
     * Method must return vacancy if it has deal for this recruiter
     * @param dealId  Id of deal
     * @return vacancy description
     */
    public Deal getDealById(final Long dealId, final Recruiter recruiter) {

        Deal deal = this.getDealRepository().getById(dealId);
        if (deal.getRecruiter().getId().equals(recruiter.getId())) {
            return deal;
        }

        return null;
    }

    public Applicant getApplicantById(final Long applicantId) {

        return this.getApplicantRepository().getApplicantById(applicantId);
    }

    /**
     * Method must add new applicant to current vacancy by recruiter
     * @param applicant
     * @param resumeFile
     * @param testAnswerFile
     * @return
     */
    public Boolean saveApplicantToVacancy(final Applicant applicant, final MultipartFile resumeFile, final MultipartFile testAnswerFile) {
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
     * @param recruiter
     * @param message
     * @return
     */
    public Boolean applyRecruiterToVacancy(final Recruiter recruiter, final Vacancy vacancy, final String message) {

        return this.getBidRepository().createBid(recruiter, vacancy, message);
    }

    /**
     * Method must return recruiter by given user id
     * @param userId
     * @return
     */
    public Recruiter findRecruiterByUserId(final Long userId) {

        return this.getRecruiterRepository().getByUserId(userId);
    }

    public User getCurrentUser(final HttpServletRequest request) {

        return this.getUserRepository().getCurrentUser(request);
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
