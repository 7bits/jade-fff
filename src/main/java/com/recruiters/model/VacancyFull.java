package com.recruiters.model;

/**
 * Vacancy with full information about it
 */
public class VacancyFull {
    private Long id;
    private String title;
    private String description;
    private String salary;
    private String creationDate;
    private String expirationDate;
    private Long testId;

    public VacancyFull() {
    }

    public VacancyFull (Long id, String title, String description, String salary, String creationDate, String expirationDate, Long testId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.testId = testId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }
}
