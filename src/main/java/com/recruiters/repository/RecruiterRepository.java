package com.recruiters.repository;

import com.recruiters.model.Recruiter;
import org.springframework.stereotype.Repository;

/**
 * Repository for working with recruiter
 */
@Repository
public class RecruiterRepository {

    /** Id of 1st recruiter */
    static final Long RECRUITER1_ID = 1L;
    /** First name of 1st recruiter */
    static final String RECRUITER1_FIRST_NAME = "Рек";
    /** Last name of 1st recruiter */
    static final String RECRUITER1_LAST_NAME = "Рекрутов";


    public Recruiter getByUserId(final Long userId) {
        return new Recruiter(1L, "Вася", "Пупкин");
    }

    public Recruiter findRecruiterByUserId(final Long userId) {
        Recruiter recruiter = new Recruiter(RECRUITER1_ID, RECRUITER1_FIRST_NAME, RECRUITER1_LAST_NAME);
        if (userId.equals(RECRUITER1_ID)) {
            return recruiter;
        } else {
            return null;
        }

    }
}
