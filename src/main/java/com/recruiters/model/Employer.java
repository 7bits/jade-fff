package com.recruiters.model;

/**
 * Employer POJO Class
 */
public class Employer {
    private Long id = null;
    private User user = null;

    public Employer() {
    }

    public Employer(final Long id, final User user) {
        this.id = id;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }
}

