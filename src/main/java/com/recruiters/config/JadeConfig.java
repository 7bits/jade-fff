package com.recruiters.config;

import com.recruiters.web.utils.CustomJadeViewResolver;
import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.spring.template.SpringTemplateLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;

/**
 * Jade Templates Configuration
 */
@Configuration
public class JadeConfig {

    /**
     * Template Loader setting
     * @return template loader
     */
    @Bean
    public SpringTemplateLoader templateLoader() {
        SpringTemplateLoader templateLoader = new SpringTemplateLoader();
        templateLoader.setBasePath("/WEB-INF/views/");
        templateLoader.setEncoding("UTF-8");
        templateLoader.setSuffix(".jade");
        return templateLoader;
    }

    /**
     * Jade Configuration
     * @return jade configuration
     */
    @Bean
    public JadeConfiguration jadeConfiguration() {
        JadeConfiguration configuration = new JadeConfiguration();
        configuration.setCaching(false);
        configuration.setTemplateLoader(templateLoader());
        return configuration;
    }

    /**
     * Setup view resolver for Jade templates
     * Using custom view resolver, because Jade is bad
     * @return jade view resolver
     */
    @Bean
    public ViewResolver jadeViewResolver() {
        CustomJadeViewResolver jadeViewResolver = new CustomJadeViewResolver();
        jadeViewResolver.setConfiguration(jadeConfiguration());
        jadeViewResolver.setOrder(0);
        return jadeViewResolver;
    }
}


