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
                "SELECT vacancy.*, user.firstname, user.lastname, " +
                "bid.id as bid_id, deal.id as deal_id " +
                "FROM vacancy " +
                "INNER JOIN employer ON employer.id = vacancy.employer_id " +
                "INNER JOIN user  ON employer.user_id=user.id " +
                "LEFT JOIN bid ON bid.vacancy_id=vacancy.id AND bid.recruiter_id=#{recruiterId} " +
                "LEFT JOIN deal ON deal.vacancy_id=vacancy.id AND deal.recruiter_id=#{recruiterId} " +
                "WHERE vacancy.status NOT LIKE 'ARCHIVED' AND DATE(vacancy.creation_date)=#{date} " +
                "AND NOT EXISTS " +
                "(SELECT * FROM deal WHERE vacancy_id=vacancy.id AND recruiter_id!=#{recruiterId}) " +
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
