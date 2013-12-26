package com.recruiters.repository;

import com.recruiters.model.Deal;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository for working with deal
 */
@Repository
public class DealRepository {

    public Deal getById(final Long id) {
        VacancyRepository vacancyRepository = new VacancyRepository();
        EmployerRepository employerRepository = new EmployerRepository();
        RecruiterRepository recruiterRepository = new RecruiterRepository();
        ApplicantRepository applicantRepository = new ApplicantRepository();

        Deal deal1 = new Deal(1L, vacancyRepository.getById(1L), recruiterRepository.getByUserId(1L), applicantRepository.getApplicantByDealId(1L));
        Deal deal2 = new Deal(2L, vacancyRepository.getById(2L), recruiterRepository.getByUserId(1L), applicantRepository.getApplicantByDealId(1L));
        Deal deal3 = new Deal(3L, vacancyRepository.getById(3L), recruiterRepository.getByUserId(1L), applicantRepository.getApplicantByDealId(1L));

        if (id.equals(1L)) {
            return deal1;
        }
        if (id.equals(2L)) {
            return deal2;
        }
        if (id.equals(3L)) {
            return deal3;
        }

        return null;
    }

    public List<Deal> findAllActiveByRecruiterId(final Long recruiterId) {

        List <Deal> deals = new ArrayList<Deal>();
        VacancyRepository vacancyRepository = new VacancyRepository();
        EmployerRepository employerRepository = new EmployerRepository();
        RecruiterRepository recruiterRepository = new RecruiterRepository();
        ApplicantRepository applicantRepository = new ApplicantRepository();

        Deal deal1 = new Deal(1L, vacancyRepository.getById(1L), recruiterRepository.getByUserId(1L), applicantRepository.getApplicantByDealId(1L));
        Deal deal2 = new Deal(2L, vacancyRepository.getById(2L), recruiterRepository.getByUserId(1L), applicantRepository.getApplicantByDealId(1L));
        Deal deal3 = new Deal(3L, vacancyRepository.getById(3L), recruiterRepository.getByUserId(1L), applicantRepository.getApplicantByDealId(1L));

        deals.add(deal1);
        deals.add(deal2);
        deals.add(deal3);

        return deals;
    }

    public Deal findByRecruiterIdAndVacancyId(final Long recruiterId, final Long vacancyId) {
        VacancyRepository vacancyRepository = new VacancyRepository();
        EmployerRepository employerRepository = new EmployerRepository();
        RecruiterRepository recruiterRepository = new RecruiterRepository();
        ApplicantRepository applicantRepository = new ApplicantRepository();
        if (vacancyId.equals(1L)) {
            return new Deal(1L, vacancyRepository.getById(1L), recruiterRepository.getByUserId(1L), applicantRepository.getApplicantByDealId(1L));
        }
        if (vacancyId.equals(2L)) {
            return new Deal(2L, vacancyRepository.getById(2L), recruiterRepository.getByUserId(1L), applicantRepository.getApplicantByDealId(1L));
        }
        if (vacancyId.equals(3L)) {
            return new Deal(3L, vacancyRepository.getById(3L), recruiterRepository.getByUserId(1L), applicantRepository.getApplicantByDealId(1L));
        }

        return  null;
    }

    /**
     * Lists all active deals for exact Employer
     * @param employerId    Id of employer
     * @return List of POJO Deal instances
     */
    public List<Deal> findAllActiveByEmployerId(final Long employerId) {

        List <Deal> deals = new ArrayList<Deal>();
        if (employerId.equals(1L)) {
            VacancyRepository vacancyRepository = new VacancyRepository();
            RecruiterRepository recruiterRepository = new RecruiterRepository();
            ApplicantRepository applicantRepository = new ApplicantRepository();

            Deal deal1 = new Deal(1L, vacancyRepository.getById(1L), recruiterRepository.getByUserId(1L), applicantRepository.getApplicantByDealId(1L));
            Deal deal2 = new Deal(2L, vacancyRepository.getById(2L), recruiterRepository.getByUserId(1L), applicantRepository.getApplicantByDealId(1L));
            Deal deal3 = new Deal(3L, vacancyRepository.getById(3L), recruiterRepository.getByUserId(1L), applicantRepository.getApplicantByDealId(1L));

            deals.add(deal1);
            deals.add(deal2);
            deals.add(deal3);

            return deals;
        }

        return null;
    }

}