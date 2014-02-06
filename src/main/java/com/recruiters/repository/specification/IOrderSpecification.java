package com.recruiters.repository.specification;

/**
 * Order specification interface
 */
public interface IOrderSpecification {
    public Boolean isAsc();
    public String asSql();
}
