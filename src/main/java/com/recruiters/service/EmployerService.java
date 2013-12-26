package com.recruiters.service;

import com.recruiters.model.Deal;
import com.recruiters.model.Employer;
import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.repository.DealRepository;
import com.recruiters.repository.EmployerRepository;
import com.recruiters.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 */
@Service
public class EmployerService {
    @Autowired
    private UserRepository userRepository = null;
    @Autowired
    private EmployerRepository employerRepository = null;
    @Autowired
    private DealRepository dealRepository = null;


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
     * Gets all active deals from DB for current employer
     * @param employer    Employer POJO instance
     * @return list of deals
     */
    public List<Deal> findEmployerDeals(final Employer employer) {

        return dealRepository.findAllActiveByEmployerId(employer.getId());
    }
}
