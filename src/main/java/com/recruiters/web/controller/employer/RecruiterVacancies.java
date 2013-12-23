package com.recruiters.web.controller.employer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controller Class for C3 "Vacancies with recruiter"
 */
@Controller
public class RecruiterVacancies {
    /** Id of employer who is currently visiting page */
    static final Long EMPLOYER_ID = 1L;
    /** Id of 1st vacancy in fake DB */
    static final Long VACANCY1_ID = 1L;
    /** Title of 1st vacancy  */
    static final String VACANCY1_TITLE = "Программист";
    /** Description of 1st vacancy */
    static final String VACANCY1_DESCRIPTION = "PHP guru";
    /** 1st vacancy date of creation */
    static final Date VACANCY1_DATE = new Date();
    /** 1st vacancy Recruiter Id */
    static final Long VACANCY1_RECRUITER_ID = 1L;
    /** 1st vacancy active recruiter name */
    static final String VACANCY1_RECRUITER_NAME = "Иванов";
    /** Id of 2nd vacancy */
    static final Long VACANCY2_ID = 2L;
    /** Title of 2nd vacancy  */
    static final String VACANCY2_TITLE = "Лесоруб";
    /** Description of 2nd vacancy */
    static final String VACANCY2_DESCRIPTION = "Умеет клёво рубить сосны";
    /** 2nd vacancy date of creation */
    static final Date VACANCY2_DATE = new Date();
    /** 2nd vacancy Recruiter Id */
    static final Long VACANCY2_RECRUITER_ID = 1L;
    /** 2nd vacancy active recruiter name */
    static final String VACANCY2_RECRUITER_NAME = "Иванов";
    /** Id of 3rd vacancy */
    static final Long VACANCY3_ID = 4L;
    /** Title of 3nd vacancy  */
    static final String VACANCY3_TITLE = "Стекольщик";
    /** Description of 3rd vacancy */
    static final String VACANCY3_DESCRIPTION = "Стеклит!";
    /** 3rd vacancy date of creation */
    static final Date VACANCY3_DATE = new Date();
    /** 3rd vacancy Recruiter Id */
    static final Long VACANCY3_RECRUITER_ID = 1L;
    /** 3rd vacancy active recruiter name */
    static final String VACANCY3_RECRUITER_NAME = "Иванов";

    /**
     * Controller method for C3 "Vacancies with recruiter" page
     * @return model and view with vacancies
     */
    @RequestMapping(value = "employer-progress-vacancies-list", method = RequestMethod.GET)
    public ModelAndView showMyVacancies() {
        Long myId = getMyId();
        ModelAndView myVacancies = new ModelAndView("employer-progress-vacancies-list.jade");
        List<VacancyForRecruiterVacancies> vacancyList = getVacanciesWithRecruiters(myId);
        myVacancies.addObject("vacancyList", vacancyList);
        return myVacancies;
    }

    /**
     * Fake getVacanciesWithRecruiters method, should be real service method of EmployerService in future
     * @return list of vacancies with active recruiters
     */
    private List<VacancyForRecruiterVacancies> getVacanciesWithRecruiters(final Long employerId) {

        List<VacancyForRecruiterVacancies> myVacancies = new ArrayList<VacancyForRecruiterVacancies>();
        VacancyForRecruiterVacancies vacancy1 = new VacancyForRecruiterVacancies(VACANCY1_ID, VACANCY1_TITLE,
                VACANCY1_DESCRIPTION, VACANCY1_DATE, VACANCY1_RECRUITER_ID, VACANCY1_RECRUITER_NAME);
        VacancyForRecruiterVacancies vacancy2 = new VacancyForRecruiterVacancies(VACANCY2_ID, VACANCY2_TITLE,
                VACANCY2_DESCRIPTION, VACANCY2_DATE, VACANCY2_RECRUITER_ID, VACANCY2_RECRUITER_NAME);
        VacancyForRecruiterVacancies vacancy3 = new VacancyForRecruiterVacancies(VACANCY3_ID, VACANCY3_TITLE,
                VACANCY3_DESCRIPTION, VACANCY3_DATE, VACANCY3_RECRUITER_ID, VACANCY3_RECRUITER_NAME);
        myVacancies.add(vacancy1);
        myVacancies.add(vacancy2);
        myVacancies.add(vacancy3);
        if (employerId.equals(EMPLOYER_ID)) {
            return myVacancies;
        } else {
            return null;
        }
    }

    /**
     * Fake method to get id of current employer
     * Should be implemented according to security behaviour
     * @return id of current employer
     */
    private Long getMyId() {
        return EMPLOYER_ID;
    }

    /**
     * Vacancy POJO class for current controller
     */
    public class VacancyForRecruiterVacancies {
        private Long id;
        private String title;
        private String description;
        private Date creationDate;
        private Long recruiterId;
        private String recruiterName;

        VacancyForRecruiterVacancies() {
        }

        VacancyForRecruiterVacancies(final Long id, final String title, final String description,
                                     final Date creationDate, final Long recruiterId, final String recruiterName) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.creationDate = creationDate;
            this.recruiterId = recruiterId;
            this.recruiterName = recruiterName;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(final String description) {
            this.description = description;
        }

        public Date getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(final Date creationDate) {
            this.creationDate = creationDate;
        }

        public Long getRecruiterId() {
            return recruiterId;
        }

        public void setRecruiterId(final Long recruiterId) {
            this.recruiterId = recruiterId;
        }

        public String getRecruiterName() {
            return recruiterName;
        }

        public void setRecruiterName(final String recruiterName) {
            this.recruiterName = recruiterName;
        }
    }
}
