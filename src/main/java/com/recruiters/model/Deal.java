package com.recruiters.model;

import java.util.List;

/**
 *  Deal POJO Class
 */
public class Deal {
    private Long id = null;
    private Vacancy vacancy = null;
    private Recruiter recruiter = null;
    private List<Applicant> applicants = null;

    public Deal(final Long id, final Vacancy vacancy, final Recruiter recruiter, final List<Applicant> applicants) {
        this.id = id;
        this.vacancy = vacancy;
        this.recruiter = recruiter;
        this.applicants = applicants;
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

    public List<Applicant> getApplicants() {
        return applicants;
    }

    public void setApplicants(final List<Applicant> applicants) {
        this.applicants = applicants;
    }
}
