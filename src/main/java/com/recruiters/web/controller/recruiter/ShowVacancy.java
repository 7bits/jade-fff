package com.recruiters.web.controller.recruiter;


import com.recruiters.model.Vacancy;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;


/**
 * Controller Class for R2 "Show Vacancy"
 */
@Controller
public class ShowVacancy {
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
    static final Date VACANCY1_CREATION_DATE = new Date();
    /** Expiration date for 1st vacancy */
    static final Date VACANCY1_EXPIRATION_DATE = DateUtils.addDays(new Date(), 1);
    /** Test file url for 1st vacancy */
    static final String VACANCY1_TEST_FILE = "#";

    /**
     * Controller for R2 "Show vacancy"
     * @return model and view with one vacancy
     */
    @RequestMapping(value = "recruiter-vacancy-show/{vacancyId}", method = RequestMethod.GET)
    public ModelAndView showVacancyById(@PathVariable final Long vacancyId) {

        ModelAndView showVacancy = new ModelAndView("recruiter-vacancy-show.jade");
        Vacancy vacancy = getVacancyById(vacancyId);
        showVacancy.addObject("vacancy", vacancy);
        return showVacancy;
    }

    /**
     * Fake "get vacancy description" method, should be implemented in RecruiterService
     * @param vacancyId    Id of vacancy for which we want to get full description
     * @return vacancy description
     */
    private Vacancy getVacancyById(final Long vacancyId) {

        Vacancy vacancy = new Vacancy(VACANCY1_ID, VACANCY1_EMPLOYER_ID, VACANCY1_TITLE,
                VACANCY1_DESCRIPTION, VACANCY1_SALARY, VACANCY1_CREATION_DATE,
                VACANCY1_EXPIRATION_DATE, VACANCY1_TEST_FILE);
        if (vacancyId.equals(VACANCY1_ID)) {
            return vacancy;
        } else {
            return null;
        }
    }
}
