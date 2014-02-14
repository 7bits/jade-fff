package com.recruiters.model;

import com.recruiters.model.status.DealStatus;

import java.util.Date;
import java.util.List;

/**
 *  Deal POJO
 */
public class Deal {
    private Long id = null;
    private Vacancy vacancy = null;
    private Recruiter recruiter = null;
    private List<Applicant> applicants = null;
    private DealStatus status = null;
    private Boolean employerArchived = false;
    private Boolean recruiterArchived = false;
    private Long allApplicantCount = 0L;
    private Long rejectedApplicantCount = 0L;
    private Long viewedApplicantCount = 0L;
    private Long unseenApplicantCount = 0L;
    private Date dateCreated = null;
    private Date lastModified = null;
    private Bid bid;
    private Long bidCount;
    private Feedback feedback;

    public Deal(final Long id, final Vacancy vacancy, final Recruiter recruiter, final List<Applicant> applicants) {
        this.id = id;
        this.vacancy = vacancy;
        this.recruiter = recruiter;
        this.applicants = applicants;
        this.status = DealStatus.IN_PROGRESS;
    }

    public Deal() {
    }

    public Deal(final Long id) {
        this.id = id;
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

    public DealStatus getStatus() {
        return status;
    }

    public void setStatus(final DealStatus status) {
        this.status = status;
    }

    public Boolean getEmployerArchived() {
        return employerArchived;
    }

    public void setEmployerArchived(final Boolean employerArchived) {
        this.employerArchived = employerArchived;
    }

    public Boolean getRecruiterArchived() {
        return recruiterArchived;
    }

    public void setRecruiterArchived(final Boolean recruiterArchived) {
        this.recruiterArchived = recruiterArchived;
    }

    public Long getAllApplicantCount() {
        return allApplicantCount;
    }

    public void setAllApplicantCount(final Long allApplicantCount) {
        this.allApplicantCount = allApplicantCount;
    }

    public Long getRejectedApplicantCount() {
        return rejectedApplicantCount;
    }

    public void setRejectedApplicantCount(final Long rejectedApplicantCount) {
        this.rejectedApplicantCount = rejectedApplicantCount;
    }

    public Long getViewedApplicantCount() {
        return viewedApplicantCount;
    }

    public void setViewedApplicantCount(final Long viewedApplicantCount) {
        this.viewedApplicantCount = viewedApplicantCount;
    }

    public Long getUnseenApplicantCount() {
        return unseenApplicantCount;
    }

    public void setUnseenApplicantCount(final Long unseenApplicantCount) {
        this.unseenApplicantCount = unseenApplicantCount;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(final Date lastModified) {
        this.lastModified = lastModified;
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(final Bid bid) {
        this.bid = bid;
    }

    public Long getBidCount() {
        return bidCount;
    }

    public void setBidCount(final Long bidCount) {
        this.bidCount = bidCount;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(final Feedback feedback) {
        this.feedback = feedback;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(final Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
