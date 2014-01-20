package com.recruiters.web.helper;

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
    private Locale locale = null;

    public UrlResolver(final String protocol,
                       final String server,
                       final String port,
                       final String applicationName,
                       final Locale locale
    ) {
        this.protocol = protocol;
        this.server = server;
        this.port = port;
        this.applicationName = applicationName;
        this.locale = locale;
    }

    /**
     * Provide full application url
     * @return url of our app
     */
    public String getAbsoluteUrl() {
        String fullPath = protocol + "://" + server;
        if (!StringUtils.isBlank(port)) {
            fullPath += ":" + port;
        }
        if (!StringUtils.isBlank(applicationName)) {
            fullPath += "/" + applicationName;
        }
        fullPath += "/" + locale;
        return fullPath;
    }

    /**
     * Provide full application url
     * @return url of our app
     */
    public String getResourceUrl() {
        String fullPath = protocol + "://" + server;
        if (!StringUtils.isBlank(port)) {
            fullPath += ":" + port;
        }
        if (!StringUtils.isBlank(applicationName)) {
            fullPath += "/" + applicationName;
        }
        fullPath += "/resources";
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
