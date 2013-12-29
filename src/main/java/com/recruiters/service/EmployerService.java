package com.recruiters.service;

import com.recruiters.model.Applicant;
import com.recruiters.model.Bid;
import com.recruiters.model.Deal;
import com.recruiters.model.Employer;
import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.repository.ApplicantRepository;
import com.recruiters.repository.BidRepository;
import com.recruiters.repository.DealRepository;
import com.recruiters.repository.EmployerRepository;
import com.recruiters.repository.UserRepository;
import com.recruiters.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Service Class for Employer
 */
@Service
public class EmployerService {

    @Autowired
    private EmployerRepository employerRepository = null;
    @Autowired
    private DealRepository dealRepository = null;
    @Autowired
    private ApplicantRepository applicantRepository = null;
    @Autowired
    private BidRepository bidRepository = null;
    @Autowired
    private VacancyRepository vacancyRepository = null;

    /**
     * Finds Employer POJO instance by its user id
     * @param userId    User Id
     * @return Employer POJO instance
     */
    public Employer findEmployerByUser(final Long userId) {

        return employerRepository.findEmployerByUser(userId);
    }

    /**
     * Get all active deals from DB for current employer
     * @param employer    Employer POJO instance
     * @return list of deals
     */
    public List<Deal> findEmployerDeals(final Employer employer) {

        return dealRepository.findActiveDealsForEmployer(employer.getId());
    }

    /**
     * Returns deal for employer by its Id, using Employer instance as security measures
     * @param dealId      Id of deal
     * @param employer    Employer POJO instance
     * @return POJO Deal instance
     */
    public Deal findDealById(final Long dealId, final Employer employer) {

        Deal deal = dealRepository.findById(dealId);
        if (deal.getVacancy().getEmployer().getId().equals(employer.getId())) {
            return deal;
        }
        return null;
    }

    /**
     * Get applicant by its id and return it if it's related to current employer
     * otherwise return null
     * @param applicantId    Id of applicant
     * @param employer       Employer POJO instance
     * @return Applicant POJO instance
     */
    public Applicant findApplicantById(final Long applicantId, final Employer employer) {

        Applicant applicant = applicantRepository.findById(applicantId);
        if (applicant.getDeal().getVacancy().getEmployer().getId().equals(employer.getId())) {
            return applicant;
        }
        return null;
    }

    /**
     * Get all active bids from DB for current employer
     * @param employer    Employer POJO instance
     * @return list of bids
     */
    public List<Bid> findEmployerBids(final Employer employer) {

        return bidRepository.findBidsForEmployerVacancies(employer.getId());
    }

    /**
     * Get bids for exact vacancy from DB for current employer
     * @param vacancyId   Id of vacancy
     * @param employer    Employer POJO instance
     * @return list of bids
     */
    public List<Bid> findBidsForVacancy(final Long vacancyId, final Employer employer) {

        Vacancy vacancy = vacancyRepository.findById(vacancyId);
        if (vacancy.getEmployer().getId().equals(employer.getId())) {
            return bidRepository.findBidsForVacancy(vacancyId);
        }
        return null;
    }

    /**
     * Get bid by its id, employer verification required
     * @param bidId       Id of bid
     * @param employer    Employer POJO instance
     * @return Bid POJO instance
     */
    public Bid findBidById(final Long bidId, final Employer employer) {

        Bid bid = bidRepository.findById(bidId);
        if (bid.getVacancy().getEmployer().getId().equals(employer.getId())) {
            return bid;
        }
        return null;
    }

    /**
     * Find all vacancies for exact employer
     * @param employer    Employer POJO instance
     * @return List of vacancies
     */
    public List<Vacancy> findEmployerVacancies(final Employer employer) {

        return vacancyRepository.findEmployerVacancies(employer.getId());
    }


    /**
     * Find all vacancies for exact employer with count of bids for each
     * @param employer    Employer POJO instance
     * @return List of vacancies
     */
    public List<Vacancy> findEmployerVacanciesWithBidCount(final Employer employer) {

        return vacancyRepository.findEmployerVacanciesWithBidCount(employer.getId());
    }

    /**
     * Get Vacancy by its id if its related to exact employer
     * @param vacancyId    Id of vacancy
     * @param employer     Employer POJO instance
     * @return Vacancy POJO instance
     */
    public Vacancy findVacancyById(final Long vacancyId, final Employer employer) {
        Vacancy vacancy = vacancyRepository.findById(vacancyId);
        if (vacancy.getEmployer().getId().equals(employer.getId())) {
            return vacancy;
        }
        return null;
    }
}
