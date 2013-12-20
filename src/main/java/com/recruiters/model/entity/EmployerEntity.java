package com.recruiters.model.entity;

import java.util.List;

/**
 * EmployerEntity POJO Class
 */
public class EmployerEntity {
    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private List<VacancyEntity> vacancyList;
}
