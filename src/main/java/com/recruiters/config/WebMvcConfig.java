package com.recruiters.config;

import com.recruiters.web.utils.HandlerInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.inject.Inject;

/**
 * Spring Web Configuration
 */
@Configuration
@PropertySource("classpath:server.properties")
@EnableWebMvc
@ComponentScan(basePackages = { "com.recruiters" })
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Inject
    private org.springframework.core.env.Environment environment;
    @Value("classpath:language.properties")
    private Resource languageChooseResource;

    /**
     * Configure resource handler
     * @param registry    Resource Handler Registry
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    /**
     * Configure Interceptors
     * @param registry    Interceptors Registry
     */
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        HandlerInterceptor handlerInterceptor = new HandlerInterceptor();
        handlerInterceptor.setProtocol(environment.getProperty("recruiter-server.protocol"));
        handlerInterceptor.setServer(environment.getProperty("recruiter-server.server"));
        handlerInterceptor.setPort(environment.getProperty("recruiter-server.port"));
        handlerInterceptor.setApplicationName(environment.getProperty("recruiter-server.application-name"));
        handlerInterceptor.setMessageSource(messageSource());
        registry.addInterceptor(handlerInterceptor);
    }

    /**
     * Configure Spring Message Source
     * @return Message Source
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("/WEB-INF/messages/messages");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(0);
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }

    /**
     * Configure Locale Resolver
     * @return Locale Resolver
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        //localeResolver.setDefaultLocale(StringUtils.parseLocaleString("ru"));
        localeResolver.setCookieName("locale");
        return localeResolver;
    }

    /**
     * Configure Request Mapping Handler
     * @return Request Mapping Handler
     */
    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter requestMappingHandlerAdapter = new RequestMappingHandlerAdapter();
        requestMappingHandlerAdapter.setIgnoreDefaultModelOnRedirect(true);
        return requestMappingHandlerAdapter;
    }

    /**
     * Configure Multi-part Resolver
     * @return Multi-part resolver
     */
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}

