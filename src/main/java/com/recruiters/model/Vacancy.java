package com.recruiters.model;

import java.util.Date;

/**
 * Vacancy POJO Class
 */
public class Vacancy {
    private Long id;
    private String title;
    private String shortDescription;
    private String longDescription;
    private Date expirationDate;
    private String salary;
//    private Test test;
//
//    private List<Recruiter> recruiterList;
//    // Can be null
//    private Recruiter activeRecruiter;
//    // Job search is over when is not null
//    private Applicant activeApplicant;

    // Should consider more flags: archive etc
    private boolean isActive;
    private String creationDate;

    public Vacancy() {
    }

    public Vacancy(final Long id, final String title, final String shortDescription, final String creationDate) {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.creationDate = creationDate;
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(final String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final String creationDate) {
        this.creationDate = creationDate;
    }
}
