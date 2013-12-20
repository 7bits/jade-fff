package com.recruiters.web.controller.recruiter;

import com.recruiters.model.Vacancy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller Class for R11 "Show active vacancies"
 */
@Controller
public class ActiveVacancies {
    /** Id of 1st vacancy */
    static final Long VACANCY1_ID = 1L;
    /** Title of 1st vacancy */
    static final String VACANCY1_TITLE = "Программист";
    /** Description of 1st vacancy */
    static final String VACANCY1_SHORT_DESCRIPTION = "PHP guru";
    /** Date of 1st vacancy */
    static final String VACANCY1_DATE = "сегодня";
    /** Id of 2nd vacancy */
    static final Long VACANCY2_ID = 2L;
    /** Title of 2nd vacancy */
    static final String VACANCY2_TITLE = "Лесоруб";
    /** Description of 2nd vacancy */
    static final String VACANCY2_SHORT_DESCRIPTION = "Умеет клёво рубить сосны";
    /** Date of 2nd vacancy */
    static final String VACANCY2_DATE = "сегодня";
    /** Id of 3rd vacancy */
    static final Long VACANCY3_ID = 4L;
    /** Title of 3rd vacancy */
    static final String VACANCY3_TITLE = "Сантехник";
    /** Description of 3rd vacancy */
    static final String VACANCY3_SHORT_DESCRIPTION = "Не пьёт!";
    /** Date of 3rd vacancy */
    static final String VACANCY3_DATE = "сегодня";

    /**
     * Controller for R11 "Active vacancies list"
     * @return model and view with list of active vacancies
     */
    @RequestMapping(value = "recruiter-active-vacancies", method = RequestMethod.GET)
    public ModelAndView showActiveVacancies() {

        ModelAndView activeVacancies = new ModelAndView("recruiter-active-vacancies.jade");
        List<Vacancy> vacancyList = getActiveVacancies();
        activeVacancies.addObject("vacancyList", vacancyList);

        return activeVacancies;
    }

    /**
     * Fake getActiveVacancies method, should be real service method of RecruiterService in future
     * @return list of active vacancies
     */
    private List<Vacancy> getActiveVacancies() {

        List<Vacancy> activeVacancies = new ArrayList<Vacancy>();

        Vacancy vacancy1 = new Vacancy(VACANCY1_ID, VACANCY1_TITLE, VACANCY1_SHORT_DESCRIPTION, VACANCY1_DATE);
        Vacancy vacancy2 = new Vacancy(VACANCY2_ID, VACANCY2_TITLE, VACANCY2_SHORT_DESCRIPTION, VACANCY2_DATE);
        Vacancy vacancy3 = new Vacancy(VACANCY3_ID, VACANCY3_TITLE, VACANCY3_SHORT_DESCRIPTION, VACANCY3_DATE);
        activeVacancies.add(vacancy1);
        activeVacancies.add(vacancy2);
        activeVacancies.add(vacancy3);

        return activeVacancies;
    }


}