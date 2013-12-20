package com.recruiters.web.controller.recruiter;

import com.recruiters.model.Vacancy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller Class for R12 "My vacancies"
 */
@Controller("recruiterMyVacancy")
public class MyVacancies {
    final static Long RECRUITER_ID = 1L;

    /**
     * Controller for R2 "My vacancies"
     * @return model and view with list of active vacancies
     */
    @RequestMapping(value = "/recruiter/myVacancies", method = RequestMethod.GET)
    public ModelAndView showMyVacancies() {
        Long myId = getMyId();
        ModelAndView myVacancies = new ModelAndView ("myVacancies");
        List<Vacancy> vacancyList = getMyVacancies (myId);
        myVacancies.addObject ("vacancyList", vacancyList);
        return myVacancies;
    }

    /**
     * Fake getMyVacancies method, should be real service method of RecruiterService in future
     * @return list of my vacancies
     */
    private List<Vacancy> getMyVacancies (Long recruiterId) {
        final Long RECRUITER1_ID = 1L;
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
        List<Vacancy> myVacancies = new ArrayList<Vacancy>();
        Vacancy vacancy1 = new Vacancy (VACANCY1_ID, VACANCY1_TITLE, VACANCY1_SHORT_DESCRIPTION, VACANCY1_DATE);
        Vacancy vacancy2 = new Vacancy (VACANCY2_ID, VACANCY2_TITLE, VACANCY2_SHORT_DESCRIPTION, VACANCY2_DATE);
        Vacancy vacancy3 = new Vacancy (VACANCY3_ID, VACANCY3_TITLE, VACANCY3_SHORT_DESCRIPTION, VACANCY3_DATE);
        myVacancies.add (vacancy1);
        myVacancies.add (vacancy2);
        myVacancies.add (vacancy3);
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
