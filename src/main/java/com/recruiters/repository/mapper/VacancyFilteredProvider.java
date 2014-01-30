package com.recruiters.repository.mapper;

import com.recruiters.repository.specification.VacancySpecification;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * Created by fairdev on 29.01.14.
 */
public class VacancyFilteredProvider {
    public static String selectVacancyFiltered(Map params) {
        String sqlQuery = "SELECT * FROM ( " +
                "SELECT vacancies.*, users.firstname, users.lastname, " +
                "bids.id as bid_id, deals.id as deal_id " +
                "FROM vacancies " +
                "INNER JOIN employers ON employers.id = vacancies.employer_id " +
                "INNER JOIN users  ON employers.user_id=users.id " +
                "LEFT JOIN bids ON bids.vacancy_id=vacancies.id AND bids.recruiter_id=#{recruiterId} " +
                "LEFT JOIN deals ON deals.vacancy_id=vacancies.id AND deals.recruiter_id=#{recruiterId} " +
                "WHERE vacancies.status NOT LIKE 'ARCHIVED' AND DATE(vacancies.creation_date)=#{date} " +
                "AND NOT EXISTS " +
                "(SELECT * FROM deals WHERE vacancy_id=vacancies.id AND recruiter_id!=#{recruiterId}) ";
        Object text = params.get("searchLikeText");
        if (text instanceof String) {
        String searchLikeText = (String) text;
        if (!searchLikeText.isEmpty()) {
            sqlQuery += "AND (vacancies.title LIKE #{searchLikeText} " +
                    "OR vacancies.description LIKE #{searchLikeText})  ";
        }
        }
        sqlQuery += ") " +
                "as all_vacancies ";
        Object specification =  params.get("vacancySpecification");
        if (specification instanceof VacancySpecification) {
            VacancySpecification vacancySpecification = (VacancySpecification) specification;
            sqlQuery += " WHERE " + vacancySpecification.asSql();
        }
        return sqlQuery;
    }
}
