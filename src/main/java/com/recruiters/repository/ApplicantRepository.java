package com.recruiters.repository;

import com.recruiters.model.Applicant;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository for working with applicant
 */
@Repository
public class ApplicantRepository {

    /** Id of 1st applicant */
    static final Long APPLICANT1_ID = 1L;
    /** Vacancy id applicant belongs to */
    static final Long APPLICANT1_VACANCY_ID = 1L;
    /** 1st Applicant first name */
    static final String APPLICANT1_FIRST_NAME = "Иван";
    /** 1st Applicant last name */
    static final String APPLICANT1_LAST_NAME = "Иванов";
    /** Id of 2nd applicant */
    static final Long APPLICANT2_ID = 2L;
    /** Vacancy id applicant belongs to */
    static final Long APPLICANT2_VACANCY_ID = 1L;
    /** 2nd Applicant first name */
    static final String APPLICANT2_FIRST_NAME = "Пётр";
    /** 2nd Applicant last name */
    static final String APPLICANT2_LAST_NAME = "Петров";
    /** Id of 3rd applicant */
    static final Long APPLICANT3_ID = 3L;
    /** Vacancy id applicant belongs to */
    static final Long APPLICANT3_VACANCY_ID = 1L;
    /** 3rd Applicant first name */
    static final String APPLICANT3_FIRST_NAME = "Константин";
    /** 3rd Applicant last name */
    static final String APPLICANT3_LAST_NAME = "Константинопольский";


    /**
     *  Method must return list of applicants for given deal
     * @param dealId
     * @return
     */
    public List<Applicant> getAppByDealId(final Long dealId) {
        List<Applicant> applicantList = new ArrayList<Applicant>();
        DealRepository dealRepository = new DealRepository();

        Applicant applicant1 = new Applicant(APPLICANT1_ID, dealRepository.getById(1L),
                APPLICANT1_FIRST_NAME, APPLICANT1_LAST_NAME);
        Applicant applicant2 = new Applicant(APPLICANT2_ID, dealRepository.getById(2L),
                APPLICANT2_FIRST_NAME, APPLICANT2_LAST_NAME);
        Applicant applicant3 = new Applicant(APPLICANT3_ID, dealRepository.getById(3L),
                APPLICANT3_FIRST_NAME, APPLICANT3_LAST_NAME);
        applicantList.add(applicant1);
        applicantList.add(applicant2);
        applicantList.add(applicant3);
        if (dealId.equals(APPLICANT1_VACANCY_ID)) {
            return applicantList;
        } else {
            return null;
        }

    }

    /**
     * Method must save Applicant model
     * @param applicant
     * @return <tt>true</tt> if model has been saved successful, <tt>false</tt> - otherwise
     */
    public Boolean saveApplicant(final Applicant applicant) {
        return true;
    }
}
