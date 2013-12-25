package com.recruiters.model;

/**
 * Bid POJO Class
 */
public class Bid {
    private Long id = null;
    private Vacancy vacancy = null;
    private Recruiter recruiter = null;

    public Bid() {

    }

    public Bid(final Long id, final Vacancy vacancy, final Recruiter recruiter) {
        this.id = id;
        this.vacancy = vacancy;
        this.recruiter = recruiter;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(final Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    public Recruiter getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(final Recruiter recruiter) {
        this.recruiter = recruiter;
    }
}
