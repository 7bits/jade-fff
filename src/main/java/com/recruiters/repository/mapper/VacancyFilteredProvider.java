package com.recruiters.repository.mapper;

import com.recruiters.repository.specification.impl.vacancy.VacancyListSpecification;

import java.util.Map;

/**
 * Create SQL query for Filtered List of Vacancies
 */
public class VacancyFilteredProvider {

    /**
     * Creating SQL query for getting specific list of vacancies
     * @param params    Parameters prepared by MyBatis
     * @return sql query
     */
    public static String selectVacancyFiltered(final Map<String, Object> params) {
        Object specification =  params.get("vacancyListSpecification");
        if (specification instanceof VacancyListSpecification) {
            VacancyListSpecification vacancyListSpecification = (VacancyListSpecification) specification;
            return vacancyListSpecification.asSql();
        }
        return "";
    }
}


