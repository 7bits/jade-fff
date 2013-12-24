package com.recruiters.model;

/**
 *  Deal POJO Class
 */
public class Deal {
    private Long id = null;
    private Vacancy vacancy = null;
    private Recruiter recruiter = null;

    public Deal(final Long id, final Vacancy vacancy, final Recruiter recruiter) {
        this.id = id;
        this.vacancy = vacancy;
        this.recruiter = recruiter;
    }

    public Deal() {
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
