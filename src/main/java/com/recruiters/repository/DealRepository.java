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
            throws RepositoryGeneralException, RepositoryTechnicalException {
        try {
            return dealMapper.findActiveDealsByEmployerId(employerId);
        } catch (MyBatisSystemException e) {
            throw new RepositoryTechnicalException("Database connection error: ", e);
        } catch (Exception e) {
            throw new RepositoryGeneralException("General database error: ", e);
        }
    }

    public Deal findByIdAndEmployerId(final Long dealId, final Long employerId) {

        return dealMapper.findByIdAndEmployerId(dealId, employerId);
    }

    public Boolean create(final Long bidId) {

        try {
            dealMapper.create(bidId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public DealMapper getDealMapper() {
        return dealMapper;
    }

    public void setDealMapper(final DealMapper dealMapper) {
        this.dealMapper = dealMapper;
    }
}
