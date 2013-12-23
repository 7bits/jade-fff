package com.recruiters.web.controller.employer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller Class for C4 "Choosing recruiter"
 */
@Controller
public class RecruiterSearch {

    /** ID of current employer */
    static final Long EMPLOYER_ID = 1L;
    /** Id of 1st vacancy */
    static final Long VACANCY1_ID = 1L;
    /** Title of 1st vacancy */
    static final String VACANCY1_TITLE = "Лесоруб";
    /** Id of 2nd vacancy */
    static final Long VACANCY2_ID = 2L;
    /** Title of 2nd vacancy */
    static final String VACANCY2_TITLE = "Сантехник";
    /** Id of 1st recruiter */
    static final Long RECRUITER1_ID = 1L;
    /** Corresponding vacancy id  for 1st recruiter */
    static final Long RECRUITER1_VACANCY_ID = 1L;
    /** 1st recruiter name */
    static final String RECRUITER1_NAME = "Иван";
    /** 1st recruiter last name */
    static final String RECRUITER1_LAST_NAME = "Иванов";
    /** Id of 2nd recruiter */
    static final Long RECRUITER2_ID = 2L;
    /** Corresponding vacancy id  for 2nd recruiter */
    static final Long RECRUITER2_VACANCY_ID = 1L;
    /** 2nd recruiter name */
    static final String RECRUITER2_NAME = "Пётр";
    /** 2nd recruiter last name */
    static final String RECRUITER2_LAST_NAME = "Петров";
    /** Id of 3rd recruiter */
    static final Long RECRUITER3_ID = 3L;
    /** Corresponding vacancy id  for 3rd recruiter */
    static final Long RECRUITER3_VACANCY_ID = 1L;
    /** 3rd recruiter name */
    static final String RECRUITER3_NAME = "Константин";
    /** 3rd recruiter last name */
    static final String RECRUITER3_LAST_NAME = "Константинопольский";
    /** Id of 4th recruiter */
    static final Long RECRUITER4_ID = 1L;
    /** Corresponding vacancy id  for 4th recruiter */
    static final Long RECRUITER4_VACANCY_ID = 2L;
    /** 4th recruiter name */
    static final String RECRUITER4_NAME = "Иван";
    /** 4th recruiter last name */
    static final String RECRUITER4_LAST_NAME = "Иванов";
    /** Id of 5th recruiter */
    static final Long RECRUITER5_ID = 2L;
    /** Corresponding vacancy id  for 5th recruiter */
    static final Long RECRUITER5_VACANCY_ID = 2L;
    /** 5th recruiter name */
    static final String RECRUITER5_NAME = "Пётр";
    /** 5th recruiter last name */
    static final String RECRUITER5_LAST_NAME = "Петров";
    /** 6thof 3rd recruiter */
    static final Long RECRUITER6_ID = 3L;
    /** Corresponding vacancy id  for 6th recruiter */
    static final Long RECRUITER6_VACANCY_ID = 2L;
    /** 6th recruiter name */
    static final String RECRUITER6_NAME = "Константин";
    /** 6th recruiter last name */
    static final String RECRUITER6_LAST_NAME = "Константинопольский";

    /**
     * Controller for C4 "Choosing recruiter"
     * @return model and view with data
     */
    @RequestMapping(value = "employer-recruiter-search")
    public ModelAndView customerRecruitSearch() {
        ModelAndView myModel = new ModelAndView("employer-recruiter-search");
        Long employerId = getMyId();
        List<VacancyForSearch> vacancyList = getVacanciesWithRecruiters(employerId);
        myModel.addObject("vacancyList", vacancyList);
        List<RecruiterForSearch> recruiterList = getRecruitersAssociatedWithVacancies(employerId);
        // recruiter have field vacancyId, so combine lists on it
        myModel.addObject("recruiterList", recruiterList);
        return myModel;
    }

    /**
     * Get vacancies for current employer service method
     * @param employerId    Id of employer
     * @return list of vacancies
     */
    private List<VacancyForSearch> getVacanciesWithRecruiters(final Long employerId) {
        List<VacancyForSearch> myVacancies = new ArrayList<VacancyForSearch>();
        if (employerId.equals(EMPLOYER_ID)) {
            List<VacancyForSearch> vacancyList = new ArrayList<VacancyForSearch>();
            vacancyList.add(new VacancyForSearch(VACANCY1_ID, VACANCY1_TITLE));
            vacancyList.add((new VacancyForSearch(VACANCY2_ID, VACANCY2_TITLE)));
            return vacancyList;
        } else {
            return null;
        }
    }

    private List<RecruiterForSearch> getRecruitersAssociatedWithVacancies(final Long employerId) {
        if (employerId.equals(EMPLOYER_ID)) {
            List<RecruiterForSearch> recruiterList = new ArrayList<RecruiterForSearch>();
            recruiterList.add(new RecruiterForSearch(RECRUITER1_ID,
                    RECRUITER1_VACANCY_ID, RECRUITER1_NAME, RECRUITER1_LAST_NAME));
            recruiterList.add((new RecruiterForSearch(RECRUITER2_ID,
                    RECRUITER2_VACANCY_ID, RECRUITER2_NAME, RECRUITER2_LAST_NAME)));
            recruiterList.add((new RecruiterForSearch(RECRUITER3_ID,
                    RECRUITER3_VACANCY_ID, RECRUITER3_NAME, RECRUITER3_LAST_NAME)));
            recruiterList.add((new RecruiterForSearch(RECRUITER4_ID,
                    RECRUITER4_VACANCY_ID, RECRUITER4_NAME, RECRUITER4_LAST_NAME)));
            recruiterList.add((new RecruiterForSearch(RECRUITER5_ID,
                    RECRUITER5_VACANCY_ID, RECRUITER5_NAME, RECRUITER5_LAST_NAME)));
            recruiterList.add((new RecruiterForSearch(RECRUITER6_ID,
                    RECRUITER6_VACANCY_ID, RECRUITER6_NAME, RECRUITER6_LAST_NAME)));
            return recruiterList;
        } else {
            return null;
        }
    }

    /**
     * Controller-specific POJO for Vacancy
     */
    private final class VacancyForSearch {
        private Long id;
        private String title;

        private VacancyForSearch() {
        }

        private VacancyForSearch(final Long id, final String title) {
            this.id = id;
            this.title = title;
        }

        public Long getId() {
            return id;
        }

        public void setId(final Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(final String title) {
            this.title = title;
        }
    }

    /**
     * Controller-specific Recruiter POJO
     */
    private final class RecruiterForSearch {
        private Long id;
        private Long vacancyId;
        private String firstName;
        private String lastName;

        private RecruiterForSearch() {
        }

        private RecruiterForSearch(final Long id, final Long vacancyId,
                                   final String firstName, final String lastName) {
            this.id = id;
            this.vacancyId = vacancyId;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Long getId() {
            return id;
        }

        public void setId(final Long id) {
            this.id = id;
        }

        public Long getVacancyId() {
            return vacancyId;
        }

        public void setVacancyId(final Long vacancyId) {
            this.vacancyId = vacancyId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(final String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(final String lastName) {
            this.lastName = lastName;
        }
    }

    private Long getMyId() {
        return EMPLOYER_ID;
    }
}
