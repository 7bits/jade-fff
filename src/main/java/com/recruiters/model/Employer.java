package com.recruiters.model;

import java.util.List;

/**
 * Employer POJO Class
 */
public class Employer {
    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private List<Vacancy> vacancyList;
}
