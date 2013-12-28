package com.recruiters.web.form;

/**
 * Java Bean for Applicant Form
 */

import com.recruiters.model.Applicant;
import com.recruiters.model.Deal;
import org.springframework.web.multipart.MultipartFile;

/**
 * Java Bean Class for Applicant Form
 */
public class ApplicantForm {
    private Long id = 0L;
    private Long dealId = null;
    private String firstName = null;
    private String lastName = null;
    private String description = null;
    private MultipartFile resumeFile = null;
    private MultipartFile testAnswerFile = null;
    private String linkToResumeFile = null;
    private String linkToTestAnswerFile = null;

    public ApplicantForm() {
    }

    public ApplicantForm(final Long id, final String firstName, final String lastName, final String description) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
    }

    public ApplicantForm(final Applicant applicant) {
        this.id = applicant.getId();
        this.firstName = applicant.getFirstName();
        this.lastName = applicant.getLastName();
        this.description = applicant.getDescription();
        this.linkToResumeFile = applicant.getResumeFile();
        this.linkToTestAnswerFile = applicant.getTestAnswerFile();
        //TODO
        // temporary solve for dealId (replace by: this.dealId = applicant.getDeal().getId() )
        this.dealId = applicant.getDeal() != null ? applicant.getDeal().getId() : 1L;
    }

    public Applicant fillModel() {
        Applicant applicant = new Applicant();
        applicant.setId(this.getId() != null ? this.getId() : 0L);
        applicant.setFirstName(this.getFirstName());
        applicant.setLastName(this.getLastName());
        applicant.setDescription(this.getDescription());
        applicant.setDeal(new Deal(dealId));

        return applicant;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getDealId() {
        return dealId;
    }

    public void setDealId(final Long dealId) {
        this.dealId = dealId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public MultipartFile getResumeFile() {
        return resumeFile;
    }

    public void setResumeFile(final MultipartFile resumeFile) {
        this.resumeFile = resumeFile;
    }

    public MultipartFile getTestAnswerFile() {
        return testAnswerFile;
    }

    public void setTestAnswerFile(final MultipartFile testAnswerFile) {
        this.testAnswerFile = testAnswerFile;
    }

    public String getLinkToResumeFile() {
        return linkToResumeFile;
    }

    public void setLinkToResumeFile(final String linkToResumeFile) {
        this.linkToResumeFile = linkToResumeFile;
    }

    public String getLinkToTestAnswerFile() {
        return linkToTestAnswerFile;
    }

    public void setLinkToTestAnswerFile(final String linkToTestAnswerFile) {
        this.linkToTestAnswerFile = linkToTestAnswerFile;
    }
}