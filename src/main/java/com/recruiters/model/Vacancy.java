package com.recruiters.model;

import java.util.Date;
import java.util.List;

/**
 * Vacancy POJO Class
 */
public class Vacancy {
    private Long id;
    private String title;
    private String shortDescription;
    private String longDescription;
    private Date creationDate;
    private Date expirationDate;
    private String salary;
    private Test test;

    private List<Applicant> applicantList;

    private List<Recruiter> recruiterList;
    // Can be null
    private Recruiter activeRecruiter;
    // Job search is over when is not null
    private Applicant activeApplicant;

    // Should consider more flags: archive etc
    private boolean isActive;
}
