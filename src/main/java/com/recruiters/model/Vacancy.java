package com.recruiters.model;

import com.recruiters.model.status.VacancyStatus;

import java.util.Date;

/**
 * Vacancy POJO
 */
public class Vacancy {
    private Long id;
    private Employer employer = null;
    private String title;
    private String description;
    private Long salaryFrom;
    private Long salaryTo;
    private Date creationDate;
    private Date expirationDate;
    private Attachment testFile;
    private VacancyStatus status = null;
    private Long bidCount = 0L;
    private Long bidId = 0L;
    private Long dealId = 0L;

    public Vacancy() {
    }

    public Vacancy(final Long id) {
        this.id = id;
    }

    public Vacancy(final Long id, final Employer employer, final String title, final String description, final Date creationDate) {
        this.id = id;
        this.employer = employer;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.status = VacancyStatus.ACTIVE;
    }

    public Vacancy(final Long id, final Employer employer, final String title,
                   final String description, final Long salaryFrom, final Long salaryTo,
                   final Date creationDate, final Date expirationDate, final Attachment testFile) {
        this.id = id;
        this.employer = employer;
        this.title = title;
        this.description = description;
        this.salaryFrom = salaryFrom;
        this.salaryTo = salaryTo;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.testFile = testFile;
        this.status = VacancyStatus.ACTIVE;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(final Employer employer) {
        this.employer = employer;
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

    public Long getSalaryFrom() {
        return salaryFrom;
    }

    public void setSalaryFrom(final Long salaryFrom) {
        this.salaryFrom = salaryFrom;
    }

    public Long getSalaryTo() {
        return salaryTo;
    }

    public void setSalaryTo(final Long salaryTo) {
        this.salaryTo = salaryTo;
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

    public Attachment getTestFile() {
        return testFile;
    }

    public void setTestFile(final Attachment testFile) {
        this.testFile = testFile;
    }

    public VacancyStatus getStatus() {
        return status;
    }

    public void setStatus(final VacancyStatus status) {
        this.status = status;
    }

    public Long getBidCount() {
        return bidCount;
    }

    public void setBidCount(final Long bidCount) {
        this.bidCount = bidCount;
    }

    public Long getBidId() {
        return bidId;
    }

    public void setBidId(final Long bidId) {
        this.bidId = bidId;
    }

    public Long getDealId() {
        return dealId;
    }

    public void setDealId(final Long dealId) {
        this.dealId = dealId;
    }
}
