package com.recruiters.web.controller.recruiter;

import com.recruiters.model.Vacancy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controller Class for R12 "My vacancies"
 */
@Controller
public class MyVacancies {
    /** Id of recruiter while we cannot get actual id */
    static final Long RECRUITER_ID = 1L;
    /** Id of recruiter, vacancies below belongs to */
    static final Long RECRUITER1_ID = 1L;
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

    /**
     * Controller for R2 "My vacancies"
     * @return model and view with list of active vacancies
     */
    @RequestMapping(value = "recruiter-vacancies-list", method = RequestMethod.GET)
    public ModelAndView showMyVacancies() {

        Long myId = getMyId();
        ModelAndView myVacancies = new ModelAndView("recruiter-my-vacancies.jade");
        List<Vacancy> vacancyList = getMyVacancies(myId);
        myVacancies.addObject("vacancyList", vacancyList);
        return myVacancies;
    }

    /**
     * Fake getMyVacancies method, should be real service method of RecruiterService in future
     * @return list of my vacancies
     */
    private List<Vacancy> getMyVacancies(final Long recruiterId) {

        List<Vacancy> myVacancies = new ArrayList<Vacancy>();

        Vacancy vacancy1 = new Vacancy(VACANCY1_ID, VACANCY1_TITLE, VACANCY1_SHORT_DESCRIPTION, VACANCY1_DATE);
        Vacancy vacancy2 = new Vacancy(VACANCY2_ID, VACANCY2_TITLE, VACANCY2_SHORT_DESCRIPTION, VACANCY2_DATE);
        Vacancy vacancy3 = new Vacancy(VACANCY3_ID, VACANCY3_TITLE, VACANCY3_SHORT_DESCRIPTION, VACANCY3_DATE);
        myVacancies.add(vacancy1);
        myVacancies.add(vacancy2);
        myVacancies.add(vacancy3);

        if (recruiterId.equals(RECRUITER1_ID)) {
            return myVacancies;
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
