package com.recruiters.model;

import java.util.Date;

/**
 * Feedback POJO
 */
public class Feedback {
    private Long id = null;
    private Deal deal = null;
    private Recruiter recruiter = null;
    private Employer employer = null;
    private String recruiterFeedback = null;
    private Date recruiterTime = null;
    private String employerFeedback = null;
    private Date employerTime = null;

    public Feedback() {

    }

    public Feedback(final Deal deal, final Recruiter recruiter, final Employer employer) {
        this.deal = deal;
        this.recruiter = recruiter;
        this.employer = employer;
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

    public Date getRecruiterTime() {
        return recruiterTime;
    }

    public void setRecruiterTime(final Date recruiterTime) {
        this.recruiterTime = recruiterTime;
    }

    public Date getEmployerTime() {
        return employerTime;
    }

    public void setEmployerTime(final Date employerTime) {
        this.employerTime = employerTime;
    }
}
