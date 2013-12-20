package com.recruiters.model.entity;

import java.util.Date;
import java.util.List;

/**
 * VacancyEntity POJO Class
 */
public class VacancyEntity {
    private Long id;
    private String title;
    private String shortDescription;
    private String longDescription;
    private Date creationDate;
    private Date expirationDate;
    private String salary;
    private TestEntity test;

    private List<ApplicantEntity> applicantList;

    private List<RecruiterEntity> recruiterList;
    // Can be null
    private RecruiterEntity activeRecruiter;
    // Job search is over when is not null
    private ApplicantEntity activeApplicant;

    // Should consider more flags: archive etc
    private boolean isActive;
}
