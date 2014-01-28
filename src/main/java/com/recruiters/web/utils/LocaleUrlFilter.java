package com.recruiters.web.utils;

import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fairdev on 27.01.14.
 */
public class LocaleUrlFilter implements Filter {

    private static final Pattern LOCALE_PATTERN = Pattern.compile("^/([a-z]{2})(?:/([a-z]{2}))?(/.*)?");
    private static final String COUNTRY_CODE_ATTRIBUTE_NAME = LocaleUrlFilter.class.getName() + ".country";
    private static final String LANGUAGE_CODE_ATTRIBUTE_NAME = LocaleUrlFilter.class.getName() + ".language";

    public void destroy() {
    }

    public void doFilter(
            final ServletRequest servletRequest,
            final ServletResponse servletResponse,
            final FilterChain filterChain
    ) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final String url = request.getRequestURI().substring(request.getContextPath().length());
        final Matcher matcher = LOCALE_PATTERN.matcher(url);
        if (matcher.matches()) {

            request.setAttribute(COUNTRY_CODE_ATTRIBUTE_NAME, matcher.group(1));
            request.setAttribute(LANGUAGE_CODE_ATTRIBUTE_NAME, matcher.group(2));
            String endOfUrl;
            if (matcher.group(3) == null) {
                endOfUrl = "/";
            } else {
                endOfUrl = matcher.group(3);
            }
            request.getRequestDispatcher(endOfUrl)
                    .forward(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    public void init(final FilterConfig arg0) throws ServletException {}

    public static String getCountryCodeAttributeName() {
        return COUNTRY_CODE_ATTRIBUTE_NAME;
    }

    public static String getLanguageCodeAttributeName() {
        return LANGUAGE_CODE_ATTRIBUTE_NAME;
    }
}
