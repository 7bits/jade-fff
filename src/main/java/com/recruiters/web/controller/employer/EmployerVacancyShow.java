package com.recruiters.web.controller.employer;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controller for C70 "Show progress of vacancy "
 */
@Controller
public class EmployerVacancyShow {
    /** Id of 1st vacancy */
    static final Long VACANCY1_ID = 1L;
    /** Title of 1st vacancy */
    static final String VACANCY1_TITLE = "";
    /** Description of 1st vacancy */
    static final String VACANCY1_DESCRIPTION = "";
    /** Salary for 1st vacancy */
    static final String VACANCY1_SALARY = "";
    /** Creation date of 1st vacancy */
    static final Date VACANCY1_CREATION_DATE = new Date();
    /** Expiration date of 1st vacancy */
    static final Date VACANCY1_EXPIRATION_DATE = DateUtils.addDays(new Date(), 1);
    /** 1st recruiter Id */
    static final Long RECRUITER1_ID = 1L;
    /** 1st recruiter first name */
    static final String RECRUITER1_NAME = "";
    /** 1st recruiter last name */
    static final String RECRUITER1_LAST_NAME = "";
    /** Id of vacancy following applicants are for */
    static final Long APPLICANTS_FOR_VACANCY_ID = 1L;
    /** Id of 1st applicant */
    static final Long APPLICANT1_ID = 1L;
    /** Name of 1st applicant */
    static final String APPLICANT1_FIRST_NAME = "Иван";
    /** Last name of 1st applicant */
    static final String APPLICANT1_LAST_NAME = "Иванов";
    /** Id of 2nd applicant */
    static final Long APPLICANT2_ID = 2L;
    /** Name of 2nd applicant */
    static final String APPLICANT2_FIRST_NAME = "Петр";
    /** Last name of 2nd applicant */
    static final String APPLICANT2_LAST_NAME = "Петров";
    /** Id of 3rd applicant */
    static final Long APPLICANT3_ID = 3L;
    /** Name of 3rd applicant */
    static final String APPLICANT3_FIRST_NAME = "Семен";
    /** Last name of 3rd applicant */
    static final String APPLICANT3_LAST_NAME = "Варламов";


    /**
     * Page controller for C70 "Show progress of vacancy"
     * @return model and view with data
     */
    @RequestMapping(value = "employer-progress-vacancy-show/vacancy/{vacancyId}/recruiter/{recruiterId}")
    public ModelAndView customerVacancyShow(@PathVariable final Long vacancyId,
                                            @PathVariable final Long recruiterId) {
        ModelAndView myModel =  new ModelAndView("employer-progress-vacancy-show.jade");
        myModel.addObject("vacancy", getVacancyById(vacancyId));
        myModel.addObject("recruiter", getRecruiterById(recruiterId));
        myModel.addObject("vacancyList", getApplicantListForVacancy(vacancyId));
        return myModel;
    }

    /**
     * Service method get Vacancy info by its id
     * @param vacancyId Id of vacancy
     * @return vacancy info
     */
    private VacancyForEmployer getVacancyById(final Long vacancyId) {
        if (vacancyId.equals(VACANCY1_ID)) {
            return new VacancyForEmployer(VACANCY1_ID, VACANCY1_TITLE, VACANCY1_DESCRIPTION, VACANCY1_SALARY,
                    VACANCY1_CREATION_DATE, VACANCY1_EXPIRATION_DATE);
        } else {
            return null;
        }
    }

    /**
     * Returns recruiter info using its id
     * @param recruiterId     if of recruiter
     * @return recruiter info
     */
    private RecruiterApplication getRecruiterById(final Long recruiterId) {
        if (recruiterId.equals(RECRUITER1_ID)) {
            return new RecruiterApplication(RECRUITER1_ID, RECRUITER1_NAME, RECRUITER1_LAST_NAME);
        } else {
            return null;
        }
    }

    /**
     * Service method for getting applicant list for current vacancy id
     * @param vacancyId Id of vacancy
     * @return list of applicants for this vacancy
     */
    private List<ApplicantForEmployer> getApplicantListForVacancy(final Long vacancyId) {
        if (vacancyId.equals(APPLICANTS_FOR_VACANCY_ID)) {
            List<ApplicantForEmployer> applicantList = new ArrayList<ApplicantForEmployer>();
            applicantList.add(new ApplicantForEmployer(APPLICANT1_ID, APPLICANT1_FIRST_NAME, APPLICANT1_LAST_NAME));
            applicantList.add(new ApplicantForEmployer(APPLICANT2_ID, APPLICANT2_FIRST_NAME, APPLICANT2_LAST_NAME));
            applicantList.add(new ApplicantForEmployer(APPLICANT3_ID, APPLICANT3_FIRST_NAME, APPLICANT3_LAST_NAME));
            return applicantList;
        } else {
            return null;
        }
    }

    /**
     * Vacancy Info POJO controller-specific
     */
    class VacancyForEmployer {
        private Long id;
        private String title;
        private String description;
        private String salary;
        private Date creationDate;
        private Date expirationDate;

        VacancyForEmployer() {
        }

        VacancyForEmployer(final Long id, final String title, final String description, final String salary,
                           final Date creationDate, final Date expirationDate) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.salary = salary;
            this.creationDate = creationDate;
            this.expirationDate = expirationDate;
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

        public String getSalary() {
            return salary;
        }

        public void setSalary(final String salary) {
            this.salary = salary;
        }

        public Date getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(final Date creationDate) {
            this.creationDate = creationDate;
        }

        public Date getExpirationDate() {
            return expirationDate;
        }

        public void setExpirationDate(final Date expirationDate) {
            this.expirationDate = expirationDate;
        }
    }

    /**
     * Controller-specific Recruiter POJO-class
     */
    class RecruiterApplication {
        private Long id;
        private String firstName;
        private String lastName;

        RecruiterApplication() {
        }

        RecruiterApplication(final Long id, final String firstName, final String lastName) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Long getId() {
            return id;
        }

        public void setId(final Long id) {
            this.id = id;
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

    /**
     *  Applicant POJO controller-specific
     */
    class ApplicantForEmployer {
        private Long id;
        private String firstName;
        private String lastName;

        ApplicantForEmployer() {
        }

        ApplicantForEmployer(final Long id, final String firstName, final String lastName) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Long getId() {
            return id;
        }

        public void setId(final Long id) {
            this.id = id;
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
}
