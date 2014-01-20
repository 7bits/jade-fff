package com.recruiters.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * Spring Context Config
 * Equals web.xml setup
 * Wired after security
 */
@Order(2)
public class ContextConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * All configs except web setup
     * @return All Configs
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { SecurityConfig.class, MyBatisConfig.class, JspConfig.class, JadeConfig.class };
    }

    /**
     * Web Configuration setup
     * @return Web Config
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebMvcConfig.class };
    }

    /**
     * Root Mapping
     * @return root mapping for servlets
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    /**
     * Filters setup
     * @return filters
     */
    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new HiddenHttpMethodFilter(), new MultipartFilter()};
    }

    /**
     * Dispatcher Servlet setup
     * @param servletContext    Servlet Context
     */
    @Override
    protected void registerDispatcherServlet(final ServletContext servletContext) {
        super.registerDispatcherServlet(servletContext);
        servletContext.addListener(new HttpSessionEventPublisher());
    }

    /**
     * Custom Registration
     * @param registration    Registration
     */
    @Override
    protected void customizeRegistration(final ServletRegistration.Dynamic registration) {
        MultipartConfigElement multipartConfigElement =
                new MultipartConfigElement("");
       registration.setMultipartConfig(multipartConfigElement);
    }
}