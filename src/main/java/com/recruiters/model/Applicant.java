package com.recruiters.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * Java Bean Class for Applicant
 */
public class Applicant {
    private Long id;
    private String firstName;
    private String lastName;
    private String description;
    private String resumeFile;
    private String testAnswerFile;

    public Applicant() {
    }

    public Applicant(Long id, String firstName, String lastName, String description) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResumeFile() {
        return resumeFile;
    }

    public void setResumeFile(String resumeFile) {
        this.resumeFile = resumeFile;
    }

    public String getTestAnswerFile() {
        return testAnswerFile;
    }

    public void setTestAnswerFile(String testAnswerFile) {
        this.testAnswerFile = testAnswerFile;
    }
}
