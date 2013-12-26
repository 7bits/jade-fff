package com.recruiters.model;

/**
 * Applicant POJO Class
 */
public class Applicant {
    private Long id;
    private Deal deal;
    private String firstName;
    private String lastName;
    private String description;
    private String sex;
    private Integer age;
    private String resumeFile;
    private String testAnswerFile;
    private ApplicantStatus status = null;

    public Applicant() {
    }

    public Applicant(final Long id, final Deal deal, final String firstName, final String lastName) {
        this.id = id;
        this.deal = deal;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = ApplicantStatus.IN_PROGRESS;
    }

    public Applicant(final Long id, final Deal deal, final String firstName,
                     final String lastName, final String description, final String sex,
                     final Integer age, final String resumeFile, final String testAnswerFile) {
        this.id = id;
        this.deal = deal;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.status = ApplicantStatus.IN_PROGRESS;
        this.sex = sex;
        this.age = age;
        this.resumeFile = resumeFile;
        this.testAnswerFile = testAnswerFile;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(final Deal deal) {
        this.deal = deal;
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

    public String getResumeFile() {
        return resumeFile;
    }

    public void setResumeFile(final String resumeFile) {
        this.resumeFile = resumeFile;
    }

    public String getTestAnswerFile() {
        return testAnswerFile;
    }

    public void setTestAnswerFile(final String testAnswerFile) {
        this.testAnswerFile = testAnswerFile;
    }

    public ApplicantStatus getStatus() {
        return status;
    }

    public void setStatus(final ApplicantStatus status) {
        this.status = status;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(final String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }
}
