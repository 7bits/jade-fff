package com.recruiters.repository.mapper.provider;

import com.recruiters.repository.specification.impl.deal.EmployerDealListSpecification;
import com.recruiters.repository.specification.impl.deal.RecruiterDealListSpecification;

import java.util.Map;

/**
 * Create SQL query for Filtered List of deals
 */
public class DealFilteredProvider {

    /**
     * Creating SQL query for getting specific list of deals for employer
     * @param params    Parameters prepared by MyBatis
     * @return sql query
     */
    public static String selectEmployerDealsFiltered(final Map<String, Object> params) {
        Object specification =  params.get("dealListSpecification");
        if (specification instanceof EmployerDealListSpecification) {
            EmployerDealListSpecification dealListSpecification = (EmployerDealListSpecification) specification;
            return dealListSpecification.asSql();
        }
        return "";
    }


    /**
     * Creating SQL query for getting specific list of deals for recruiter
     * @param params    Parameters prepared by MyBatis
     * @return sql query
     */
    public static String selectRecruiterDealsFiltered(final Map<String, Object> params) {
        Object specification =  params.get("dealListSpecification");
        if (specification instanceof RecruiterDealListSpecification) {
            RecruiterDealListSpecification dealListSpecification = (RecruiterDealListSpecification) specification;
            return dealListSpecification.asSql();
        }
        return "";
    }
}


