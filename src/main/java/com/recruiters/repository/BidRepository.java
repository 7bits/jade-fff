package com.recruiters.repository;

import com.recruiters.model.Bid;
import com.recruiters.model.BidStatus;
import com.recruiters.repository.mapper.BidMapper;
import org.mybatis.spring.MyBatisSystemException;
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
    public Bid findById(final Long bidId) throws RepositoryException {
        if (bidId == null) {
            throw new RepositoryException("bidId is null");
        }
        try {

            return bidMapper.findById(bidId);
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Find all bids for exact vacancy
     * @param vacancyId    Vacancy Id
     * @return List of Bid POJO instances
     */
    public List<Bid> findBidsByVacancyId(final Long vacancyId) throws RepositoryException {
        if (vacancyId == null) {
            throw new RepositoryException("vacancyId is null");
        }
        try {

            return bidMapper.findBidsByVacancyId(vacancyId);
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Method must return list of recruiter bids.
     * @return
     */
    public List<Bid> findBidsByRecruiterId(final Long recruiterId) {

        return bidMapper.findBidsByRecruiterId(recruiterId);
    }

    public Boolean create(final Long recruiterId, final Long vacancyId, final String message) {
         try {
             bidMapper.create(recruiterId, vacancyId, message);
             return true;
         } catch (Exception e) {
             return false;
         }
    }

    public Long updateStatus(final Long bidId, final BidStatus status)
            throws RepositoryException {
        if (bidId == null || status == null) {
            throw new RepositoryException("bidId or status is null");
        }
        try {
            bidMapper.updateStatus(bidId, status);

            return bidId;
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    public BidMapper getBidMapper() {
        return bidMapper;
    }

    public void setBidMapper(final BidMapper bidMapper) {
        this.bidMapper = bidMapper;
    }
}
