package com.recruiters.repository;

import com.recruiters.model.Deal;
import com.recruiters.repository.mapper.DealMapper;
import org.mybatis.spring.MyBatisSystemException;
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

    public List<Deal> findActiveDealsByRecruiterId(final Long recruiterId) {

        return dealMapper.findActiveDealsByRecruiterId(recruiterId);
    }

    public Deal findByDealIdAndRecruiterId(final Long dealId, final Long recruiterId) {

        return dealMapper.findByDealIdAndRecruiterId(dealId, recruiterId);
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

    public Deal findByIdAndEmployerId(final Long dealId, final Long employerId)
            throws RepositoryException {
        if (dealId == null || employerId == null) {
            throw new RepositoryException("dealId or employerId is null");
        }
        try {

            return dealMapper.findByIdAndEmployerId(dealId, employerId);
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

    public DealMapper getDealMapper() {
        return dealMapper;
    }

    public void setDealMapper(final DealMapper dealMapper) {
        this.dealMapper = dealMapper;
    }
}
