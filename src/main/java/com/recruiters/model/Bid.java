package com.recruiters.model;

/**
 * Bid POJO
 */
public class Bid {
    private Long id = null;
    private Vacancy vacancy = null;
    private Recruiter recruiter = null;
    private String message = null;
    private BidStatus status = null;
    private Long dealId = 0L;

    public Bid() {

    }

    public Bid(final Long id, final Vacancy vacancy, final Recruiter recruiter) {
        this.id = id;
        this.vacancy = vacancy;
        this.recruiter = recruiter;
        this.status = BidStatus.ACTIVE;
    }

    public Bid(final Long id, final Vacancy vacancy, final Recruiter recruiter, final String message) {
        this.id = id;
        this.vacancy = vacancy;
        this.recruiter = recruiter;
        this.message = message;
        this.status = BidStatus.ACTIVE;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public BidStatus getStatus() {
        return status;
    }

    public void setStatus(final BidStatus status) {
        this.status = status;
    }

    public Long getDealId() {
        return dealId;
    }

    public void setDealId(final Long dealId) {
        this.dealId = dealId;
    }
}
