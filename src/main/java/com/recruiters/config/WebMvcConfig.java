package com.recruiters.config;

import com.recruiters.web.utils.HandlerInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.inject.Inject;
import javax.servlet.MultipartConfigElement;
import java.io.File;

@Configuration
@PropertySource("classpath:server.properties")
@EnableWebMvc
@ComponentScan(basePackages = { "com.recruiters" })
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Inject
    private org.springframework.core.env.Environment environment;

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

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
     * Setting up Spring Message Source
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

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        //localeResolver.setDefaultLocale(StringUtils.parseLocaleString("ru"));
        localeResolver.setCookieName("locale");
        return localeResolver;
    }

    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter requestMappingHandlerAdapter = new RequestMappingHandlerAdapter();
        requestMappingHandlerAdapter.setIgnoreDefaultModelOnRedirect(true);
        return requestMappingHandlerAdapter;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}

