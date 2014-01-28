package com.recruiters.web.utils;

import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Spring Type Locale Resolver from session attributes,
 * where it appears after using
 * @link LocaleUrlFilter
 */
public class AttributeLocaleResolver implements LocaleResolver {

    /** Default locale */
    private Locale defaultLocale = null;
    /** Map with all <languages, Set<countries> using on website */
    private final Map<String, Set<String>> countryLanguages;

    public AttributeLocaleResolver(
            final Map<String, Set<String>> countryLanguages,
            final Locale defaultLocale
    ) {
        this.countryLanguages = countryLanguages;
        this.defaultLocale = defaultLocale;
    }

    /**
     * Resolving locale from attributes
     * @param servletRequest    Http Request
     * @return locale
     */
    public Locale resolveLocale(final HttpServletRequest servletRequest) {
        String languageCode = (String) servletRequest.getAttribute(LocaleUrlFilter.getLanguageCodeAttributeName());
        if (languageCode != null) {
            String countryCode = (String) servletRequest.getAttribute(LocaleUrlFilter.getCountryCodeAttributeName());
            Set countryCodes = countryLanguages.get(languageCode);
            if (countryCodes != null && !countryCodes.isEmpty()) {
                if (countryCode == null) {
                countryCode = (String) countryCodes.iterator().next();
                }
                return new Locale(languageCode, countryCode);
            }
        }

        return defaultLocale;
    }

    /**
     * Set attributes locale to locale
     * @param servletRequest     Http Request
     * @param servletResponse    Http Response
     * @param locale             Locale
     */
    public void setLocale(
            final HttpServletRequest servletRequest,
            final HttpServletResponse servletResponse,
            final Locale locale
    ) {
        if (locale != null) {
            if (locale.getLanguage() != null) {
                servletRequest.setAttribute(LocaleUrlFilter.getLanguageCodeAttributeName(), locale.getLanguage());
            }
            if (locale.getCountry() != null) {
                servletRequest.setAttribute(LocaleUrlFilter.getCountryCodeAttributeName(), locale.getCountry());
            }
        }
    }
}