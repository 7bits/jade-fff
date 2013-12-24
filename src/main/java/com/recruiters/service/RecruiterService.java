package com.recruiters.service;

import com.recruiters.model.Applicant;
import com.recruiters.model.Deal;
import com.recruiters.model.Employer;
import com.recruiters.model.Recruiter;
import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.repository.ApplicantRepository;
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
import java.util.ArrayList;
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

    /**
     * Method must return all vacancies for this recruiter
     * @return
     */
    public List<Vacancy> findListOfAvailableVacancies() {

        return this.getVacancyRepository().findListOfAvailableVacancies();
    }

    /**
     * Method must return list of vacancies. Each of vacancies has deal for this recruiter
     * @param recruiterId
     * @return
     */
    public List<Vacancy> findListOfInProgressVacanciesForRecruiter(final Long recruiterId) {

        List <Deal> deals = this.getDealRepository().findAllByRecruiterId(recruiterId);
        List <Vacancy> vacancies = new ArrayList<Vacancy>();
        for (Deal deal : deals) {
            vacancies.add(deal.getVacancy());
        }

        return vacancies;
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
     * @param recruiterId  Id of recruiter who wants to get this data
     * @param vacancyId    Id of vacancy for which we want to get full description
     * @return vacancy description
     */
    public Vacancy getVacancyInProgressByRecruiterIdAndVacancyId(final Long recruiterId, final Long vacancyId) {

        return this.getVacancyRepository().getVacancyByRecruiterIdAndVacancyId(recruiterId, vacancyId);
    }

    /**
     * List of applicants for this vacancy (if vacancy has dael for this recruiter)
     * @param recruiterId    Id of recruiter who asks for data
     * @param vacancyId      Id of vacancy, applicants apply for
     * @return List of applicants
     */
    public List<Applicant> getApplicantListForVacancy(final Long recruiterId, final Long vacancyId) {
        Deal deal = this.getDealRepository().findByRecruiterIdAndVacancyId(recruiterId, vacancyId);
        List<Applicant> applicantList = null;
        if (deal != null) {
            applicantList = this.getApplicantRepository().getAppByDealId(deal.getId());
        }
        return applicantList;
    }

    /**
     * Method must add new applicant to current vacancy by recruiter
     * @param applicant
     * @param resumeFile
     * @param testAnswerFile
     * @return
     */
    public Boolean addApplicantToVacancy(final Applicant applicant, final MultipartFile resumeFile, final MultipartFile testAnswerFile) {
        String fileNameForResume =  this.getFileRepository().saveFile(resumeFile);
        String fileNameForTestAnswers = this.getFileRepository().saveFile(testAnswerFile);
        applicant.setResumeFile(fileNameForResume);
        applicant.setTestAnswerFile(fileNameForTestAnswers);

        return this.getApplicantRepository().saveApplicant(applicant);
    }

    /**
     * Method must return employer by given vacancy id
     * @param vacancyId
     * @return
     */
    public Employer getEmployerByVacancyId(final Long vacancyId) {

        return this.getEmployerRepository().getEmployerByVacancyId(vacancyId);
    }

    /**
     * Method must return recruiter by given user id
     * @param userId
     * @return
     */
    public Recruiter findRecruiterByUserId(final Long userId) {

        return this.getRecruiterRepository().findRecruiterByUserId(userId);
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
}
