package com.recruiters.config;

import com.recruiters.web.utils.AttributeLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.LocaleResolver;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Locale resolver config
 */
@Configuration
@PropertySource("classpath:language.properties")
public class LocaleResolverConfig {
    /** Property key for default locale */
    private static final String PROPERTY_DEFAULT_LOCALE = "recruiter-language.default";
    /** Property key for languages */
    private static final String PROPERTY_LANGUAGES = "recruiter-language.languages";
    /** Property keys for countries start with */
    private static final String PROPERTY_COUNTRIES_START_WITH = "recruiter-language.countries";

    @Inject
    private org.springframework.core.env.Environment environment;

    /**
     * Configure Locale Resolver with country and language codes,
     * for which our application have translations
     * @return Locale Resolver
     */
    @Bean
    public LocaleResolver localeResolver() {
        String defaultLocale = environment.getProperty(PROPERTY_DEFAULT_LOCALE);
        String[] languages = environment.getProperty(PROPERTY_LANGUAGES).split(",");
        Map<String, Set<String>> countryLanguages = new HashMap<String, Set<String>>();
        for (String language : languages) {
            String propertyCode = PROPERTY_COUNTRIES_START_WITH + "." + language;
            StringTokenizer st = new StringTokenizer(
                    environment.getProperty(propertyCode),
                    ","
            );
            Set<String> countries = new HashSet<String>();
            while (st.hasMoreTokens()) {
                countries.add(st.nextToken());
            }
            countryLanguages.put(language, countries);
        }
        AttributeLocaleResolver localeResolver = attributeLocaleResolver();
        localeResolver.setCountryLanguages(countryLanguages);
        localeResolver.setDefaultLocale(new Locale(defaultLocale));
        return localeResolver;
    }

    /**
     * Create Bean for our custom Locale Resolver via attributes
     * added with filter to Session scope
     * @return Locale resolver
     */
    @Bean
    public AttributeLocaleResolver attributeLocaleResolver() {
        return new AttributeLocaleResolver();
    }
}
