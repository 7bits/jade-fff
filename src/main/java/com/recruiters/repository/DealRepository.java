package com.recruiters.repository;

import com.recruiters.model.Deal;
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

    // TODO
    // param should be recruiter ID
    public List<Deal> findActiveDealsByRecruiterId(final Long recruiterId) {

        return dealMapper.findActiveDealsByRecruiterId(recruiterId);
    }

    // TODO
    // param should be recruiter ID
    public Deal findDealByDealIdAndRecruiterId(final Long dealId, final Long recruiterId) {

        return dealMapper.findDealByDealIdAndRecruiterId(dealId, recruiterId);
    }

    /**
     * Lists all active deals for exact Employer
     * @param employerId    Id of employer
     * @return List of POJO Deal instances
     */
    public List<Deal> findActiveDealsByEmployerId(final Long employerId) {

        return dealMapper.findActiveDealsByEmployerId(employerId);
    }

    // TODO
    // param should be recruiter ID
    public Deal findDealByIdAndEmployerId(final Long dealId, final Long employerId) {

        return dealMapper.findDealByIdAndEmployerId(dealId, employerId);
    }

    public Boolean createDeal(final Long bidId) {

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
