package com.recruiters.repository;

import com.recruiters.model.Recruiter;
import com.recruiters.model.Vacancy;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Repository for working with vacancy
 */
@Repository
public class VacancyRepository {

    /** Id of 1st vacancy */
    static final Long VACANCY1_ID = 1L;
    /** Title of 1st vacancy */
    static final String VACANCY1_TITLE = "Программист";
    /** Description of 1st vacancy */
    static final String VACANCY1_SHORT_DESCRIPTION = "PHP guru";
    /** Date of 1st vacancy */
    static final Date VACANCY1_DATE = new Date();
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




    /** Id of 2nd vacancy */
    static final Long VACANCY2_ID = 2L;
    /** Title of 2nd vacancy */
    static final String VACANCY2_TITLE = "Лесоруб";
    /** Description of 2nd vacancy */
    static final String VACANCY2_SHORT_DESCRIPTION = "Умеет клёво рубить сосны";
    /** Date of 2nd vacancy */
    static final Date VACANCY2_DATE = new Date();
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




    /** Id of 3rd vacancy */
    static final Long VACANCY3_ID = 3L;
    /** Title of 3rd vacancy */
    static final String VACANCY3_TITLE = "Сантехник";
    /** Description of 3rd vacancy */
    static final String VACANCY3_SHORT_DESCRIPTION = "Не пьёт!";
    /** Date of 3rd vacancy */
    static final Date VACANCY3_DATE = new Date();
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



    /**
     * Method return vacancy by id
     * @param vacancyId
     * @return
     */
    public Vacancy getById(final Long vacancyId) {

        EmployerRepository employerRepository = new EmployerRepository();
        Vacancy vacancy1 = new Vacancy(VACANCY1_ID, employerRepository.getById(1L), VACANCY1_TITLE, VACANCY1_SHORT_DESCRIPTION, VACANCY1_DATE);
        Vacancy vacancy2 = new Vacancy(VACANCY2_ID, employerRepository.getById(1L), VACANCY2_TITLE, VACANCY2_SHORT_DESCRIPTION, VACANCY2_DATE);
        Vacancy vacancy3 = new Vacancy(VACANCY3_ID, employerRepository.getById(1L), VACANCY3_TITLE, VACANCY3_SHORT_DESCRIPTION, VACANCY3_DATE);
        if (vacancyId.equals(1L)) {
            return vacancy1;
        }
        if (vacancyId.equals(2L)) {
            return vacancy2;
        }
        if (vacancyId.equals(3L)) {
            return vacancy3;
        }

        return null;
    }

    /**
     * Method must return all vacancies for this recruiter
     * @return
     */
    public List<Vacancy> findListOfAvailableVacancies() {
        List <Vacancy> vacancies = new ArrayList<Vacancy>();

        EmployerRepository employerRepository = new EmployerRepository();
        Vacancy vacancy1 = new Vacancy(VACANCY1_ID, employerRepository.getById(1L), VACANCY1_TITLE, VACANCY1_SHORT_DESCRIPTION, VACANCY1_DATE);
        Vacancy vacancy2 = new Vacancy(VACANCY2_ID, employerRepository.getById(1L), VACANCY2_TITLE, VACANCY2_SHORT_DESCRIPTION, VACANCY2_DATE);
        Vacancy vacancy3 = new Vacancy(VACANCY3_ID, employerRepository.getById(1L), VACANCY3_TITLE, VACANCY3_SHORT_DESCRIPTION, VACANCY3_DATE);
        vacancies.add(vacancy1);
        vacancies.add(vacancy2);
        vacancies.add(vacancy3);

        return vacancies;
    }

    public Boolean updateVacancy(final Recruiter recruiter, final String message) {

        return true;
    }
}
