package com.recruiters.repository.specification;

/**
 * Created by fairdev on 29.01.14.
 */
interface ISpecification<Entity> {
    Boolean isSatisfiedBy(final Entity candidate);
    String asSql();
    ISpecification<Entity> or(final ISpecification<Entity> other);
}
