package com.recruiters.service.utils;

import org.apache.commons.lang3.StringUtils;

/**
 *  service for provide some function for jade templates
 */
public class TemplateService {

    private String protocol = null;
    private String server = null;
    private String port = null;
    private String applicationName = null;

    public TemplateService(final String protocol, final String server, final String port, final String applicationName) {
        this.protocol = protocol;
        this.server = server;
        this.port = port;
        this.applicationName = applicationName;
    }

    public String getAbsoluteUrl() {
        String fullPath = protocol + "://" + server;
        if (!StringUtils.isBlank(port)) {
            fullPath += ":" + port;
        }
        if (!StringUtils.isBlank(applicationName)) {
            fullPath += "/" + applicationName;
        }
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
