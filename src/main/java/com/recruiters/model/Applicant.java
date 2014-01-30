package com.recruiters.model;

import com.recruiters.model.status.ApplicantStatus;

/**
 * Applicant POJO
 */
public class Applicant {
    private Long id;
    private Deal deal;
    private String firstName;
    private String lastName;
    private String description;
    private String sex;
    private Integer age;
    private Attachment resumeFile;
    private Attachment testAnswerFile;
    private ApplicantStatus status = null;
    private Boolean viewed = false;

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
                     final Integer age, final Attachment resumeFile, final Attachment testAnswerFile) {
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

    public Attachment getResumeFile() {
        return resumeFile;
    }

    public void setResumeFile(final Attachment resumeFile) {
        this.resumeFile = resumeFile;
    }

    public Attachment getTestAnswerFile() {
        return testAnswerFile;
    }

    public void setTestAnswerFile(final Attachment testAnswerFile) {
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

    public Boolean getViewed() {
        return viewed;
    }

    public void setViewed(final Boolean viewed) {
        this.viewed = viewed;
    }
}
