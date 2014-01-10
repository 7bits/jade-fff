package com.recruiters.repository;

import com.recruiters.model.Bid;
import com.recruiters.model.BidStatus;
import com.recruiters.repository.mapper.BidMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for working with bid
 */
@Repository
public class BidRepository {

    @Autowired
    private BidMapper bidMapper = null;

    /**
     * Get Bid by its id
     * @param bidId    Id of bid
     * @return Bid POJO instance
     */
    public Bid findById(final Long bidId) {

        return bidMapper.findById(bidId);
    }

    /**
     * Find all bids for exact vacancy
     * @param vacancyId    Vacancy Id
     * @return List of Bid POJO instances
     */
    public List<Bid> findBidsByVacancyId(final Long vacancyId) {

        return bidMapper.findBidsForVacancy(vacancyId);
    }

    /**
     * Method must return list of recruiter bids.
     * @return
     */
    public List<Bid> findBidsByRecruiterId(final Long recruiterId) {

        return bidMapper.findRecruiterBids(recruiterId);
    }

    public Boolean createBid(final Long recruiterId, final Long vacancyId, final String message) {
         try {
             bidMapper.createBid(recruiterId, vacancyId, message);
             return true;
         } catch (Exception e) {
             return false;
         }
    }

    public Boolean updateBidStatus(final Long bidId, final BidStatus status) {
        try {
            bidMapper.updateBidStatus(bidId, status);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public BidMapper getBidMapper() {
        return bidMapper;
    }

    public void setBidMapper(final BidMapper bidMapper) {
        this.bidMapper = bidMapper;
    }
}
