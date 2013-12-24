package com.recruiters.service;

import com.recruiters.model.Applicant;
import com.recruiters.model.Employer;
import com.recruiters.model.Recruiter;
import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.repository.ApplicantRepository;
import com.recruiters.repository.FileRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 */
@Service
public class RecruiterService {

    @Autowired
    private FileRepository fileRepository = null;
    @Autowired
    private ApplicantRepository applicantRepository = null;

    /** Id of 1st applicant */
    static final Long APPLICANT1_ID = 1L;
    /** Vacancy id applicant belongs to */
    static final Long APPLICANT1_VACANCY_ID = 1L;
    /** 1st Applicant first name */
    static final String APPLICANT1_FIRST_NAME = "Иван";
    /** 1st Applicant last name */
    static final String APPLICANT1_LAST_NAME = "Иванов";
    /** Id of 2nd applicant */
    static final Long APPLICANT2_ID = 2L;
    /** Vacancy id applicant belongs to */
    static final Long APPLICANT2_VACANCY_ID = 1L;
    /** 2nd Applicant first name */
    static final String APPLICANT2_FIRST_NAME = "Пётр";
    /** 2nd Applicant last name */
    static final String APPLICANT2_LAST_NAME = "Петров";
    /** Id of 3rd applicant */
    static final Long APPLICANT3_ID = 3L;
    /** Vacancy id applicant belongs to */
    static final Long APPLICANT3_VACANCY_ID = 1L;
    /** 3rd Applicant first name */
    static final String APPLICANT3_FIRST_NAME = "Константин";
    /** 3rd Applicant last name */
    static final String APPLICANT3_LAST_NAME = "Константинопольский";

    /** Id of 1st employer */
    static final Long EMPLOYER1_ID = 1L;
    /** First name of 1st employer */
    static final String EMPLOYER1_FIRST_NAME = "Василий";
    /** Last name of 1st employer */
    static final String EMPLOYER1_LAST_NAME = "Иванов";
    /** Id of 1st employer */
    static final Long RECRUITER1_ID = 1L;
    /** First name of 1st employer */
    static final String RECRUITER1_FIRST_NAME = "Иван";
    /** Last name of 1st employer */
    static final String RECRUITER1_LAST_NAME = "Васильев";

    /** If of employer for 2nd vacancy */
    static final Long VACANCY2_EMPLOYER_ID = 1L;
    /** Description of 2nd vacancy */
    static final String VACANCY2_DESCRIPTION = "Умеет клёво рубить сосны";
    /** Salary range for 2nd vacancy */
    static final String VACANCY2_SALARY = "33 - 45 $ в час";
    /** Creation date for 2nd vacancy */
    static final Date VACANCY2_CREATION_DATE = new Date();
    /** Expiration date for 2nd vacancy */
    static final Date VACANCY2_EXPIRATION_DATE = DateUtils.addDays(new Date(), 1);
    /** Test file url for 2nd vacancy */
    static final String VACANCY2_TEST_FILE = "#";
    /** If of employer for 3rd vacancy */
    static final Long VACANCY3_EMPLOYER_ID = 1L;
    /** Description of 3rd vacancy */
    static final String VACANCY3_DESCRIPTION = "Не пьёт!";
    /** Salary range for 3rd vacancy */
    static final String VACANCY3_SALARY = "80 - 100 $ в час";
    /** Creation date for 3rd vacancy */
    static final Date VACANCY3_CREATION_DATE = new Date();
    /** Expiration date for 3rd vacancy */
    static final Date VACANCY3_EXPIRATION_DATE = DateUtils.addDays(new Date(), 1);
    /** Test file url for 3rd vacancy */
    static final String VACANCY3_TEST_FILE = "#";


    /** If of employer for 1st vacancy */
    static final Long VACANCY1_EMPLOYER_ID = 1L;
    /** Description of 1st vacancy */
    static final String VACANCY1_DESCRIPTION = "Обожаю рубить сосны!";
    /** Salary range for 1st vacancy */
    static final String VACANCY1_SALARY = "30 - 50 $ в час";
    /** Creation date for 1st vacancy */
    static final Date VACANCY1_CREATION_DATE = new Date();
    /** Expiration date for 1st vacancy */
    static final Date VACANCY1_EXPIRATION_DATE = DateUtils.addDays(new Date(), 1);
    /** Test file url for 1st vacancy */
    static final String VACANCY1_TEST_FILE = "#";


    /** Id of 1st vacancy */
    static final Long VACANCY1_ID = 1L;
    /** Title of 1st vacancy */
    static final String VACANCY1_TITLE = "Программист";
    /** Description of 1st vacancy */
    static final String VACANCY1_SHORT_DESCRIPTION = "PHP guru";
    /** Date of 1st vacancy */
    static final Date VACANCY1_DATE = new Date();
    /** Id of 2nd vacancy */
    static final Long VACANCY2_ID = 2L;
    /** Title of 2nd vacancy */
    static final String VACANCY2_TITLE = "Лесоруб";
    /** Description of 2nd vacancy */
    static final String VACANCY2_SHORT_DESCRIPTION = "Умеет клёво рубить сосны";
    /** Date of 2nd vacancy */
    static final Date VACANCY2_DATE = new Date();
    /** Id of 3rd vacancy */
    static final Long VACANCY3_ID = 4L;
    /** Title of 3rd vacancy */
    static final String VACANCY3_TITLE = "Сантехник";
    /** Description of 3rd vacancy */
    static final String VACANCY3_SHORT_DESCRIPTION = "Не пьёт!";
    /** Date of 3rd vacancy */
    static final Date VACANCY3_DATE = new Date();


    public List<Vacancy> findAvailableListOfVacancies() {

        List <Vacancy> vacancies = new ArrayList<Vacancy>();

        Vacancy vacancy1 = new Vacancy(VACANCY1_ID, VACANCY1_TITLE, VACANCY1_SHORT_DESCRIPTION, VACANCY1_DATE);
        Vacancy vacancy2 = new Vacancy(VACANCY2_ID, VACANCY2_TITLE, VACANCY2_SHORT_DESCRIPTION, VACANCY2_DATE);
        Vacancy vacancy3 = new Vacancy(VACANCY3_ID, VACANCY3_TITLE, VACANCY3_SHORT_DESCRIPTION, VACANCY3_DATE);
        vacancies.add(vacancy1);
        vacancies.add(vacancy2);
        vacancies.add(vacancy3);

        return vacancies;
    }

    public List<Vacancy> findListOfInProgressVacanciesForRecruiter(final Long recruiterId) {

        List <Vacancy> vacancies = new ArrayList<Vacancy>();

        Vacancy vacancy1 = new Vacancy(VACANCY1_ID, VACANCY1_TITLE, VACANCY1_SHORT_DESCRIPTION, VACANCY1_DATE);
        Vacancy vacancy2 = new Vacancy(VACANCY2_ID, VACANCY2_TITLE, VACANCY2_SHORT_DESCRIPTION, VACANCY2_DATE);
        Vacancy vacancy3 = new Vacancy(VACANCY3_ID, VACANCY3_TITLE, VACANCY3_SHORT_DESCRIPTION, VACANCY3_DATE);
        vacancies.add(vacancy1);
        vacancies.add(vacancy2);
        vacancies.add(vacancy3);

        return vacancies;
    }

    /**
     * @param recruiterId  Id of recruiter who wants to get this data
     * @param vacancyId    Id of vacancy for which we want to get full description
     * @return vacancy description
     */
    public Vacancy getVacancyByRecruiterIdAndVacancyId(final Long recruiterId, final Long vacancyId) {

        Vacancy vacancy = new Vacancy(VACANCY1_ID, VACANCY1_EMPLOYER_ID, VACANCY1_TITLE,
                VACANCY1_DESCRIPTION, VACANCY1_SALARY, VACANCY1_CREATION_DATE,
                VACANCY1_EXPIRATION_DATE, VACANCY1_TEST_FILE);
        if (vacancyId.equals(VACANCY1_ID)) {
            return vacancy;
        } else {
            return null;
        }
    }

    /**
     * @param recruiterId  Id of recruiter who wants to get this data
     * @param vacancyId    Id of vacancy for which we want to get full description
     * @return vacancy description
     */
    public Vacancy getVacancyInProgressByRecruiterIdAndVacancy(final Long recruiterId, final Long vacancyId) {
        Vacancy vacancy1 = new Vacancy(VACANCY1_ID, VACANCY1_EMPLOYER_ID, VACANCY1_TITLE,
                VACANCY1_DESCRIPTION, VACANCY1_SALARY, VACANCY1_CREATION_DATE, VACANCY1_EXPIRATION_DATE,
                VACANCY1_TEST_FILE);
        Vacancy vacancy2 = new Vacancy(VACANCY2_ID, VACANCY2_EMPLOYER_ID, VACANCY2_TITLE,
                VACANCY2_DESCRIPTION, VACANCY2_SALARY, VACANCY2_CREATION_DATE, VACANCY2_EXPIRATION_DATE,
                VACANCY2_TEST_FILE);
        Vacancy vacancy3 = new Vacancy(VACANCY3_ID, VACANCY3_EMPLOYER_ID, VACANCY3_TITLE,
                VACANCY3_DESCRIPTION, VACANCY3_SALARY, VACANCY3_CREATION_DATE, VACANCY3_EXPIRATION_DATE,
                VACANCY3_TEST_FILE);

        if (vacancyId.equals(VACANCY1_ID)) {
            return vacancy1;
        } else if (vacancyId.equals(VACANCY2_ID)) {
            return vacancy2;
        } else if (vacancyId.equals(VACANCY3_ID)) {
            return vacancy3;
        } else {
            return null;
        }

    }

    /**
     * List of applicants for this job
     * @param recruiterId    Id of recruiter who asks for data
     * @param vacancyId      Id of vacancy, applicants apply for
     * @return List of applicants
     */
    public List<Applicant> getApplicantListForVacancy(final Long recruiterId, final Long vacancyId) {
        List<Applicant> applicantList = new ArrayList<Applicant>();
        Applicant applicant1 = new Applicant(APPLICANT1_ID, APPLICANT1_VACANCY_ID,
                APPLICANT1_FIRST_NAME, APPLICANT1_LAST_NAME);
        Applicant applicant2 = new Applicant(APPLICANT2_ID, APPLICANT2_VACANCY_ID,
                APPLICANT2_FIRST_NAME, APPLICANT2_LAST_NAME);
        Applicant applicant3 = new Applicant(APPLICANT3_ID, APPLICANT3_VACANCY_ID,
                APPLICANT3_FIRST_NAME, APPLICANT3_LAST_NAME);
        applicantList.add(applicant1);
        applicantList.add(applicant2);
        applicantList.add(applicant3);
        if (vacancyId.equals(APPLICANT1_VACANCY_ID)) {
            return applicantList;
        } else {
            return null;
        }
    }


    public Boolean addApplicantToVacancy(final Applicant applicant, final MultipartFile resumeFile, final MultipartFile testAnswerFile) {
        String fileNameForResume =  this.getFileRepository().saveFile(resumeFile);
        String fileNameForTestAnswers = this.getFileRepository().saveFile(testAnswerFile);
        applicant.setResumeFile(fileNameForResume);
        applicant.setTestAnswerFile(fileNameForTestAnswers);

        return this.getApplicantRepository().saveApplicant(applicant);
    }

    public Employer getEmployerByVacancyId(final Long employerId) {
        Employer employer = new Employer(EMPLOYER1_ID, EMPLOYER1_FIRST_NAME, EMPLOYER1_LAST_NAME);
        if (employerId.equals(EMPLOYER1_ID)) {
            return employer;
        } else {
            return null;
        }
    }

    public Recruiter findRecruiterByUserId(final Long userId) {
        Recruiter recruiter = new Recruiter(RECRUITER1_ID, RECRUITER1_FIRST_NAME, RECRUITER1_LAST_NAME);
        if (userId.equals(RECRUITER1_ID)) {
            return recruiter;
        } else {
            return null;
        }
    }

    public User getCurrentUser(final HttpServletRequest request) {
        return new User(1L, "recruiter", "123123");
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
}
