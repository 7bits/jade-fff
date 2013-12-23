package com.recruiters.repository;

import com.recruiters.model.Applicant;
import org.springframework.stereotype.Repository;

/**
 * Repository for working with applicant
 */
@Repository
public class ApplicantRepository {

    public Boolean saveApplicant(final Applicant applicant) {
        return true;
    }
}
