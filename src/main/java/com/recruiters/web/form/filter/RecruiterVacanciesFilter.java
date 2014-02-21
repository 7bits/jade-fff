package com.recruiters.web.form.filter;

import com.recruiters.repository.specification.impl.EmptySpecification;
import com.recruiters.repository.specification.ISpecification;
import com.recruiters.repository.specification.impl.vacancy.BiddedVacancySpecification;
import com.recruiters.repository.specification.impl.vacancy.ContractVacancySpecification;
import com.recruiters.repository.specification.impl.vacancy.NewVacancySpecification;
import com.recruiters.repository.specification.impl.vacancy.VacancyRecruiterListSpecification;
import com.recruiters.repository.specification.impl.vacancy.VacancyTextSpecification;
import com.recruiters.repository.specification.impl.OrderByParam;
import com.recruiters.repository.specification.impl.vacancy.VacancyOrderByType;


/**
 * Vacancy Search Filter Form
 */
public class RecruiterVacanciesFilter {
    private String searchText = "";
    private Boolean hideVacancies = false;
    private Boolean hideBids = false;
    private Boolean hideDeals = false;
    private String date = "";
    private String sortingOrder;
    private String sortColumn;
    private Boolean sortAsc;

    public RecruiterVacanciesFilter() {
    }

    /**
     * Specification builder
     * @return Vacancy Specification
     */
    public VacancyRecruiterListSpecification getListSpecifications() {
        ISpecification vacancySpecification = new EmptySpecification();

        if (!hideVacancies) {
            vacancySpecification = vacancySpecification.or(new NewVacancySpecification());
        }
        if (!hideBids) {
            vacancySpecification = vacancySpecification.or(new BiddedVacancySpecification());
        }
        if (!hideDeals) {
            vacancySpecification = vacancySpecification.or(new ContractVacancySpecification());
        }

        if(!searchText.isEmpty()) {
            vacancySpecification = vacancySpecification.and(new VacancyTextSpecification(searchText));
        }

        if (sortColumn == null || sortAsc == null) {
            return new VacancyRecruiterListSpecification(vacancySpecification, null);
        }

        if (sortColumn.equals("title") || sortColumn.equals("description") || sortColumn.equals("creation_date")) {
            return new VacancyRecruiterListSpecification(vacancySpecification, new OrderByParam(sortColumn, sortAsc));
        }
        if (sortColumn.equals("type")) {
            return new VacancyRecruiterListSpecification(vacancySpecification, new VacancyOrderByType(sortAsc));
        }
        return new VacancyRecruiterListSpecification(vacancySpecification, null);
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(final String searchText) {
        this.searchText = searchText;
    }

    public Boolean getHideVacancies() {
        return hideVacancies;
    }

    public void setHideVacancies(final Boolean hideVacancies) {
        this.hideVacancies = hideVacancies;
    }

    public Boolean getHideBids() {
        return hideBids;
    }

    public void setHideBids(final Boolean hideBids) {
        this.hideBids = hideBids;
    }

    public Boolean getHideDeals() {
        return hideDeals;
    }

    public void setHideDeals(final Boolean hideDeals) {
        this.hideDeals = hideDeals;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getSortingOrder() {
        return sortingOrder;
    }

    public void setSortingOrder(final String sortingOrder) {
        this.sortingOrder = sortingOrder;
    }

    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(final String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public Boolean getSortAsc() {
        return sortAsc;
    }

    public void setSortAsc(final Boolean sortAsc) {
        this.sortAsc = sortAsc;
    }
}
