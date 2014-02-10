package com.recruiters.repository.specification;

/**
 * Generic Specification interface
 */
public interface ISpecification<Entity> {
    public Boolean isSatisfiedBy(final Entity candidate);
    public String asSql();
    public ISpecification or(final ISpecification other);
    public ISpecification and(final ISpecification other);
}
