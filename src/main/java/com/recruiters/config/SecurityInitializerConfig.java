package com.recruiters.config;

import com.recruiters.web.utils.LocaleUrlFilter;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.ServletContext;

/**
 * Spring Security Initialization
 * Runs before all other configurations
 */
@Order(1)
public class SecurityInitializerConfig extends AbstractSecurityWebApplicationInitializer {

    /**
     * Before Spring Security handler
     * We should include all filters there which should be
     * initialised before Spring Security
     * @param servletContext    Servlet context
     */
    @Override
    protected void beforeSpringSecurityFilterChain(final ServletContext servletContext) {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        insertFilters(servletContext, characterEncodingFilter);
    }

    @Override
    protected void afterSpringSecurityFilterChain(final ServletContext servletContext) {
        LocaleUrlFilter localeUrlFilter = new LocaleUrlFilter();
        insertFilters(servletContext, localeUrlFilter);
    }
}
