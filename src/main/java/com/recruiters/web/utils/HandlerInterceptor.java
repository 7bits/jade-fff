package com.recruiters.web.utils;

import com.recruiters.service.BusinessRulesService;
import com.recruiters.web.helper.UrlResolver;
import com.recruiters.web.helper.UserResolver;
import com.recruiters.web.helper.CsrfResolver;
import com.recruiters.web.helper.MessageResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Main interceptor is used to solve all issues we have with non-JSP templates
 * like absence of localisation, formatters, error checkers and other tools
 */
public class HandlerInterceptor extends HandlerInterceptorAdapter {

    /** domain name variable for send to view templates: consist from protocol, server_name, port and application name */
    static final String DOMAIN_NAME_VARIABLE = "domain";
    /** role name variable for send to view templates */
    static final String SECURITY_SERVICE_NAME = "security";
    /** Type of object in ModelAndView which contains BindingResults */
    static final String TYPE_WITH_BINDING_RESULT = "org.springframework.validation.BindingResult";
    /** Object name which will contains form errors if any in model */
    static final String MODEL_ERRORS_NAME = "errors";
    /** Name of object which will contain locale */
    static final String MODEL_LOCALE_NAME = "locale";
    /** Message resolver Helper name */
    static final String MODEL_MESSAGE_RESOLVER_NAME = "fmt";
    /** CSRF resolver Helper name */
    static final String CSRF_RESOLVER_NAME = "csrf";
    /** Condition Tester Helper name */
    static final String CONDITION_TESTER_NAME = "test";
    /** Current uri, needed for language switching */
    static final String CURRENT_URI = "uri";

    private String protocol = null;
    private String server = null;
    private String port = null;
    private String applicationName = null;
    private MessageSource messageSource;
    private MessageResolver messageResolver;
    private UrlResolver urlResolver;

    public HandlerInterceptor() {
    }

    /**
     * Handler is requested after each controller, before model and view render
     * Adds all necessary data to model and view, which we may need to use
     * inside template later.
     * @param request     Http Request
     * @param response    Http Response
     * @param handler     Handler
     * @param mav         Model and View
     * @throws Exception in very rare circumstances like IO errors
     */
    @Override
    public void postHandle(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Object handler,
            final ModelAndView mav
    ) throws Exception {
        if (mav != null) {
            // Add model data only when there is some view associated, or it'll be
            // associated in future
            if (mav.getView() != null || mav.getViewName().endsWith(".jade")
                    || mav.getViewName().startsWith("redirect:")) {
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
                                // Resolving error codes with MessageSource
                                String[] codes = error.getCodes();
                                String resolvedMessage = "";
                                String tempMessage = "";
                                for (String code: codes) {
                                    tempMessage = messageSource.getMessage(code, null, null);
                                    if (!tempMessage.equals(code)) {
                                        resolvedMessage = tempMessage;
                                    }
                                }
                                errors.put(error.getField(), resolvedMessage);
                            }
                        }
                    }
                }
                // Should be outside ModelMap iterating otherwise we will get exception
                // Adding is forced because otherwise jade is output some sh!t when it shouldn't
                mav.addObject(MODEL_ERRORS_NAME, errors);

                // Adding language and country code to model
                Locale locale = RequestContextUtils.getLocale(request);
                mav.addObject(MODEL_LOCALE_NAME, locale);
                // Message resolver added
                mav.addObject(MODEL_MESSAGE_RESOLVER_NAME, messageResolver);
                // Url resolver added
                mav.addObject(DOMAIN_NAME_VARIABLE, urlResolver);

                // Condition tester
                mav.addObject(CONDITION_TESTER_NAME, new BusinessRulesService());

                // CSRF Token Resolver
                mav.addObject(CSRF_RESOLVER_NAME, new CsrfResolver(request));

                // User resolver
                mav.addObject(SECURITY_SERVICE_NAME, new UserResolver());

                // User resolver
                mav.addObject(CURRENT_URI, request.getServletPath());
            } else {
                // Removing any objects bind to model except last one
                Integer size = mav.getModel().size();
                Object toKeep = mav.getModel().entrySet().toArray()[size - 1];
                mav.getModel().clear();
                mav.addObject(toKeep);
            }
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

    public void setMessageSource(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void setMessageResolver(final MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    public void setUrlResolver(final UrlResolver urlResolver) {
        this.urlResolver = urlResolver;
    }
}
