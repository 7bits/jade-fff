package com.recruiters.web.controller.employer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for C5 "Show recruiter info to employer"
 */
@Controller
public class EmployerRecruiterDeal {
    /** Id of 1st recruiter */
    static final Long RECRUITER1_ID = 1L;
    /** Name of 1st recruiter */
    static final String RECRUITER1_FIRST_NAME = "Иван";
    /** Last name of 1st resruiter */
    static final String RECRUITER1_LAST_NAME = "Иванов";
    /** Terms of 1st recruiter */
    static final String RECRUITER1_TERMS = "По нижеследующему договору. Donec id elit non mi porta gravida" +
            " at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, " +
            "ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis " +
            "euismod. Donec sed odio dui.";
    /** Id of 1st vacancy*/
    static final Long VACANCY1_ID = 1L;
    /** Description for 1st vacancy */
    static final String VACANCY1_DESCRIPTION = "Лесоруб";
    /** Salary for 1st vacancy */
    static final String VACANCY1_SALARY = "7000 - 15000 руб.";
    /** Id of 2nd vacancy*/
    static final Long VACANCY2_ID = 2L;
    /** Description for 2nd vacancy */
    static final String VACANCY2_DESCRIPTION = "Стекольщик";
    /** Salary for 2nd vacancy */
    static final String VACANCY2_SALARY = "9000 - 12000 руб.";
    /** Id of 3rd vacancy*/
    static final Long VACANCY3_ID = 3L;
    /** Description for 3rd vacancy */
    static final String VACANCY3_DESCRIPTION = "Каменщик";
    /** Salary for 3rd vacancy */
    static final String VACANCY3_SALARY = "7500 - 12000 руб.";

    /**
     * Method for C5 "Show recruiter info to employer page"
     * @return model and view with data
     */
    @RequestMapping(value = "employer-recruiter-show/recruiter/{recruiterId}/vacancy/{vacancyId}")
    public ModelAndView customerRecruitShow(@PathVariable final Long recruiterId,
                                            @PathVariable final Long vacancyId) {
        ModelAndView myModel = new ModelAndView("employer-recruiter-show.jade");
        myModel.addObject("recruiter", getRecruiterInfo(recruiterId));
        myModel.addObject("vacancy", getVacancyInfo(vacancyId));
        return myModel;
    }

    private RecruitApplication getRecruiterInfo(final Long recruiterId) {
        if (recruiterId.equals(RECRUITER1_ID)) {
            return new RecruitApplication(RECRUITER1_ID, RECRUITER1_FIRST_NAME, RECRUITER1_LAST_NAME, RECRUITER1_TERMS);
        } else {
            return null;
        }
    }

    private VacancyShortInfo getVacancyInfo(final Long vacancyId) {
        VacancyShortInfo vacancy1 = new VacancyShortInfo(VACANCY1_ID, VACANCY1_DESCRIPTION, VACANCY1_SALARY);
        VacancyShortInfo vacancy2 = new VacancyShortInfo(VACANCY2_ID, VACANCY2_DESCRIPTION, VACANCY2_SALARY);
        VacancyShortInfo vacancy3 = new VacancyShortInfo(VACANCY3_ID, VACANCY3_DESCRIPTION, VACANCY3_SALARY);
        if (vacancyId.equals(VACANCY1_ID)) {
            return vacancy1;
        } else if (vacancyId.equals(VACANCY2_ID)) {
            return vacancy2;
        } else if (vacancyId.equals(VACANCY3_ID)) {
            return vacancy3;
        } else {
            return null;
        }
    }

    /**
     * Controller-specific Vacancy POJO
     */
    class VacancyShortInfo {
        private Long id;
        private String description;
        private String salary;

        VacancyShortInfo() {
        }

        VacancyShortInfo(final Long id, final String description, final String salary) {
            this.id = id;
            this.description = description;
            this.salary = salary;
        }

        public Long getId() {
            return id;
        }

        public void setId(final Long id) {
            this.id = id;
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
    }

    /**
     * Controller-specific Recruiter POJO-class
     */
    class RecruitApplication {
        private Long id;
        private String firstName;
        private String lastName;
        private String terms;

        RecruitApplication() {
        }

        RecruitApplication(final Long id, final String firstName, final String lastName, final String terms) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.terms = terms;
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

        public String getTerms() {
            return terms;
        }

        public void setTerms(final String terms) {
            this.terms = terms;
        }
    }
}
