package com.recruiters.repository;

import com.recruiters.model.Deal;
import com.recruiters.repository.mapper.DealMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository for working with deal
 */
@Repository
public class DealRepository {

    @Autowired
    private DealMapper dealMapper = null;

    public Deal getById(final Long id) {

        Deal deal = dealMapper.getById(id);
        return deal;
    }

    public List<Deal> findAllActiveByRecruiterId(final Long recruiterId) {

        List<Deal> dealList = dealMapper.findAllActiveByRecruiterId(recruiterId);
        return dealList;
    }

    /**
     * Lists all active deals for exact Employer
     * @param employerId    Id of employer
     * @return List of POJO Deal instances
     */
    public List<Deal> findAllActiveByEmployerId(final Long employerId) {

        List<Deal> dealList = dealMapper.findAllActiveByEmployerId(employerId);
        return dealList;
    }

    public DealMapper getDealMapper() {
        return dealMapper;
    }

    public void setDealMapper(final DealMapper dealMapper) {
        this.dealMapper = dealMapper;
    }
}
