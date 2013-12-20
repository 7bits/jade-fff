package com.recruiters.web.controller.recruiter;

import com.recruiters.model.VacancyFull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Controller Class for R2 "Show Vacancy"
 */
@Controller
public class ShowVacancy {

    /**
     * Controller for R2 "Show vacancy"
     * @return model and view with one vacancy
     */
    @RequestMapping(value = "/recruiter/showVacancy/{$vacancyId}", method = RequestMethod.GET)
    public ModelAndView showVacancyById (@PathVariable Long vacancyId) {

        ModelAndView showVacancy = new ModelAndView ("showVacancy");
        VacancyFull vacancy = getVacancyFullById (vacancyId);
        showVacancy.addObject("vacancy", vacancy);
        return showVacancy;
    }

    /**
     * Fake "get full vacancy description" method, should be implemented in RecruiterService
     * @param vacancyId    Id of vacancy for which we want to get full description
     * @return full vacancy description
     */
    private VacancyFull getVacancyFullById (Long vacancyId) {
        final Long VACANCY1_ID = 1L;
        final String  VACANCY1_TITLE = "Лесоруб";
        final String VACANCY1_DESCRIPTION = "Обожаю рубить сосны!";
        final String VACANCY1_SALARY = "30 - 50 $ в час";
        final String VACANCY1_CREATION_DATE = "сегодня";
        final String VACANCY1_EXPIRATION_DATE = "завтра";
        final Long VACANCY1_TEST_ID = 1L;
        VacancyFull vacancyFull1 = new VacancyFull (VACANCY1_ID, VACANCY1_TITLE, VACANCY1_DESCRIPTION,
                VACANCY1_SALARY, VACANCY1_CREATION_DATE, VACANCY1_EXPIRATION_DATE, VACANCY1_TEST_ID);
        if (vacancyId.equals(VACANCY1_ID)) {
            return vacancyFull1;
        } else {
            return null;
        }
    }

}
