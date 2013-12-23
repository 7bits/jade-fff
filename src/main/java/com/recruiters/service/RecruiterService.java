package com.recruiters.service;

import com.recruiters.model.Applicant;
import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.repository.ApplicantRepository;
import com.recruiters.repository.FileRepository;
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

    public List<Vacancy> findRecruiterListOfVacancies(final Long recruiterId) {

        List <Vacancy> vacancies = new ArrayList<Vacancy>();

        Vacancy vacancy1 = new Vacancy(VACANCY1_ID, VACANCY1_TITLE, VACANCY1_SHORT_DESCRIPTION, VACANCY1_DATE);
        Vacancy vacancy2 = new Vacancy(VACANCY2_ID, VACANCY2_TITLE, VACANCY2_SHORT_DESCRIPTION, VACANCY2_DATE);
        Vacancy vacancy3 = new Vacancy(VACANCY3_ID, VACANCY3_TITLE, VACANCY3_SHORT_DESCRIPTION, VACANCY3_DATE);
        vacancies.add(vacancy1);
        vacancies.add(vacancy2);
        vacancies.add(vacancy3);

        return vacancies;
    }

    public Boolean addApplicantToVacancy(final Applicant applicant, final MultipartFile resumeFile, final MultipartFile testAnswerFile) {
        String fileNameForResume =  this.getFileRepository().saveFile(resumeFile);
        String fileNameForTestAnswers = this.getFileRepository().saveFile(testAnswerFile);
        applicant.setResumeFile(fileNameForResume);
        applicant.setTestAnswerFile(fileNameForTestAnswers);


        return this.getApplicantRepository().saveApplicant(applicant);
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
