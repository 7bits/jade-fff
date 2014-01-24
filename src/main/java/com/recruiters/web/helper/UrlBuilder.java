package com.recruiters.web.helper;

import com.recruiters.web.controller.utils.DateTimeUtils;
import com.recruiters.web.form.VacanciesFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * Url Builder for Controller layer
 */
public class UrlBuilder {
    private HttpServletRequest request;
    private String localeUri;

    public UrlBuilder(final HttpServletRequest request, final String localeUri) {
        this.request = request;
        this.localeUri = localeUri;
    }

    /**
     * Generate full url for recruiter filter request
     * @param vacanciesFilter    Vacancies filter object
     * @return full url with params
     */
    public String recruiterFilterUrl(final VacanciesFilter vacanciesFilter) {
        DateTimeUtils dateTimeUtils = new DateTimeUtils();
        String link = getAbsoluteUrl() + "/" + localeUri + "/recruiter-find-new-vacancies?";
        link += "?searchText=";
        link += vacanciesFilter.getSearchText() == null ? "" : vacanciesFilter.getSearchText();
        link += "&showVacancies=";
        link += vacanciesFilter.getShowVacancies() == null ? "" : vacanciesFilter.getShowVacancies();
        link += "&showBids=";
        link += vacanciesFilter.getShowBids() == null ? "" : vacanciesFilter.getShowBids();
        link += "&showDeals=";
        link += vacanciesFilter.getShowDeals() == null ? "" : vacanciesFilter.getShowDeals();
        link += "&date=" + vacanciesFilter.getDate();

        return link;
    }

    /**
     * Build application url
     * @return application url
     */
    private String getAbsoluteUrl() {
        return request.getScheme() + "://" +
                request.getServerName() + ":" +
                request.getServerPort() +
                request.getContextPath();
    }

}
