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

    @Inject
    private org.springframework.core.env.Environment environment;

    /**
     * Configure Locale Resolver with country and language codes,
     * for which our application have translations
     * @return Locale Resolver
     */
    @Bean
    public LocaleResolver localeResolver() {
        String defaultLocale = environment.getProperty("recruiter-language.default");
        String[] languages = environment.getProperty("recruiter-language.languages").split(",");
        Map<String, Set<String>> countryLanguages = new HashMap<String, Set<String>>();
        for (String language : languages) {
            String propertyCode = "recruiter-language.languages" + "." + language;
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
