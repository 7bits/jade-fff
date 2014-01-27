package com.recruiters.web.helper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 *  Url helper for templates
 */
public class UrlResolver {

    private String protocol = null;
    private String server = null;
    private String port = null;
    private String applicationName = null;
    private Locale locale = null;
    private HttpServletRequest request = null;


    public UrlResolver(final String protocol,
                       final String server,
                       final String port,
                       final String applicationName,
                       final Locale locale,
                       final HttpServletRequest request
    ) {
        this.protocol = protocol;
        this.server = server;
        this.port = port;
        this.applicationName = applicationName;
        this.locale = locale;
        this.request = request;
    }

    /**
     * Provide full application url with locale
     * @return url of our app with locale
     */
    public String getAbsoluteUrl() {
        String fullPath = getApplicationUrl();
        fullPath += locale;
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
        String requestUri = request.getRequestURI();
            try {
                Integer localeStartIndex = requestUri.indexOf("/", 1) + 1;
                Integer localeEndIndex = requestUri.indexOf("/", localeStartIndex);
                String currentLocale = requestUri.substring(localeStartIndex, localeEndIndex);
                String changeLocale;
               if (currentLocale.equals("ru")) {
                   changeLocale = getApplicationUrl() + "en" + requestUri.substring(localeEndIndex);
               } else if (currentLocale.equals("en")) {
                   changeLocale = getApplicationUrl() + "ru" + requestUri.substring(localeEndIndex);
               } else {
                   changeLocale = getApplicationUrl() + "ru" + requestUri.substring(localeEndIndex);
               }

                return changeLocale;
            } catch (IndexOutOfBoundsException e) {

                return "#";
            }
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
