package com.recruiters.repository.mapper.provider;

import com.recruiters.repository.specification.impl.deal.DealListSpecification;

import java.util.Map;

/**
 * Create SQL query for Filtered List of deals
 */
public class DealFilteredProvider {

    /**
     * Creating SQL query for getting specific list of deals
     * @param params    Parameters prepared by MyBatis
     * @return sql query
     */
    public static String selectDealsFiltered(final Map<String, Object> params) {
        Object specification =  params.get("dealListSpecification");
        if (specification instanceof DealListSpecification) {
            DealListSpecification dealListSpecification = (DealListSpecification) specification;
            return dealListSpecification.asSql();
        }
        return "";
    }
}


