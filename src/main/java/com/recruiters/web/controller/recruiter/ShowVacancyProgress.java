package com.recruiters.web.controller.recruiter;

import com.recruiters.model.Applicant;
import com.recruiters.model.Employer;
import com.recruiters.model.Vacancy;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controller Class for R6 "Show vacancy progress"
 */
@Controller
public class ShowVacancyProgress {
    /** Id of current recruiter visited this page */
    static final Long RECRUITER_ID = 1L;
    /** Id of 1st vacancy */
    static final Long VACANCY1_ID = 1L;
    /** If of employer for 1st vacancy */
    static final Long VACANCY1_EMPLOYER_ID = 1L;
    /** Title of 1st vacancy */
    static final String  VACANCY1_TITLE = "Программист";
    /** Description of 1st vacancy */
    static final String VACANCY1_DESCRIPTION = "PHP Guru";
    /** Salary range for 1st vacancy */
    static final String VACANCY1_SALARY = "30 - 50 $ в час";
    /** Creation date for 1st vacancy */
    static final Date VACANCY1_CREATION_DATE = new Date();
    /** Expiration date for 1st vacancy */
    static final Date VACANCY1_EXPIRATION_DATE = DateUtils.addDays(new Date(), 1);
    /** Test file url for 1st vacancy */
    static final String VACANCY1_TEST_FILE = "#";
    /** Id of 1st employer */
    static final Long EMPLOYER1_ID = 1L;
    /** First name of 1st employer */
    static final String EMPLOYER1_FIRST_NAME = "Василий";
    /** Last name of 1st employer */
    static final String EMPLOYER1_LAST_NAME = "Иванов";
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
    /** Id of 2nd vacancy */
    static final Long VACANCY2_ID = 2L;
    /** If of employer for 2nd vacancy */
    static final Long VACANCY2_EMPLOYER_ID = 1L;
    /** Title of 2nd vacancy */
    static final String  VACANCY2_TITLE = "Лесоруб";
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
    /** Id of 3rd vacancy */
    static final Long VACANCY3_ID = 4L;
    /** If of employer for 3rd vacancy */
    static final Long VACANCY3_EMPLOYER_ID = 1L;
    /** Title of 3rd vacancy */
    static final String  VACANCY3_TITLE = "Сантехник";
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


    @RequestMapping(value = "recruiter-progress-vacancy-show/{vacancyId}", method = RequestMethod.GET)
    public ModelAndView showVacancyProgress(@PathVariable final Long vacancyId) {

        Long recruiterId = getMyId();
        ModelAndView vacancyProgress = new ModelAndView("recruit-vacancy-show.jade");
        Vacancy vacancy = getVacancyInfo(recruiterId, vacancyId);
        vacancyProgress.addObject("vacancy", vacancy);
        Employer employer = getEmployerInfo(recruiterId, vacancy.getEmployerId());
        vacancyProgress.addObject("employer", employer);
        List<Applicant> applicantList = getApplicantListForVacancy(recruiterId, vacancyId);
        vacancyProgress.addObject("applicantList", applicantList);
        return vacancyProgress;
    }

    /**
     * Get Information about vacancy by its id
     * @param recruiterId    Id of recruiter who wants to get it
     * @param vacancyId      Id of vacancy
     * @return Vacancy info
     */
    private Vacancy getVacancyInfo(final Long recruiterId, final Long vacancyId) {

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
     * Get employer info service method
     * @param recruiterId    Id of recruiter who asks for data
     * @param employerId     Id of employer we want to get info about
     * @return employer information
     */
    private Employer getEmployerInfo(final Long recruiterId, final Long employerId) {

        Employer employer = new Employer(EMPLOYER1_ID, EMPLOYER1_FIRST_NAME, EMPLOYER1_LAST_NAME);
        if (employerId.equals(EMPLOYER1_ID)) {
            return employer;
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
    private List<Applicant> getApplicantListForVacancy(final Long recruiterId, final Long vacancyId) {

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

    /**
     * Fake method to get id of current recruiter
     * Should be implemented according to security behaviour
     * @return id of current recruiter
     */
    private Long getMyId() {
        return RECRUITER_ID;
    }
}
