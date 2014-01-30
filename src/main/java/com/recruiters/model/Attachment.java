package com.recruiters.model;

import java.util.Date;

/**
 * Attachment POJO
 */
public class Attachment {
    private Long id;
    private String systemFilename;
    private String publicFilename;
    private Date creationDate;
    private Employer employer;
    private Recruiter recruiter;

    public Attachment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getSystemFilename() {
        return systemFilename;
    }

    public void setSystemFilename(final String systemFilename) {
        this.systemFilename = systemFilename;
    }

    public String getPublicFilename() {
        return publicFilename;
    }

    public void setPublicFilename(final String publicFilename) {
        this.publicFilename = publicFilename;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(final Employer employer) {
        this.employer = employer;
    }

    public Recruiter getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(final Recruiter recruiter) {
        this.recruiter = recruiter;
    }
}
