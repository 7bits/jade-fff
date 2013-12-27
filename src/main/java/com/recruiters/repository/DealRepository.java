package com.recruiters.repository;

import com.recruiters.model.Deal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository for working with deal
 */
@Repository
public class DealRepository {
    @Autowired
    private RecruiterRepository recruiterRepository = null;
    @Autowired
    private EmployerRepository employerRepository = null;
    @Autowired
    private VacancyRepository vacancyRepository = null;
    @Autowired
    private ApplicantRepository applicantRepository = null;

    public Deal getById(final Long id) {
        Deal deal1 = new Deal(1L, vacancyRepository.getById(1L), recruiterRepository.getById(1L), applicantRepository.getApplicantByDealId(1L));
        Deal deal2 = new Deal(2L, vacancyRepository.getById(2L), recruiterRepository.getById(1L), applicantRepository.getApplicantByDealId(1L));
        Deal deal3 = new Deal(3L, vacancyRepository.getById(3L), recruiterRepository.getById(1L), applicantRepository.getApplicantByDealId(1L));

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

        Deal deal1 = new Deal(1L, vacancyRepository.getById(1L), recruiterRepository.getById(1L), applicantRepository.getApplicantByDealId(1L));
        Deal deal2 = new Deal(2L, vacancyRepository.getById(2L), recruiterRepository.getById(1L), applicantRepository.getApplicantByDealId(1L));
        Deal deal3 = new Deal(3L, vacancyRepository.getById(3L), recruiterRepository.getById(1L), applicantRepository.getApplicantByDealId(1L));

        deals.add(deal1);
        deals.add(deal2);
        deals.add(deal3);

        return deals;
    }

    /**
     * Lists all active deals for exact Employer
     * @param employerId    Id of employer
     * @return List of POJO Deal instances
     */
    public List<Deal> findAllActiveByEmployerId(final Long employerId) {
        List <Deal> deals = new ArrayList<Deal>();
        if (employerId.equals(1L)) {

            Deal deal1 = new Deal(1L, vacancyRepository.getById(1L), recruiterRepository.getById(1L), applicantRepository.getApplicantByDealId(1L));
            Deal deal2 = new Deal(2L, vacancyRepository.getById(2L), recruiterRepository.getById(1L), applicantRepository.getApplicantByDealId(1L));
            Deal deal3 = new Deal(3L, vacancyRepository.getById(3L), recruiterRepository.getById(1L), applicantRepository.getApplicantByDealId(1L));

            deals.add(deal1);
            deals.add(deal2);
            deals.add(deal3);

            return deals;
        }

        return null;
    }

    public RecruiterRepository getRecruiterRepository() {
        return recruiterRepository;
    }

    public void setRecruiterRepository(final RecruiterRepository recruiterRepository) {
        this.recruiterRepository = recruiterRepository;
    }

    public EmployerRepository getEmployerRepository() {
        return employerRepository;
    }

    public void setEmployerRepository(final EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    public VacancyRepository getVacancyRepository() {
        return vacancyRepository;
    }

    public void setVacancyRepository(final VacancyRepository vacancyRepository) {
        this.vacancyRepository = vacancyRepository;
    }

    public ApplicantRepository getApplicantRepository() {
        return applicantRepository;
    }

    public void setApplicantRepository(final ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }
}
