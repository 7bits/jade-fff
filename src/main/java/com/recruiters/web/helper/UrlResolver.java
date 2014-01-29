package com.recruiters.web.helper;

import com.recruiters.web.controller.utils.DateTimeUtils;
import com.recruiters.web.form.VacanciesFilter;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;


/**
 *  Url helper for templates
 */
public class UrlResolver {

    private String protocol = null;
    private String server = null;
    private String port = null;
    private String applicationName = null;


    public UrlResolver() {
    }

    /**
     * Provide full application url with locale
     * @return url of our app with locale
     */
    public String getAbsoluteUrl(final Locale locale) {
        String fullPath = getApplicationUrl();
        fullPath += makeLocaleUrlPart(locale);
        return fullPath;
    }

    /**
     * Provide resources url
     * @return resources url
     */
    public String getResourceUrl() {
        String fullPath = getApplicationUrl();
        fullPath += "/resources";
        return fullPath;
    }

    /**
     * Return url for language switch
     * @return full url of the same page with locale in url changed
     */
    public String getLangChangeUrl() {
//        String countryCode = (String) request.getAttribute(LocaleUrlFilter.getCountryCodeAttributeName());
//        //String langCode = (String) request.getAttribute(LocaleUrlFilter.getLanguageCodeAttributeName());
        String changeLocale;
//        if (countryCode.equals("us")) {
//            changeLocale = getApplicationUrl() + "ru/" + request.getServletPath();
//        } else {
//            changeLocale = getApplicationUrl() + "us/en" + request.getServletPath();
//        }

        changeLocale = "#";
        return changeLocale;
    }

    public String buildRedirectUri(final String uri, final Locale locale) {
        String fullPath = "redirect:";
        fullPath += makeLocaleUrlPart(locale);
        fullPath += "/" + uri;
        return fullPath;
    }

    public String buildRedirectUriLongParam(final String uri, final Long param, final Locale locale) {
        String fullPath = "redirect:";
        fullPath += makeLocaleUrlPart(locale);
        fullPath += "/" + uri + "/" + param;
        return fullPath;
    }

    public String buildFullUri(final String uri, final Locale locale) {
        String fullPath = "/" + applicationName;
        fullPath += makeLocaleUrlPart(locale);
        fullPath += "/" + uri;
        return fullPath;
    }

    /**
     * Generate uri for recruiter filter request
     * @param vacanciesFilter    Vacancies filter object
     * @param locale             Locale
     * @return uri with params
     */
    public String buildRecruiterFilterUri(final VacanciesFilter vacanciesFilter, final Locale locale) {
        String link = "recruiter-find-new-vacancies";
        link += "?searchText=";
        link += vacanciesFilter.getSearchText() == null ? "" : vacanciesFilter.getSearchText();
        link += "&showVacancies=";
        link += vacanciesFilter.getShowVacancies() == null ? "" : vacanciesFilter.getShowVacancies();
        link += "&showBids=";
        link += vacanciesFilter.getShowBids() == null ? "" : vacanciesFilter.getShowBids();
        link += "&showDeals=";
        link += vacanciesFilter.getShowDeals() == null ? "" : vacanciesFilter.getShowDeals();
        link += "&date=" + vacanciesFilter.getDate();

        return buildFullUri(link, locale);
    }


    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(final String protocol) {
        this.protocol = protocol;
    }

    public String getServer() {
        return server;
    }

    public void setServer(final String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    public void setPort(final String port) {
        this.port = port;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(final String applicationName) {
        this.applicationName = applicationName;
    }


    /**
     * Provide full application url
     * @return url of our app
     */
    private String getApplicationUrl() {
        String fullPath = protocol + "://" + server;
        if (!StringUtils.isBlank(port)) {
            fullPath += ":" + port;
        }
        if (!StringUtils.isBlank(applicationName)) {
            fullPath += "/" + applicationName;
        }
        return fullPath;
    }

    private String makeLocaleUrlPart(final Locale locale) {
        String fullPath = "";
        if (locale != null) {
            String country = locale.getCountry().toLowerCase();
            String language = locale.getLanguage().toLowerCase();
            if (!language.isEmpty()) {
                fullPath += "/" + language;
                if (!country.isEmpty()) {
                    fullPath += "/" + country;
                }
            }
        }
        return fullPath;
    }
}
