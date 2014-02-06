package com.recruiters.repository.specification;

/**
* Generic List specification interface
*/
public interface IListSpecification<Entity> {
    public ISpecification<Entity> getEntitySpecification();
    public IOrderSpecification getOrderSpecification();
    public String asSql();
}
