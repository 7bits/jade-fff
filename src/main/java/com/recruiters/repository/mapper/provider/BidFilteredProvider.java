package com.recruiters.repository.mapper.provider;

import com.recruiters.repository.specification.impl.bid.BidListSpecification;

import java.util.Map;

/**
 * Create SQL query for Filtered List of bids
 */
public class BidFilteredProvider {

    /**
     * Creating SQL query for getting specific list of bids
     * @param params    Parameters prepared by MyBatis
     * @return sql query
     */
    public static String selectBidsFiltered(final Map<String, Object> params) {
        Object specification =  params.get("bidListSpecification");
        if (specification instanceof BidListSpecification) {
            BidListSpecification bidListSpecification = (BidListSpecification) specification;
            return bidListSpecification.asSql();
        }
        return "";
    }
}


