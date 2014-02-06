package com.recruiters.repository.mapper;

import com.recruiters.repository.specification.vacancy.VacancyListSpecification;
import com.recruiters.repository.specification.vacancy.VacancySpecification;

import java.util.Map;

/**
 * Create SQL query for Filtered List of Vacancies
 */
public class VacancyFilteredProvider {

    /** Default string size, used for string builder */
    private static final Integer DEFAULT_STRING_SIZE = 2048;

    /**
     * Creating SQL query for getting specific list of vacancies
     * @param params    Parameters prepared by MyBatis
     * @return sql query
     */
    public static String selectVacancyFiltered(final Map<String, Object> params) {
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
                "(SELECT * FROM deals WHERE vacancy_id=vacancies.id AND recruiter_id!=#{recruiterId}) ");
        Object text = params.get("searchLikeText");
        if (text instanceof String) {
            String searchLikeText = (String) text;
            if (!searchLikeText.isEmpty()) {
                sqlQuery.append("AND (vacancies.title LIKE #{searchLikeText} " +
                        "OR vacancies.description LIKE #{searchLikeText})  ");
            }
        }
        sqlQuery.append(") as all_vacancies ");
        Object specification =  params.get("vacancySpecification");
        if (specification instanceof VacancyListSpecification) {
            VacancyListSpecification vacancyListSpecification = (VacancyListSpecification) specification;
            sqlQuery.append(vacancyListSpecification.asSql());
        } else {
            // TODO think how to solve inconsistency between SQL and Code language
            sqlQuery.append(" WHERE 0 ");
        }
        return sqlQuery.toString();
    }
}
