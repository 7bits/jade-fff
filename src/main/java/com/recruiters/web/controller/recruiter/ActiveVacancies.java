package com.recruiters.web.controller.recruiter;

import com.recruiters.model.Vacancy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller Class for R11 "Show active vacancies"
 */
@Controller
public class ActiveVacancies {

    /**
     * Controller for R11 "Active vacancies list"
     * @return model and view with list of active vacancies
     */
    @RequestMapping(value = "/recruiter-my-vacancies", method = RequestMethod.GET)
    public ModelAndView showActiveVacancies() {

        ModelAndView activeVacancies = new ModelAndView ("recruiter-my-vacancies.jade");
        List<Vacancy> vacancyList = getActiveVacancies();
        activeVacancies.addObject ("vacancyList", vacancyList);

        return activeVacancies;
    }

    /**
     * Fake getActiveVacancies method, should be real service method of RecruiterService in future
     * @return list of active vacancies
     */
    private List<Vacancy> getActiveVacancies () {
        final Long VACANCY1_ID = 1L;
        final String VACANCY1_TITLE = "Программист";
        final String VACANCY1_SHORT_DESCRIPTION = "PHP guru";
        final String VACANCY1_DATE = "сегодня";
        final Long VACANCY2_ID = 2L;
        final String VACANCY2_TITLE = "Лесоруб";
        final String VACANCY2_SHORT_DESCRIPTION = "Умеет клёво рубить сосны";
        final String VACANCY2_DATE = "сегодня";
        final Long VACANCY3_ID = 4L;
        final String VACANCY3_TITLE = "Сантехник";
        final String VACANCY3_SHORT_DESCRIPTION = "Не пьёт!";
        final String VACANCY3_DATE = "сегодня";
        List<Vacancy> activeVacancies = new ArrayList<Vacancy>();
        Vacancy vacancy1 = new Vacancy (VACANCY1_ID, VACANCY1_TITLE, VACANCY1_SHORT_DESCRIPTION, VACANCY1_DATE);
        Vacancy vacancy2 = new Vacancy (VACANCY2_ID, VACANCY2_TITLE, VACANCY2_SHORT_DESCRIPTION, VACANCY2_DATE);
        Vacancy vacancy3 = new Vacancy (VACANCY3_ID, VACANCY3_TITLE, VACANCY3_SHORT_DESCRIPTION, VACANCY3_DATE);
        activeVacancies.add (vacancy1);
        activeVacancies.add (vacancy2);
        activeVacancies.add (vacancy3);

        return activeVacancies;
    }


}