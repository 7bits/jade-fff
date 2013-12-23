package com.recruiters.service.Utils;

import org.apache.commons.lang3.StringUtils;

/**
 *  service for provide some function for jade templates
 */
public final class TemplateService {

    private TemplateService() {}

    public static String getFullUrl(
            final String protocol,
            final String server,
            final String port,
            final String applicationName
    ) {
        String fullPath = protocol + "://" + server;
        if (!StringUtils.isBlank(port)) {
            fullPath += ":" + port;
        }
        if (!StringUtils.isBlank(applicationName)) {
            fullPath += "/" + applicationName;
        }
        return fullPath;
    }

}
