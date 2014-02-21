package com.recruiters.repository;

import com.recruiters.model.Deal;
import com.recruiters.model.status.DealStatus;
import com.recruiters.repository.exception.RepositoryException;
import com.recruiters.repository.mapper.DealMapper;
import com.recruiters.repository.specification.impl.deal.EmployerDealListSpecification;
import com.recruiters.repository.specification.impl.deal.RecruiterDealListSpecification;
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
     * Find and return filtered deals for certain recruiter
     * @param recruiterId              Id of recruiter
     * @param dealListSpecification    Recruiter deal list specification
     * @return List of Deal instances, which belongs to exact recruiter with filter applied
     * @throws com.recruiters.repository.exception.RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public List<Deal> findFilteredDealsByRecruiterId(
            final Long recruiterId,
            final RecruiterDealListSpecification dealListSpecification)
            throws RepositoryException {
        if (recruiterId == null || dealListSpecification == null) {
            throw new RepositoryException("recruiterId or dealListSpecification is null");
        }
        try {

            return dealMapper.findFilteredDealsByRecruiterId(recruiterId, dealListSpecification);
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Find and return filtered deals for certain employer
     * @param employerId    Id of employer
     * @param employerDealListSpecification Specification for list of
     * @return List of Deal instances, which belongs to exact employer and
     * match specification
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public List<Deal> findFilteredDealsByEmployerId(
            final Long employerId,
            final EmployerDealListSpecification employerDealListSpecification
    ) throws RepositoryException {
        if (employerId == null || employerDealListSpecification == null) {
            throw new RepositoryException("employerId or listSpecification is null");
        }
        try {

            return dealMapper.findFilteredDealsByEmployerId(employerId, employerDealListSpecification);
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
     * Find and return ended deals without employer feedback
     * @param employerId    Id of employer
     * @return List of Deal instances that match conditions
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public List<Deal> findDealsForEmployerFeedback(final Long employerId)
            throws  RepositoryException {
        if (employerId == null) {
            throw new RepositoryException("employerId is null");
        }
        try {

            return dealMapper.findDealsForEmployerFeedback(employerId);
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }


    /**
     * Find and return ended deals without recruiter feedback
     * @param recruiterId    Id of recruiter
     * @return List of Deal instances that match conditions
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public List<Deal> findDealsForRecruiterFeedback(final Long recruiterId)
            throws  RepositoryException {
        if (recruiterId == null) {
            throw new RepositoryException("recruiterId is null");
        }
        try {

            return dealMapper.findDealsForRecruiterFeedback(recruiterId);
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Find and return ended deals without recruiter feedback
     * @param recruiterId    Id of recruiter
     * @return List of Deal instances that match conditions
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public List<Deal> findNewDealsForRecruiter(final Long recruiterId)
            throws  RepositoryException {
        if (recruiterId == null) {
            throw new RepositoryException("recruiterId is null");
        }
        try {

            return dealMapper.findNewDealsForRecruiter(recruiterId);
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Create deal from bid, do not touch bid
     * @param bidId    Id of bid
     * @return Id of created deal
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public Long create(final Long bidId) throws RepositoryException {
        if (bidId == null) {
            throw new RepositoryException("bidId is null");
        }
        try {
            Deal deal = new Deal();
            dealMapper.create(bidId, deal);

            return deal.getId();
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Update status of Deal
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

    /**
     * Fire Deal -  update status to fired
     * @param dealId    Id of deal
     * @return Id of deal, which status we've modified
     * @throws RepositoryException if input parameters are incorrect or there
     * were any technical issues
     */
    public Long fireRecruiter(final Long dealId)
            throws RepositoryException {
        if (dealId == null) {
            throw new RepositoryException("dealId is null");
        }
        try {
            dealMapper.fireRecruiter(dealId);

            return dealId;
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Clear fired deals (move to archive) for exact recruiter
     * @param recruiterId    Id of recruiter
     * @return Id of recruiter if success
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public Long clearFiredByRecruiterId(final Long recruiterId) throws RepositoryException {
        if (recruiterId == null) {
            throw new RepositoryException("recruiterId is null");
        }
        try {
            dealMapper.clearFiredByRecruiterId(recruiterId);

            return recruiterId;
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Clear approved deals (move to archive) for exact recruiter
     * @param recruiterId    Id of recruiter
     * @return Id of recruiter if success
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public Long clearApprovedByRecruiterId(final Long recruiterId) throws RepositoryException {
        if (recruiterId == null) {
            throw new RepositoryException("recruiterId is null");
        }
        try {
            dealMapper.clearApprovedByRecruiterId(recruiterId);

            return recruiterId;
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }


    /**
     * Set Deal viewed state to true
     * @param dealId    Deal Id
     * @return Deal Id if it was successfully updated
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public Long setViewed(final Long dealId) throws RepositoryException {
        if (dealId == null) {
            throw new RepositoryException("bidId is null");
        }
        try {

            dealMapper.setViewed(dealId);
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
