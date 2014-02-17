package com.recruiters.repository.specification.impl.vacancy;

import com.recruiters.model.Vacancy;
import com.recruiters.repository.specification.IListSpecification;
import com.recruiters.repository.specification.IOrderSpecification;
import com.recruiters.repository.specification.ISpecification;

/**
 * Specification for Vacancy List used by Employer
 */
public class VacancyEmployerListSpecification implements IListSpecification<Vacancy> {
    private ISpecification vacancySpecification;
    private IOrderSpecification listOrderSpecification;
    /** Default string size, used for string builder */
    private static final Integer DEFAULT_STRING_SIZE = 2048;

    public VacancyEmployerListSpecification(
            final ISpecification vacancySpecification,
            final IOrderSpecification listOrderSpecification
    ) {
        this.vacancySpecification = vacancySpecification;
        this.listOrderSpecification = listOrderSpecification;
    }

    @Override
    public ISpecification getEntitySpecification() {
        return vacancySpecification;
    }

    @Override
    public IOrderSpecification getOrderSpecification() {
        return listOrderSpecification;
    }

    @Override
    public String asSql() {
        StringBuilder sqlQuery = new StringBuilder(DEFAULT_STRING_SIZE);
        sqlQuery.append("SELECT * FROM (SELECT vacancy.id, vacancy.title, " +
                "vacancy.description, vacancy.creation_date, vacancy.status, " +
                "count(bid.id) as all_bids, " +
                "(SELECT COUNT(id) FROM bid " +
                "WHERE vacancy_id=vacancy.id AND viewed=0) as unseen_bids, " +
                "(SELECT COUNT(id) FROM bid " +
                "WHERE vacancy_id=vacancy.id AND viewed=1) as viewed_bids, " +
                "(SELECT COUNT(id) FROM bid " +
                "WHERE vacancy_id=vacancy.id AND status=\"REJECTED\") as rejected_bids, " +
                "max_updated.max_updated_date " +
                "FROM vacancy " +
                "LEFT JOIN bid ON bid.vacancy_id=vacancy.id " +
                "LEFT JOIN deal ON deal.vacancy_id=vacancy.id " +
                "INNER JOIN " +
                "(SELECT id, MAX(updated_date) as max_updated_date FROM " +
                "(SELECT id,updated_date FROM vacancy WHERE employer_id=#{employerId} " +
                "UNION ALL " +
                "SELECT bid.vacancy_id,MAX(bid.updated_date) FROM bid " +
                "INNER JOIN vacancy ON vacancy.id=bid.vacancy_id " +
                "WHERE vacancy.employer_id=#{employerId} " +
                "GROUP BY (bid.vacancy_id) " +
                ")as updated_dates GROUP BY id) as max_updated " +
                "ON max_updated.id=vacancy.id " +
                "WHERE vacancy.employer_id=#{employerId} " +
                "AND vacancy.status != \"ARCHIVED\" " +
                "AND deal.id IS NULL " +
                "GROUP BY vacancy.id) as all_vacancies ");
        if (listOrderSpecification == null) {
            sqlQuery.append(" WHERE ");
            sqlQuery.append(vacancySpecification.asSql());
        } else {
            sqlQuery.append(" WHERE ");
            sqlQuery.append(vacancySpecification.asSql());
            sqlQuery.append(listOrderSpecification.asSql());
        }
        return sqlQuery.toString();
    }
}
