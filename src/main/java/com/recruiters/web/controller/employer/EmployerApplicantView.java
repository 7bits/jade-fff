package com.recruiters.web.controller.employer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for C71 "View applicant"
 */
@Controller
public class EmployerApplicantView {

    /** Id of the employer who is visiting this page*/
    static final Long EMPLOYER_ID = 1L;
    /** Id of 1st applicant */
    static final Long APPLICANT1_ID = 1L;
    /** Name of 1st applicant */
    static final String APPLICANT1_FIRST_NAME = "Иван";
    /** Last name of 1st applicant */
    static final String APPLICANT1_LAST_NAME = "Иванов";
    /** Description of 1st applicant */
    static final String APPLICANT1_DESCRIPTION = "Круто рубит дрова";
    /** Age of 1st applicant */
    static final Integer APPLICANT1_AGE = 25;
    /** Sex of 1st applicant*/
    static final String APPLICANT1_SEX = "Мужской";
    /** Url of test answer for 1st applicant */
    static final String APPLICANT1_TEST_ANSWER_URL = "#";
    /** Url for resume for 1st applicant*/
    static final String APPLICANT1_RESUME_URL = "#";
    /** Id of 2nd applicant */
    static final Long APPLICANT2_ID = 2L;
    /** Name of 2nd applicant */
    static final String APPLICANT2_FIRST_NAME = "Петр";
    /** Last name of 2nd applicant */
    static final String APPLICANT2_LAST_NAME = "Петров";
    /** Description of 2nd applicant */
    static final String APPLICANT2_DESCRIPTION = "Рубит дрова так что щепки во все стороны летят";
    /** Age of 2nd applicant */
    static final Integer APPLICANT2_AGE = 34;
    /** Sex of 2nd applicant*/
    static final String APPLICANT2_SEX = "Мужской";
    /** Url of test answer for 2nd applicant */
    static final String APPLICANT2_TEST_ANSWER_URL = "#";
    /** Url for resume for 2nd applicant*/
    static final String APPLICANT2_RESUME_URL = "#";
    /** Id of 3rd applicant */
    static final Long APPLICANT3_ID = 3L;
    /** Name of 3rd applicant */
    static final String APPLICANT3_FIRST_NAME = "Семен";
    /** Last name of 3rd applicant */
    static final String APPLICANT3_LAST_NAME = "Варламов";
    /** Description of 3rd applicant */
    static final String APPLICANT3_DESCRIPTION = "Рубит дрова с двух рук!";
    /** Age of 3rd applicant */
    static final Integer APPLICANT3_AGE = 31;
    /** Sex of 3rd applicant*/
    static final String APPLICANT3_SEX = "Мужской";
    /** Url of test answer for 3rd applicant */
    static final String APPLICANT3_TEST_ANSWER_URL = "#";
    /** Url for resume for 3rd applicant*/
    static final String APPLICANT3_RESUME_URL = "#";

    /**
     * Page controller for C71 "View applicant"
     * @return model and view with data
     */
    @RequestMapping(value = "employer-employee-show/{applicantId}")
    public ModelAndView employeeShow(@PathVariable final Long applicantId) {

        Long employerId = getMyId();
        ModelAndView myModel = new ModelAndView("employer-employee-show.jade");
        ApplicantForEmployer applicant = getApplicantById(employerId, applicantId);
        myModel.addObject("applicant", applicant);
        return myModel;
    }

    /**
     * Returns applicant info by its id
     * @param employerId     Id of employer for security purposes
     * @param applicantId    Id of applicant
     * @return Applicant info
     */
    private ApplicantForEmployer getApplicantById(final Long employerId, final Long applicantId) {

        ApplicantForEmployer applicant1 = new ApplicantForEmployer(APPLICANT1_ID,
                APPLICANT1_FIRST_NAME, APPLICANT1_LAST_NAME, APPLICANT1_DESCRIPTION,
                APPLICANT1_AGE, APPLICANT1_SEX, APPLICANT1_RESUME_URL, APPLICANT1_TEST_ANSWER_URL);
        ApplicantForEmployer applicant2 = new ApplicantForEmployer(APPLICANT2_ID,
                APPLICANT2_FIRST_NAME, APPLICANT2_LAST_NAME, APPLICANT2_DESCRIPTION,
                APPLICANT2_AGE, APPLICANT2_SEX, APPLICANT2_RESUME_URL, APPLICANT2_TEST_ANSWER_URL);
        ApplicantForEmployer applicant3 = new ApplicantForEmployer(APPLICANT3_ID,
                APPLICANT3_FIRST_NAME, APPLICANT3_LAST_NAME, APPLICANT3_DESCRIPTION,
                APPLICANT3_AGE, APPLICANT3_SEX, APPLICANT3_RESUME_URL, APPLICANT3_TEST_ANSWER_URL);

        if (applicantId.equals(APPLICANT1_ID)) {
            return applicant1;
        } else if (applicantId.equals(APPLICANT2_ID)) {
            return applicant2;
        } else if (applicantId.equals(APPLICANT3_ID)) {
            return applicant3;
        } else {
            return null;
        }
    }

    /**
     * Applicant POJO controller-specific
     */
    public class ApplicantForEmployer {
        private Long id;
        private String firstName;
        private String lastName;
        private String description;
        private Integer age;
        private String sex;
        private String resumeUrl;
        private String testAnswerUrl;

        ApplicantForEmployer() {
        }

        ApplicantForEmployer(final Long id, final String firstName, final String lastName,
                           final String description, final Integer age, final String sex,
                           final String resumeUrl, final String testAnswerUrl) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.description = description;
            this.age = age;
            this.sex = sex;
            this.resumeUrl = resumeUrl;
            this.testAnswerUrl = testAnswerUrl;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(final String description) {
            this.description = description;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(final Integer age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(final String sex) {
            this.sex = sex;
        }

        public String getResumeUrl() {
            return resumeUrl;
        }

        public void setResumeUrl(final String resumeUrl) {
            this.resumeUrl = resumeUrl;
        }

        public String getTestAnswerUrl() {
            return testAnswerUrl;
        }

        public void setTestAnswerUrl(final String testAnswerUrl) {
            this.testAnswerUrl = testAnswerUrl;
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
}
