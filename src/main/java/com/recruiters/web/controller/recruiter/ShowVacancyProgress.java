package com.recruiters.web.controller.recruiter;

import com.recruiters.model.Applicant;
import com.recruiters.model.Employer;
import com.recruiters.model.Vacancy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller Class for R6 "Show vacancy progress"
 */
@Controller
public class ShowVacancyProgress {
    /** Id of 1st vacancy */
    static final Long VACANCY1_ID = 1L;
    /** If of employer for 1st vacancy */
    static final Long VACANCY1_EMPLOYER_ID = 1L;
    /** Title of 1st vacancy */
    static final String  VACANCY1_TITLE = "Лесоруб";
    /** Description of 1st vacancy */
    static final String VACANCY1_DESCRIPTION = "Обожаю рубить сосны!";
    /** Salary range for 1st vacancy */
    static final String VACANCY1_SALARY = "30 - 50 $ в час";
    /** Creation date for 1st vacancy */
    static final String VACANCY1_CREATION_DATE = "сегодня";
    /** Expiration date for 1st vacancy */
    static final String VACANCY1_EXPIRATION_DATE = "завтра";
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


    @RequestMapping(value = "recruiter-progress-vacancy-show/{vacancyId}", method = RequestMethod.GET)
    public ModelAndView showVacancyProgress(@PathVariable final Long vacancyId) {

        ModelAndView vacancyProgress = new ModelAndView("recruit-vacancy-show.jade");
        Vacancy vacancy = getVacancyInfo(vacancyId);
        vacancyProgress.addObject("vacancy", vacancy);
        Employer employer = getEmployerInfo(vacancy.getEmployerId());
        vacancyProgress.addObject("employer", employer);
        List<Applicant> applicantList = getApplicantListForVacancy(vacancyId);
        vacancyProgress.addObject("applicantList", applicantList);
        return vacancyProgress;
    }

    private Vacancy getVacancyInfo(final Long vacancyId) {

        Vacancy vacancy = new Vacancy(VACANCY1_ID, VACANCY1_EMPLOYER_ID, VACANCY1_TITLE,
                VACANCY1_DESCRIPTION, VACANCY1_SALARY, VACANCY1_CREATION_DATE, VACANCY1_EXPIRATION_DATE,
                VACANCY1_TEST_FILE);
        if (vacancyId.equals(VACANCY1_ID)) {
            return vacancy;
        } else {
            return null;
        }
    }

    private Employer getEmployerInfo(final Long employerId) {

        Employer employer = new Employer(EMPLOYER1_ID, EMPLOYER1_FIRST_NAME, EMPLOYER1_LAST_NAME);
        if (employerId.equals(EMPLOYER1_ID)) {
            return employer;
        } else {
            return null;
        }

    }

    private List<Applicant> getApplicantListForVacancy(final Long vacancyId) {

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
}
