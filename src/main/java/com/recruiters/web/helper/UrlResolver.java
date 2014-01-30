package com.recruiters.web.helper;

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
     * Provide resources url
     * @param uri    Uri of resource
     * @return resources url
     */
    public String getResourceUrl(final String uri) {
        String fullPath = getApplicationUrl();
        fullPath += "/resources";
        if (uri != null) {
            if (!uri.startsWith("/")) {
                fullPath += "/";
            }
        }
        fullPath += uri;
        return fullPath;
    }

    /**
     * Return url for language switch
     * @param uri       Uri of current page without app name
     * @param locale    Current locale
     * @return full url of the same page with locale in url changed
     */
    public String getLangChangeUrl(final String uri, final Locale locale) {
        String changeLocale;
        if (locale.getLanguage().equals("en")) {
            changeLocale = buildFullUri(uri, new Locale("ru"));
        } else {
            changeLocale = buildFullUri(uri, new Locale("en", "us"));
        }
        return changeLocale;
    }

    /**
     * Build uri for redirect
     * @param uri       Page uri
     * @param locale    Locale
     * @return complete uri with redirect:
     */
    public String buildRedirectUri(final String uri, final Locale locale) {
        String fullPath = "redirect:";
        fullPath += makeLocaleUrlPart(locale);
        if (uri != null) {
            if (!uri.startsWith("/")) {
                fullPath += "/";
            }
        }
        fullPath += uri;
        return fullPath;
    }


    /**
     * Build uri for redirect with one parameter
     * @param uri       Page uri
     * @param locale    Locale
     * @param param     Long type parameter
     * @return complete uri with redirect:
     */
    public String buildRedirectUri(final String uri, final Long param, final Locale locale) {
        String fullPath = "redirect:";
        fullPath += makeLocaleUrlPart(locale);
        if (uri != null) {
            if (!uri.startsWith("/")) {
                fullPath += "/";
            }
        }
        fullPath += uri + "/" + param;
        return fullPath;
    }

    /**
     * Build uri suitable for web links (with application name in path)
     * @param uri       Page uri
     * @param locale    Locale
     * @return complete uri with redirect:
     */
    public String buildFullUri(final String uri, final Locale locale) {
        String fullPath = "/" + applicationName;
        fullPath += makeLocaleUrlPart(locale);
        if (uri != null) {
            if (!uri.startsWith("/")) {
                fullPath += "/";
            }
        }
        fullPath += uri;
        return fullPath;
    }

    /**
     * Build uri suitable for web links (with application name in path)
     * @param uri       Page uri
     * @param param     Parameter of Long type
     * @param locale    Locale
     * @return complete uri with redirect:
     */
    public String buildFullUri(final String uri, final Long param, final Locale locale) {
        String fullPath = "/" + applicationName;
        fullPath += makeLocaleUrlPart(locale);
        if (uri != null) {
            if (!uri.startsWith("/")) {
                fullPath += "/";
            }
            fullPath += uri;
            if (!uri.endsWith("/")) {
                fullPath += "/";
            }
        }
        fullPath += param;
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
        link += vacanciesFilter.getSearchText();
        link += "&showVacancies=";
        link += vacanciesFilter.getShowVacancies();
        link += "&showBids=";
        link += vacanciesFilter.getShowBids();
        link += "&showDeals=";
        link += vacanciesFilter.getShowDeals();
        link += "&date=" + vacanciesFilter.getDate();
        link += "&submit=Filter";
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
