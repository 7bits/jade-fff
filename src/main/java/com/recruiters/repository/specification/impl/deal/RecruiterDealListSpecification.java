package com.recruiters.repository.specification.impl.deal;

import com.recruiters.model.Deal;
import com.recruiters.repository.specification.IListSpecification;
import com.recruiters.repository.specification.IOrderSpecification;
import com.recruiters.repository.specification.ISpecification;

/**
 * Specification for Deal List for Recruiter
 */
public class RecruiterDealListSpecification implements IListSpecification<Deal> {
    private ISpecification dealSpecification;
    private IOrderSpecification listOrderSpecification;
    /** Default string size, used for string builder */
    private static final Integer DEFAULT_STRING_SIZE = 2048;

    public RecruiterDealListSpecification(
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
        sqlQuery.append("SELECT * FROM " +
                "(SELECT deal.id, deal.status, deal.creation_date, " +
                "vacancy.id as vacancy_id,  vacancy.employer_id, vacancy.title, " +
                "vacancy.description, vacancy.salary_from, vacancy.salary_to, " +
                "recruiter.id as recruiter_id, " +
                "user.firstname, user.lastname, " +
                "(SELECT COUNT(applicant.id) FROM applicant " +
                "WHERE deal_id=deal.id) as all_applicants, " +
                "(SELECT COUNT(applicant.id) FROM applicant " +
                "WHERE deal_id=deal.id AND viewed=1) as viewed_applicants, " +
                "(SELECT COUNT(applicant.id) FROM applicant " +
                "WHERE deal_id=deal.id AND viewed=0) as unseen_applicants, " +
                "(SELECT COUNT(applicant.id) FROM applicant " +
                "WHERE deal_id=deal.id AND status=\"REJECTED\") as rejected_applicants, " +
                "max_updated.max_updated_date " +
                "FROM deal " +
                "INNER JOIN vacancy ON vacancy.id=deal.vacancy_id " +
                "INNER JOIN recruiter  ON recruiter.id=deal.recruiter_id " +
                "INNER JOIN user ON recruiter.user_id=user.id " +
                "INNER JOIN (SELECT id, MAX(updated_date) as max_updated_date FROM " +
                "(SELECT id,updated_date FROM deal WHERE recruiter_id=#{recruiterId} " +
                "UNION ALL " +
                "SELECT deal.id, vacancy.updated_date FROM vacancy " +
                "INNER JOIN deal ON deal.vacancy_id=vacancy.id " +
                "WHERE deal.recruiter_id=#{recruiterId} " +
                "UNION ALL " +
                "SELECT deal.id, MAX(applicant.updated_date)  FROM applicant " +
                "INNER JOIN deal ON applicant.deal_id=deal.id " +
                "WHERE deal.recruiter_id=#{recruiterId})as updated_dates GROUP BY id) as max_updated " +
                "ON max_updated.id=deal.id " +
                "WHERE recruiter.id=#{recruiterId} AND " +
                "deal.recruiter_archived = 0)" +
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
