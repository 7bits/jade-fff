package com.recruiters.repository;

import com.recruiters.model.Bid;
import com.recruiters.model.BidStatus;
import com.recruiters.model.Recruiter;
import com.recruiters.model.Vacancy;
import com.recruiters.repository.mapper.BidMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository implementing all methods related to
 * Bid Entity manipulations with MyBatis
 */
@Repository
public class BidRepository {

    /** MyBatis Bid Mapper */
    @Autowired
    private BidMapper bidMapper = null;

    /**
     * Find and return bid by its id
     * @param bidId    Id of bid
     * @return Bid POJO instance
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
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
     * Find and return all bids for certain vacancy
     * @param vacancyId    Id of vacancy
     * @return List of Bid instances, which belongs to exact vacancy
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
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
     * Find and return all  bids for certain recruiter
     * @param recruiterId    Id of recruiter
     * @return List of Bid instances, which belongs to exact recruiter
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public List<Bid> findBidsByRecruiterId(final Long recruiterId) throws RepositoryException {
        if (recruiterId == null) {
            throw new RepositoryException("vacancyId is null");
        }
        try {
            return bidMapper.findBidsByRecruiterId(recruiterId);
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Find and return active Bid by id and recruiter id
     * certain recruiter
     * @param bidId      Id of bid
     * @param recruiterId    Id of recruiter
     * @return Bid instance
     * @throws RepositoryException if input parameters are incorrect or there
     * were any technical issues
     */
    public Bid findActiveBidById(
            final Long bidId,
            final Long recruiterId
    ) throws RepositoryException {
        if (bidId == null || recruiterId == null) {
            throw new RepositoryException("bidId or recruiterId is null");
        }
        try {
            return bidMapper.findActiveBidById(bidId);
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }


    /**
     * Create bid from certain vacancy, applying to it Recruiter
     * with custom message
     * @param recruiterId    Id of recruiter
     * @param vacancyId      Id of vacancy
     * @param message        Custom message, may be null
     * @return Id of created bid
     * @throws RepositoryException if input parameters are incorrect or there
     * were any technical issues
     */
    public Long create(
            final Long recruiterId,
            final Long vacancyId,
            final String message
    ) throws RepositoryException {
        if (recruiterId == null || vacancyId == null) {
            throw new RepositoryException("recruiterId or vacancyId is null");
        }
        try {
            Bid bid = new Bid(null, new Vacancy(vacancyId), new Recruiter(recruiterId), message);
            bidMapper.create(bid);

            return bid.getId();
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Update status of Bid
     * @param bidId     Id of bid
     * @param status    New status of bid
     * @return Id of bid, which status we've modified
     * @throws RepositoryException if input parameters are incorrect or there
     * were any technical issues
     */
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
