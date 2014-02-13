package com.recruiters.model;

/**
 * Feedback POJO
 */
public class Feedback {
    private Long id = null;
    private Deal deal = null;
    private Recruiter recruiter = null;
    private Employer employer = null;
    private String recruiterFeedback = null;
    private String employerFeedback = null;

    public Feedback() {

    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(final Deal deal) {
        this.deal = deal;
    }

    public Recruiter getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(final Recruiter recruiter) {
        this.recruiter = recruiter;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(final Employer employer) {
        this.employer = employer;
    }

    public String getRecruiterFeedback() {
        return recruiterFeedback;
    }

    public void setRecruiterFeedback(final String recruiterFeedback) {
        this.recruiterFeedback = recruiterFeedback;
    }

    public String getEmployerFeedback() {
        return employerFeedback;
    }

    public void setEmployerFeedback(final String employerFeedback) {
        this.employerFeedback = employerFeedback;
    }
}
