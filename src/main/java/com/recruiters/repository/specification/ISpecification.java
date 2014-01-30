package com.recruiters.repository.specification;

/**
 * Generic Specification interface
 */
interface ISpecification<Entity> {
    Boolean isSatisfiedBy(final Entity candidate);
    String asSql();
    ISpecification<Entity> or(final ISpecification<Entity> other);
}
