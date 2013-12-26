package com.recruiters.model;

import java.util.Date;

/**
 * Vacancy POJO Class
 */
public class Vacancy {
    private Long id;
    private Employer employer = null;
    private String title;
    private String description;
    private String salary;
    private Date creationDate;
    private Date expirationDate;
    private String testFile;
    private VacancyStatus status = null;
    private Long bidCount = 0L;

    public Vacancy() {
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
                   final String description, final String salary,
                   final Date creationDate, final Date expirationDate, final String testFile) {
        this.id = id;
        this.employer = employer;
        this.title = title;
        this.description = description;
        this.salary = salary;
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

    public String getTestFile() {
        return testFile;
    }

    public void setTestFile(final String testFile) {
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
}
