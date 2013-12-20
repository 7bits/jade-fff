package com.recruiters.web.form;

/**
 * Java Bean for Applicant Form
 */

import org.springframework.web.multipart.MultipartFile;

/**
 * Java Bean Class for Applicant Form
 */
public class ApplicantForm {
    private Long id;
    private Long vacancyId;
    private String firstName;
    private String lastName;
    private String description;
    private MultipartFile resumeFile;
    private MultipartFile testAnswerFile;

    public ApplicantForm() {
    }

    public ApplicantForm(final Long id, final String firstName, final String lastName, final String description) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public MultipartFile getResumeFile() {
        return resumeFile;
    }

    public void setResumeFile(final MultipartFile resumeFile) {
        this.resumeFile = resumeFile;
    }

    public MultipartFile getTestAnswerFile() {
        return testAnswerFile;
    }

    public void setTestAnswerFile(final MultipartFile testAnswerFile) {
        this.testAnswerFile = testAnswerFile;
    }
}