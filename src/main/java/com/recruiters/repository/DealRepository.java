package com.recruiters.repository;

import com.recruiters.model.Deal;
import com.recruiters.model.DealStatus;
import com.recruiters.repository.mapper.DealMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository implementing all methods related to
 * Deal Entity manipulations with MyBatis
 */
@Repository
public class DealRepository {

    /** MyBatis Deal Mapper */
    @Autowired
    private DealMapper dealMapper = null;

    /**
     * Find and return all active deals for certain recruiter
     * @param recruiterId    Id of recruiter
     * @return List of Deal instances, which belongs to exact recruiter and
     * have "IN PROGRESS" Status
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public List<Deal> findActiveDealsByRecruiterId(final Long recruiterId)
            throws  RepositoryException {
        if (recruiterId == null) {
            throw new RepositoryException("recruiterId is null");
        }
        try {

            return dealMapper.findActiveDealsByRecruiterId(recruiterId);
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Find and return all active deals for certain employer
     * @param employerId    Id of employer
     * @return List of Deal instances, which belongs to exact employer and
     * have "IN PROGRESS" Status
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public List<Deal> findActiveDealsByEmployerId(final Long employerId)
            throws RepositoryException {
        if (employerId == null) {
            throw new RepositoryException("employerId is null");
        }
        try {

            return dealMapper.findActiveDealsByEmployerId(employerId);
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Find and return deal by its id
     * @param dealId    Id of deal
     * @return Deal POJO instance
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public Deal findById(final Long dealId)
            throws RepositoryException {
        if (dealId == null) {
            throw new RepositoryException("dealId is null");
        }
        try {

            return dealMapper.findById(dealId);
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Create deal from bid, do not touch bid
     * @param bidId    Id of bid
     * @return Id of bid which translates to new deal
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public Long create(final Long bidId) throws RepositoryException {
        if (bidId == null) {
            throw new RepositoryException("bidId is null");
        }
        try {

            dealMapper.create(bidId);
            return bidId;
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Set new status for deal
     * @param dealId    Id of deal
     * @param status    New status of deal
     * @return Id of deal, which status we've modified
     * @throws RepositoryException if input parameters are incorrect or there
     * were any technical issues
     */
    public Long updateStatus(final Long dealId, final DealStatus status)
            throws RepositoryException {
        if (dealId == null || status == null) {
            throw new RepositoryException("dealId or status is null");
        }
        try {
            dealMapper.updateStatus(dealId, status);

            return dealId;
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    public DealMapper getDealMapper() {
        return dealMapper;
    }

    public void setDealMapper(final DealMapper dealMapper) {
        this.dealMapper = dealMapper;
    }
}
