package com.recruiters.web.form;

/**
 * Vacancy Search Filter Form
 */
public class VacanciesFilter {
    private String searchText = "";
    private Boolean showVacancies = false;
    private Boolean showBids = false;
    private Boolean showDeals = false;
    private String date = "";
    private String submit = null;

    public VacanciesFilter() {
    }

    /**
     * Copy constructor
     * @param vacanciesFilter    VacanciesFilter instance
     */
    public VacanciesFilter(final VacanciesFilter vacanciesFilter) {
        searchText = vacanciesFilter.getSearchText();
        showVacancies = vacanciesFilter.getShowVacancies();
        showBids = vacanciesFilter.getShowBids();
        showDeals = vacanciesFilter.getShowDeals();
        date = vacanciesFilter.getDate();
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(final String searchText) {
        this.searchText = searchText;
    }

    public Boolean getShowVacancies() {
        return showVacancies;
    }

    public void setShowVacancies(final Boolean showVacancies) {
        this.showVacancies = showVacancies;
    }

    public Boolean getShowBids() {
        return showBids;
    }

    public void setShowBids(final Boolean showBids) {
        this.showBids = showBids;
    }

    public Boolean getShowDeals() {
        return showDeals;
    }

    public void setShowDeals(final Boolean showDeals) {
        this.showDeals = showDeals;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getSubmit() {
        return submit;
    }

    public void setSubmit(final String submit) {
        this.submit = submit;
    }
}
