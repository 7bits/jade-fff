package com.recruiters.web.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by fairdev on 27.01.14.
 */
public class CustomLocaleResolver implements LocaleResolver {

    private Locale defaultLocale = null;
    private final Map<String, List <String>> countryLanguages;

    public CustomLocaleResolver(
            final Map<String, List <String>> countryLanguages,
            final Locale defaultLocale
    ) {
        this.countryLanguages = countryLanguages;
        this.defaultLocale = defaultLocale;
    }

    public Locale resolveLocale(final HttpServletRequest servletRequest) {
        final String countryCode = (String) servletRequest.getAttribute(LocaleUrlFilter.getCountryCodeAttributeName());
        if (countryCode != null) {
            String languageCode = (String) servletRequest.getAttribute(LocaleUrlFilter.getLanguageCodeAttributeName());
            if (languageCode == null) {
                final List languageCodes = countryLanguages.get(countryCode);
                if (languageCodes != null && !languageCodes.isEmpty()) languageCode = (String) languageCodes.get(0);
            }
            if (languageCode != null) return new Locale(languageCode, countryCode);
        }

        return defaultLocale;
    }

    public void setLocale(
            final HttpServletRequest servletRequest,
            final HttpServletResponse servletResponse,
            final Locale locale
    ) {
        throw new UnsupportedOperationException();
    }
}