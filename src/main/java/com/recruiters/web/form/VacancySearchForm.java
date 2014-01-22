package com.recruiters.web.form;

/**
 * Object for Vacancy Search action
 */
public class VacancySearchForm {
    private String search = null;
    private Boolean showVacancies = false;
    private Boolean showBids = false;
    private Boolean showDeals = false;

    public VacancySearchForm() {
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(final String search) {
        this.search = search;
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
}
