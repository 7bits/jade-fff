package com.recruiters.web.form;

import com.recruiters.model.Recruiter;
import com.recruiters.model.User;

/**
 * Recruiter Form
 */
public class RecruiterForm {
    private Long id;
    private String firstName;
    private String lastName;
    private String description;
    private String oldPassword;
    private String newPassword;

    public RecruiterForm() {
    }

    public RecruiterForm(final Recruiter recruiter) {
        this.id = recruiter.getId();
        this.firstName = recruiter.getUser().getFirstName();
        this.lastName = recruiter.getUser().getLastName();
        this.description = recruiter.getUser().getDescription();
    }

    public Recruiter fillModel(final User user) {
        Recruiter recruiter = new Recruiter();
        recruiter.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDescription(description);
        if (!newPassword.isEmpty()) {
            user.setPassword(newPassword);
        }
        recruiter.setUser(user);

        return recruiter;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
