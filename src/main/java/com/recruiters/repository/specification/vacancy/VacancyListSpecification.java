package com.recruiters.repository.specification.vacancy;

import com.recruiters.model.Vacancy;
import com.recruiters.repository.specification.IListSpecification;
import com.recruiters.repository.specification.IOrderSpecification;
import com.recruiters.repository.specification.ISpecification;
import com.recruiters.repository.specification.vacancy.order.VacancyListOrderSpecification;

/**
 * Specification for Vacancy List
 */
public class VacancyListSpecification implements IListSpecification<Vacancy> {
    private VacancySpecification vacancySpecification;
    private VacancyListOrderSpecification vacancyListOrderSpecification;
    /** Default string size, used for string builder */
    private static final Integer DEFAULT_STRING_SIZE = 2048;

    public VacancyListSpecification(
            final VacancySpecification vacancySpecification,
            final VacancyListOrderSpecification vacancyListOrderSpecification
    ) {
        this.vacancySpecification = vacancySpecification;
        this.vacancyListOrderSpecification = vacancyListOrderSpecification;
    }

    @Override
    public ISpecification<Vacancy> getEntitySpecification() {
        return vacancySpecification;
    }

    @Override
    public IOrderSpecification getOrderSpecification() {
        return vacancyListOrderSpecification;
    }

    @Override
    public String asSql() {
        StringBuilder sqlQuery = new StringBuilder(DEFAULT_STRING_SIZE);
        sqlQuery.append("SELECT * FROM ( " +
                "SELECT vacancies.*, users.firstname, users.lastname, " +
                "bids.id as bid_id, deals.id as deal_id " +
                "FROM vacancies " +
                "INNER JOIN employers ON employers.id = vacancies.employer_id " +
                "INNER JOIN users  ON employers.user_id=users.id " +
                "LEFT JOIN bids ON bids.vacancy_id=vacancies.id AND bids.recruiter_id=#{recruiterId} " +
                "LEFT JOIN deals ON deals.vacancy_id=vacancies.id AND deals.recruiter_id=#{recruiterId} " +
                "WHERE vacancies.status NOT LIKE 'ARCHIVED' AND DATE(vacancies.creation_date)=#{date} " +
                "AND NOT EXISTS " +
                "(SELECT * FROM deals WHERE vacancy_id=vacancies.id AND recruiter_id!=#{recruiterId}) " +
                ") as all_vacancies ");
        if (vacancySpecification == null) {
            sqlQuery.append(" WHERE 0 ");
        } else if (vacancyListOrderSpecification == null) {
            sqlQuery.append(" WHERE ");
            sqlQuery.append(vacancySpecification.asSql());
        } else {
            sqlQuery.append(" WHERE ");
            sqlQuery.append(vacancySpecification.asSql());
            sqlQuery.append(vacancyListOrderSpecification.asSql());
        }
        return sqlQuery.toString();
    }
}
