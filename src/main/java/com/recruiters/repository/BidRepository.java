package com.recruiters.repository;

import com.recruiters.model.Bid;
import com.recruiters.repository.mapper.BidMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository for working with bid
 */
@Repository
public class BidRepository {

    @Autowired
    private BidMapper bidMapper = null;

    /**
     * Method must return list of recruiter bids.
     * @return
     */
    public List<Bid> findListOfRecruiterBids(final Long recruiterId) {

        return bidMapper.findListOfRecruiterBids(recruiterId);
    }

    /**
     * Find all bids for vacancies of exact employer
     * @param employerId    Id of employer
     * @return List of Bid POJO instances
     */
    public List<Bid> findAllActiveByEmployerId(final Long employerId) {

        return bidMapper.findAllActiveByEmployerId(employerId);
    }

    /**
     * Get Bid by its id
     * @param bidId    Id of bid
     * @return Bid POJO instance
     */
    public Bid getBidById(final Long bidId) {

        return bidMapper.getBidById(bidId);
    }

    /**
     * Find all bids for exact vacancy
     * @param vacancyId    Vacancy Id
     * @return List of Bid POJO instances
     */
    public List<Bid> findAllBidsForVacancy(final Long vacancyId) {

        return bidMapper.findAllBidsForVacancy(vacancyId);
    }

    public BidMapper getBidMapper() {
        return bidMapper;
    }

    public void setBidMapper(final BidMapper bidMapper) {
        this.bidMapper = bidMapper;
    }
}
