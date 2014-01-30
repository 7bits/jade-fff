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
    /** Default string size, used for string builder */
    private static final Integer DEFAULT_STRING_SIZE = 255;


    public UrlResolver() {
    }

    /**
     * Provide resources url
     * @param uri    Uri of resource
     * @return resources url
     */
    public String getResourceUrl(final String uri) {
        StringBuilder resourceUrl = new StringBuilder(DEFAULT_STRING_SIZE);
        resourceUrl.append(getApplicationUrl());
        resourceUrl.append("/resources");
        if (uri != null) {
            if (!uri.startsWith("/")) {
                resourceUrl.append("/");
            }
        }
        resourceUrl.append(uri);
        return resourceUrl.toString();
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
        StringBuilder redirectUri = new StringBuilder(DEFAULT_STRING_SIZE);
        redirectUri.append("redirect:");
        redirectUri.append(makeLocaleUrlPart(locale));
        if (uri != null) {
            if (!uri.startsWith("/")) {
                redirectUri.append("/");
            }
        }
        redirectUri.append(uri);
        return redirectUri.toString();
    }


    /**
     * Build uri for redirect with one parameter
     * @param uri       Page uri
     * @param locale    Locale
     * @param param     Long type parameter
     * @return complete uri with redirect:
     */
    public String buildRedirectUri(final String uri, final Long param, final Locale locale) {
        StringBuilder redirectUri = new StringBuilder(DEFAULT_STRING_SIZE);
        redirectUri.append("redirect:");
        redirectUri.append(makeLocaleUrlPart(locale));
        if (uri != null) {
            if (!uri.startsWith("/")) {
                redirectUri.append("/");
            }
        }
        redirectUri.append(uri);
        redirectUri.append("/");
        redirectUri.append(param);
        return redirectUri.toString();
    }

    /**
     * Build uri suitable for web links (with application name in path)
     * @param uri       Page uri
     * @param locale    Locale
     * @return complete uri with redirect:
     */
    public String buildFullUri(final String uri, final Locale locale) {
        StringBuilder fullUri = new StringBuilder(DEFAULT_STRING_SIZE);
        fullUri.append("/");
        fullUri.append(applicationName);
        fullUri.append(makeLocaleUrlPart(locale));
        if (uri != null) {
            if (!uri.startsWith("/")) {
                fullUri.append("/");
            }
        }
        fullUri.append(uri);
        return fullUri.toString();
    }

    /**
     * Build uri suitable for web links (with application name in path)
     * @param uri       Page uri
     * @param param     Parameter of Long type
     * @param locale    Locale
     * @return complete uri with redirect:
     */
    public String buildFullUri(final String uri, final Long param, final Locale locale) {
        StringBuilder fullUri = new StringBuilder(DEFAULT_STRING_SIZE);
        fullUri.append("/");
        fullUri.append(applicationName);
        fullUri.append(makeLocaleUrlPart(locale));
        if (uri != null) {
            if (!uri.startsWith("/")) {
                fullUri.append("/");
            }
            fullUri.append(uri);
            if (!uri.endsWith("/")) {
                fullUri.append("/");
            }
        }
        fullUri.append(param);
        return fullUri.toString();
    }

    /**
     * Generate uri for recruiter filter request
     * @param vacanciesFilter    Vacancies filter object
     * @param locale             Locale
     * @return uri with params
     */
    public String buildRecruiterFilterUri(final VacanciesFilter vacanciesFilter, final Locale locale) {
        StringBuilder uri = new StringBuilder(DEFAULT_STRING_SIZE);
        uri.append("recruiter-find-new-vacancies");
        uri.append("?searchText=");
        uri.append(vacanciesFilter.getSearchText());
        uri.append("&showVacancies=");
        uri.append(vacanciesFilter.getShowVacancies());
        uri.append("&showBids=");
        uri.append(vacanciesFilter.getShowBids());
        uri.append("&showDeals=");
        uri.append(vacanciesFilter.getShowDeals());
        uri.append("&date=");
        uri.append(vacanciesFilter.getDate());
        uri.append("&submit=Filter");
        return buildFullUri(uri.toString(), locale);
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
    private StringBuilder getApplicationUrl() {
        StringBuilder url = new StringBuilder(DEFAULT_STRING_SIZE);
        url.append(protocol);
        url.append("://");
        url.append(server);
        if (!StringUtils.isBlank(port)) {
            url.append(":");
            url.append(port);
        }
        if (!StringUtils.isBlank(applicationName)) {
            url.append("/");
            url.append(applicationName);
        }
        return url;
    }

    private StringBuilder makeLocaleUrlPart(final Locale locale) {
        StringBuilder urlPart = new StringBuilder(DEFAULT_STRING_SIZE);
        if (locale != null) {
            String country = locale.getCountry().toLowerCase();
            String language = locale.getLanguage().toLowerCase();
            if (!language.isEmpty()) {
                urlPart.append("/");
                urlPart.append(language);
                if (!country.isEmpty()) {
                    urlPart.append("/");
                    urlPart.append(country);
                }
            }
        }
        return urlPart;
    }
}
