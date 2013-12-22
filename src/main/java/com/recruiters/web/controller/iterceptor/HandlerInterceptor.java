package com.recruiters.web.controller.iterceptor;

import com.recruiters.service.UtilsService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 */
public class HandlerInterceptor extends HandlerInterceptorAdapter {

    private String protocol = null;
    private String server = null;
    private String port = null;
    private String applicationName = null;

    @Override
    public void postHandle(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Object handler,
            final ModelAndView mav
    ) {
        mav.addObject(
                "domainName", UtilsService.getFullUrl(
                        protocol,
                        server,
                        port,
                        applicationName
                )
        );
        mav.addObject("userRole", UtilsService.getUserRole(request));
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
