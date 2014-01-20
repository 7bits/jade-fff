package com.recruiters.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;

/**
 * User POJO
 */
public class User implements UserDetails {
    private Long id = null;
    private String username = null;
    private String password = null;
    private String firstName = null;
    private String lastName = null;
    private String description = null;
    private Long employerId = null;
    private Long recruiterId = null;
    private Role role = null;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection <GrantedAuthority> roles = new LinkedList<GrantedAuthority>();
        roles.add(this.getRole());

        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }

    public GrantedAuthority getRole() {

        GrantedAuthority role = this.role;
        if (role == null) {
            if (this.employerId != null) {
                role = Role.createEmployerRole();
            } else if (this.recruiterId != null) {
                role = Role.createRecruiterRole();
            }
            this.role = (Role) role;
        }

        return role;
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

    public Long getEmployerId() {
        return employerId;
    }

    public void setEmployerId(final Long employerId) {
        this.employerId = employerId;
    }

    public Long getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(final Long recruiterId) {
        this.recruiterId = recruiterId;
    }
}
