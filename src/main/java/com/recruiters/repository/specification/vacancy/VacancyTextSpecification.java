package com.recruiters.repository.specification.vacancy;

import com.recruiters.model.Vacancy;
import com.recruiters.repository.specification.ISpecification;

/**
 * Specification for Vacancy containing text
 */
public class VacancyTextSpecification extends VacancySpecification {
    private String containsText;
    public VacancyTextSpecification(final String containsText) {
        this.containsText = containsText;
    }

    @Override
    public Boolean isSatisfiedBy(final Vacancy candidate) {
        if (candidate.getTitle().contains(containsText) ||
                candidate.getDescription().contains(containsText)) {
            return true;
        }
        return false;
    }

    @Override
    public String asSql() {
        // TODO I don't like we are not using containsText, but otherwise we should do escaping job for MyBatis
        // TODO and it will not be a part of prepared statement any more
        return " title LIKE #{searchLikeText} " +
        " OR description LIKE #{searchLikeText} ";
    }
}