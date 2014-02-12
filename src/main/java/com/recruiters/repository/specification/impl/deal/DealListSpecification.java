package com.recruiters.repository.specification.impl.deal;

import com.recruiters.model.Vacancy;
import com.recruiters.repository.specification.IListSpecification;
import com.recruiters.repository.specification.IOrderSpecification;
import com.recruiters.repository.specification.ISpecification;

/**
 * Specification for Deal List
 */
public class DealListSpecification implements IListSpecification<Vacancy> {
    private ISpecification dealSpecification;
    private IOrderSpecification listOrderSpecification;
    /** Default string size, used for string builder */
    private static final Integer DEFAULT_STRING_SIZE = 2048;

    public DealListSpecification(
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
        sqlQuery.append("SELECT * FROM (SELECT deal.id, deal.status, deal.bid_id, " +
                "vacancy.id as vacancy_id,  vacancy.employer_id, vacancy.title, " +
                "vacancy.description, vacancy.salary_from, vacancy.salary_to, " +
                "vacancy.creation_date, " +
                "recruiter.id as recruiter_id, " +
                "user.firstname, user.lastname, " +
                "b1.message, COUNT(b2.id) as bids " +
                "FROM deal " +
                "INNER JOIN vacancy ON vacancy.id=deal.vacancy_id " +
                "INNER JOIN recruiter  ON recruiter.id=deal.recruiter_id " +
                "INNER JOIN user ON recruiter.user_id=user.id " +
                "INNER JOIN bid b1 ON b1.id=deal.bid_id " +
                "LEFT JOIN bid b2 ON b2.vacancy_id=deal.vacancy_id " +
                "WHERE vacancy.employer_id=#{employerId} " +
                "GROUP BY b2.vacancy_id)" +
                " as deals ");
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
