package com.recruiters.model;

/**
 */
public class User {
    private Long id = null;
    private String username = null;
    private String password = null;
    private String firstName = null;
    private String lastName = null;
    private String description = null;

    public User() {

    }

    public User(final Long id, final String username, final String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(final Long id, final String username, final String password, final String firstName, final String lastName) {
        this(id, username, password);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(final Long id, final String username, final String password,
                final String firstName, final String lastName, final String description) {
        this(id, username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
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
}
