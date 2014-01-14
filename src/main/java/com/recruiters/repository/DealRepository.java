package com.recruiters.repository;

import com.recruiters.model.Deal;
import com.recruiters.model.DealStatus;
import com.recruiters.repository.mapper.DealMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for working with deal
 */
@Repository
public class DealRepository {

    @Autowired
    private DealMapper dealMapper = null;

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
     * Lists all active deals for exact Employer
     * @param employerId    Id of employer
     * @return List of POJO Deal instances
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
