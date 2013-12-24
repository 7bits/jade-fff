package com.recruiters.model;

/**
 * Employer POJO Class
 */
public class Recruiter {
    private Long id;
    private String firstName;
    private String lastName;

    public Recruiter() {
    }

    public Recruiter(final Long id, final String firstName, final String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
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
}

