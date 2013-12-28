package com.recruiters.web.form;

import com.recruiters.model.Recruiter;

/**
 * JavaBean for RecruiterForm
 */
public class RecruiterForm {
    private Long id;
    private String firstName;
    private String lastName;
    private String oldPassword;
    private String newPassword;

    public RecruiterForm() {
    }

    public RecruiterForm(final Recruiter recruiter) {
        this.id = recruiter.getId();
        this.firstName = recruiter.getUser().getFirstName();
        this.lastName = recruiter.getUser().getLastName();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
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

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(final String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }
}
