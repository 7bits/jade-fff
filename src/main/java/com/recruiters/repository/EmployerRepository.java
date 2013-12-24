package com.recruiters.repository;

import com.recruiters.model.Employer;
import com.recruiters.model.Recruiter;
import org.springframework.stereotype.Repository;

/**
 * Repository for working with employer
 */
@Repository
public class EmployerRepository {

    /** Id of 1st employer */
    static final Long EMPLOYER1_ID = 1L;
    /** First name of 1st employer */
    static final String EMPLOYER1_FIRST_NAME = "Василий";
    /** Last name of 1st employer */
    static final String EMPLOYER1_LAST_NAME = "Иванов";

    public Employer getByUserId(final Long userId) {
        return new Employer(1L, "Пупко", "Васькин");
    }

    public Employer getEmployerByVacancyId(final Long employerId) {
        Employer employer = new Employer(EMPLOYER1_ID, EMPLOYER1_FIRST_NAME, EMPLOYER1_LAST_NAME);
        if (employerId.equals(EMPLOYER1_ID)) {
            return employer;
        } else {
            return null;
        }
    }
}
