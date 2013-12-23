package com.recruiters.web.controller.employer;

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
@Controller("employerMyVacancy")
public class MyVacancies {
    /** Id of recruiter who is currently visiting page */
    static final Long RECRUITER_ID = 1L;
    /** Id of recruiter in fake DB */
    static final Long RECRUITER1_ID = 1L;
    /** Id of 1st vacancy in fake DB */
    static final Long VACANCY1_ID = 1L;
    /** Title of 1st vacancy  */
    static final String VACANCY1_TITLE = "Программист";
    /** Description of 1st vacancy */
    static final String VACANCY1_SHORT_DESCRIPTION = "PHP guru";
    /** 1st vacancy date of creation */
    static final Date VACANCY1_DATE = new Date();
    /** Id of 2nd vacancy */
    static final Long VACANCY2_ID = 2L;
    /** Title of 2nd vacancy  */
    static final String VACANCY2_TITLE = "Лесоруб";
    /** Description of 2nd vacancy */
    static final String VACANCY2_SHORT_DESCRIPTION = "Умеет клёво рубить сосны";
    /** 2nd vacancy date of creation */
    static final Date VACANCY2_DATE = new Date();
    /** Id of 3rd vacancy */
    static final Long VACANCY3_ID = 4L;
    /** Title of 3nd vacancy  */
    static final String VACANCY3_TITLE = "Стекольщик";
    /** Description of 3rd vacancy */
    static final String VACANCY3_SHORT_DESCRIPTION = "Стеклит!";
    /** 3rd vacancy date of creation */
    static final Date VACANCY3_DATE = new Date();

    @RequestMapping(value = "employer-my-vacancies", method = RequestMethod.GET)
    public ModelAndView showMyVacancies() {
        Long myId = getMyId();
        ModelAndView myVacancies = new ModelAndView("myVacancies");
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
