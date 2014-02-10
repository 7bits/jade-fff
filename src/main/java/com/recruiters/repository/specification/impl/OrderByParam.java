package com.recruiters.repository.specification.impl;

import com.recruiters.repository.specification.IOrderSpecification;

/**
 * List specification: ordered by parameter
 */
public class OrderByParam implements IOrderSpecification {
    /** StringBuilder buffer size */
    private static final Integer STRING_BUFFER_SIZE = 1024;
    private String param;
    private Boolean isAsc;

    public OrderByParam(final String param, final Boolean isAsc) {
        this.param = param;
        this.isAsc = isAsc;
    }

    @Override
    public Boolean isAsc() {
        return isAsc;
    }

    @Override
    public String asSql() {
        StringBuilder sb = new StringBuilder(STRING_BUFFER_SIZE);
        sb.append(" ORDER BY ");
        sb.append(param);
        if (isAsc()) {
            sb.append(" ASC ");
        } else {
            sb.append(" DESC ");
        }
        return sb.toString();
    }
}
