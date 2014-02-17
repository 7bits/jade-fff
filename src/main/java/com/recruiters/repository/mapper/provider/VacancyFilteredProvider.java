package com.recruiters.repository.mapper.provider;

import com.recruiters.repository.specification.impl.vacancy.VacancyEmployerListSpecification;
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

    /**
     * Creating SQL query for getting specific list of vacancies for employer
     * @param params    Parameters prepared by MyBatis
     * @return sql query
     */
    public static String selectVacancyEmployerFiltered(final Map<String, Object> params) {
        Object specification =  params.get("vacancyListSpecification");
        if (specification instanceof VacancyEmployerListSpecification) {
            VacancyEmployerListSpecification vacancyListSpecification = (VacancyEmployerListSpecification) specification;
            return vacancyListSpecification.asSql();
        }
        return "";
    }
}


