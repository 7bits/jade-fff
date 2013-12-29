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

    public Deal findById(final Long id) {

        return dealMapper.findById(id);
    }

    public List<Deal> findActiveDealsForRecruiter(final Long recruiterId) {

        return dealMapper.findActiveDealsByRecruiterId(recruiterId);
    }

    /**
     * Lists all active deals for exact Employer
     * @param employerId    Id of employer
     * @return List of POJO Deal instances
     */
    public List<Deal> findActiveDealsForEmployer(final Long employerId) {

        return dealMapper.findActiveDealsByEmployerId(employerId);
    }

    public DealMapper getDealMapper() {
        return dealMapper;
    }

    public void setDealMapper(final DealMapper dealMapper) {
        this.dealMapper = dealMapper;
    }
}
