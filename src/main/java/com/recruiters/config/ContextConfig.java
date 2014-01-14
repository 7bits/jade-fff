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
import java.io.File;

/**
 *  Second order configs
 */
@Order(2)
public class ContextConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

//    /** Max upload file size 10 MB */
//    private static final int MAX_UPLOAD_FILE_SIZE = 10485760;
//
//    /** Upload path */
//    private static final String UPLOAD_PATH = "/Users/admin/Downloads/";

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { SecurityConfig.class, MyBatisConfig.class, JspConfig.class, JadeConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebMvcConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new HiddenHttpMethodFilter(), new MultipartFilter()};
    }

    @Override
    protected void registerDispatcherServlet(final ServletContext servletContext) {
        super.registerDispatcherServlet(servletContext);
        servletContext.addListener(new HttpSessionEventPublisher());
    }

    @Override
    protected void customizeRegistration(final ServletRegistration.Dynamic registration) {
        MultipartConfigElement multipartConfigElement =
                new MultipartConfigElement("");
       registration.setMultipartConfig(multipartConfigElement);
    }
}