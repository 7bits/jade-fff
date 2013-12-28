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
    private UserRepository userRepository = null;
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
     * Detached user getter, should be combined with equal getter
     * in RecruiterService Class in future
     * @param request Http request
     * @return User POJO instance
     */
    public User getCurrentUser(final HttpServletRequest request) {

        return userRepository.getCurrentUser(request);
    }

    /**
     * Finds Employer POJO instance by its user id
     * @param userId    User Id
     * @return Employer POJO instance
     */
    public Employer findEmployerByUserId(final Long userId) {

        return employerRepository.getEmployerByUserId(userId);
    }

    /**
     * Returns deal for employer by its Id, using Employer instance as security measures
     * @param dealId      Id of deal
     * @param employer    Employer POJO instance
     * @return POJO Deal instance
     */
    public Deal getDealById(final Long dealId, final Employer employer) {

        Deal deal = dealRepository.getById(dealId);
        if (deal.getVacancy().getEmployer().getId().equals(employer.getId())) {
            return deal;
        }
        return null;
    }

    /**
     * Get all active deals from DB for current employer
     * @param employer    Employer POJO instance
     * @return list of deals
     */
    public List<Deal> findEmployerDeals(final Employer employer) {

        return dealRepository.findAllActiveByEmployerId(employer.getId());
    }

    /**
     * Get applicant by its id and return it if it's related to current employer
     * otherwise return null
     * @param applicantId    Id of applicant
     * @param employer       Employer POJO instance
     * @return Applicant POJO instance
     */
    public Applicant getApplicantById(final Long applicantId, final Employer employer) {

        Applicant applicant = applicantRepository.getApplicantById(applicantId);
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

        return bidRepository.findAllActiveByEmployerId(employer.getId());
    }


    /**
     * Get bids for exact vacancy from DB for current employer
     * @param vacancyId   Id of vacancy
     * @param employer    Employer POJO instance
     * @return list of bids
     */
    public List<Bid> findBidsForVacancy(final Long vacancyId, final Employer employer) {

        Vacancy vacancy = vacancyRepository.getById(vacancyId);
        if (vacancy.getEmployer().getId().equals(employer.getId())) {
            return bidRepository.findAllBidsForVacancy(vacancyId);
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
     * Get bid by its id, employer verification required
     * @param bidId       Id of bid
     * @param employer    Employer POJO instance
     * @return Bid POJO instance
     */
    public Bid getBidById(final Long bidId, final Employer employer) {

        Bid bid = bidRepository.getBidById(bidId);
        if (bid.getVacancy().getEmployer().getId().equals(employer.getId())) {
            return bid;
        }
        return null;
    }

    /**
     * Get Vacancy by its id if its related to exact employer
     * @param vacancyId    Id of vacancy
     * @param employer     Employer POJO instance
     * @return Vacancy POJO instance
     */
    public Vacancy getVacancyById(final Long vacancyId, final Employer employer) {
        Vacancy vacancy = vacancyRepository.getById(vacancyId);
        if (vacancy.getEmployer().getId().equals(employer.getId())) {
            return vacancy;
        }
        return null;
    }
}
