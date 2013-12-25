package com.recruiters.web.controller.iterceptor;

import com.recruiters.service.utils.TemplateService;
import com.recruiters.service.utils.SecurityService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class HandlerInterceptor extends HandlerInterceptorAdapter {

    /** domain name variable for send to view templates: consist from protocol, server_name, port and application name */
    static final String DOMAIN_NAME_VARIABLE = "domainName";
    /** role name variable for send to view templates */
    static final String USER_ROLE_VARIABLE = "userRole";
    /** Type of object in ModelAndView which contains BindingResults */
    static final String TYPE_WITH_BINDING_RESULT = "org.springframework.validation.BindingResult";
    /** Object name which will contains form errors if any in model */
    static final String MODEL_ERRORS_NAME = "errors";

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
                DOMAIN_NAME_VARIABLE,
                TemplateService.getFullUrl(
                        protocol,
                        server,
                        port,
                        applicationName
                )
        );
        mav.addObject(USER_ROLE_VARIABLE, SecurityService.getUserRole(request));

        // Resolving if ModelAndView have any form data, getting errors from it (if any)
        // and adding them to HashMap with pre-configured name into ModelAndView
        Map<String, Object> modelMap = mav.getModelMap();
        Map<String, String> errors = new HashMap<String, String>();
        for (Map.Entry<String, Object> entry : modelMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key.contains(TYPE_WITH_BINDING_RESULT)) {
                BindingResult bindingResult = (BindingResult) value;
                if (bindingResult.getFieldErrors().size() != 0) {
                    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
                    for (FieldError error : fieldErrors) {
                        errors.put(error.getField(), error.getDefaultMessage());
                    }
                }
            }
        }
        // Should be outside ModelMap iterating otherwise we will get exception
        if (errors.size() != 0) {
            mav.addObject(MODEL_ERRORS_NAME, errors);
        }
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
