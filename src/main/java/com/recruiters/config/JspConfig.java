package com.recruiters.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * Jsp configuration
 */
@Configuration
public class JspConfig {

    /**
     * JSP View Resolver configuration
     * @return view resolver for JSP
     */
    @Bean
    public UrlBasedViewResolver urlBasedViewResolver() {
        UrlBasedViewResolver jspViewResolver = new InternalResourceViewResolver();
        jspViewResolver.setViewClass(JstlView.class);
        jspViewResolver.setPrefix("/WEB-INF/view/");
        jspViewResolver.setSuffix(".jsp");
        jspViewResolver.setOrder(1);

        return jspViewResolver;
    }

}
