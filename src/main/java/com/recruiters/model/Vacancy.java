package com.recruiters.model;

/**
 * Vacancy POJO Class
 */
public class Vacancy {
    public Long id;
    public String title;
    public String shortDescription;
    public String creationDate;

    public Vacancy() {
    }

    public Vacancy(Long id, String title, String shortDescription, String creationDate) {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.creationDate = creationDate;
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
