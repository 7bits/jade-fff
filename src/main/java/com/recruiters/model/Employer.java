package com.recruiters.model;

import java.util.List;

/**
 * Employer POJO
 */
public class Employer {
    private Long id = null;
    private User user = null;
    private List<Feedback> feedbacks = null;

    public Employer() {
    }

    public Employer(final Long id) {
        this.id = id;
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

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(final List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
}

