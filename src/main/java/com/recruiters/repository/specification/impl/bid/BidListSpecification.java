package com.recruiters.repository.specification.impl.bid;

import com.recruiters.model.Bid;
import com.recruiters.repository.specification.IListSpecification;
import com.recruiters.repository.specification.IOrderSpecification;
import com.recruiters.repository.specification.ISpecification;

/**
 * Specification for Bid List
 */
public class BidListSpecification implements IListSpecification<Bid> {
    private ISpecification dealSpecification;
    private IOrderSpecification listOrderSpecification;
    /** Default string size, used for string builder */
    private static final Integer DEFAULT_STRING_SIZE = 2048;

    public BidListSpecification(
            final ISpecification vacancySpecification,
            final IOrderSpecification listOrderSpecification
    ) {
        this.dealSpecification = vacancySpecification;
        this.listOrderSpecification = listOrderSpecification;
    }

    @Override
    public ISpecification getEntitySpecification() {
        return dealSpecification;
    }

    @Override
    public IOrderSpecification getOrderSpecification() {
        return listOrderSpecification;
    }

    @Override
    public String asSql() {
        StringBuilder sqlQuery = new StringBuilder(DEFAULT_STRING_SIZE);
        sqlQuery.append("SELECT * FROM (" +
                "SELECT bid.id, bid.message, bid.status, bid.viewed, bid.creation_date, bid.updated_date, " +
                "vacancy.id as vacancy_id,  vacancy.employer_id, vacancy.title, " +
                "vacancy.description, vacancy.salary_from, vacancy.salary_to, " +
                "recruiter.id as recruiter_id, " +
                "user.firstname, user.lastname " +
                "FROM bid " +
                "INNER JOIN vacancy ON vacancy.id=bid.vacancy_id " +
                "INNER JOIN recruiter  ON recruiter.id=bid.recruiter_id " +
                "INNER JOIN employer ON employer.id=vacancy.employer_id " +
                "INNER JOIN user ON user.id=employer.user_id " +
                "WHERE bid.recruiter_id=#{recruiterId} AND recruiter_archived=0" +
                ")" +
                " as bids ");
        if (listOrderSpecification == null) {
            sqlQuery.append(" WHERE ");
            sqlQuery.append(dealSpecification.asSql());
        } else {
            sqlQuery.append(" WHERE ");
            sqlQuery.append(dealSpecification.asSql());
            sqlQuery.append(listOrderSpecification.asSql());
        }
        return sqlQuery.toString();
    }
}
