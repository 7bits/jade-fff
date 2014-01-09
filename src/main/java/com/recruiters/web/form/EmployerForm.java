package com.recruiters.web.form;

import com.recruiters.model.Employer;
import com.recruiters.model.Recruiter;
import com.recruiters.model.User;

/**
 * JavaBean for EmployerForm
 */
public class EmployerForm {
    private Long id;
    private String firstName;
    private String lastName;
    private String description;
    private String oldPassword;
    private String newPassword;

    public EmployerForm() {
    }

    public EmployerForm(final Employer employer) {
        this.id = employer.getId();
        this.firstName = employer.getUser().getFirstName();
        this.lastName = employer.getUser().getLastName();
        this.description = employer.getUser().getDescription();
    }

    public Employer fillModel(final User user) {
        Employer employer = new Employer();
        employer.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDescription(description);
        if (!newPassword.isEmpty()) {
            user.setPassword(newPassword);
        }
        employer.setUser(user);

        return employer;
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
