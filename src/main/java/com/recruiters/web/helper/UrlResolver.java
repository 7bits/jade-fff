package com.recruiters.web.helper;

import com.recruiters.web.utils.LocaleUrlFilter;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 *  Url helper for templates
 */
public class UrlResolver {

    private String protocol = null;
    private String server = null;
    private String port = null;
    private String applicationName = null;
    private HttpServletRequest request = null;


    public UrlResolver(final String protocol,
                       final String server,
                       final String port,
                       final String applicationName,
                       final HttpServletRequest request
    ) {
        this.protocol = protocol;
        this.server = server;
        this.port = port;
        this.applicationName = applicationName;
        this.request = request;
    }

    /**
     * Provide full application url with locale
     * @return url of our app with locale
     */
    public String getAbsoluteUrl() {
        String fullPath = getApplicationUrl();
        String countryCode = (String) request.getAttribute(LocaleUrlFilter.getCountryCodeAttributeName());
        String langCode = (String) request.getAttribute(LocaleUrlFilter.getLanguageCodeAttributeName());
        fullPath += countryCode;
        if (langCode != null) {
            fullPath += "/" + langCode;
        }
        return fullPath;
    }

    /**
     * Provide resources url
     * @return resources url
     */
    public String getResourceUrl() {
        String fullPath = getApplicationUrl();
        fullPath += "resources";
        return fullPath;
    }

    /**
     * Return url for language switch
     * @return full url of the same page with locale in url changed
     */
    public String getLangChangeUrl() {
        String countryCode = (String) request.getAttribute(LocaleUrlFilter.getCountryCodeAttributeName());
        //String langCode = (String) request.getAttribute(LocaleUrlFilter.getLanguageCodeAttributeName());
        String changeLocale;
        if (countryCode.equals("us")) {
            changeLocale = getApplicationUrl() + "ru/" + request.getServletPath();
        } else {
            changeLocale = getApplicationUrl() + "us/en" + request.getServletPath();
        }

        return changeLocale;
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
        fullPath += "/";
        return fullPath;
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
}
