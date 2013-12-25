package com.recruiters.config;

import com.recruiters.web.controller.interceptor.HandlerInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.inject.Inject;

@Configuration
@PropertySource("classpath:server.properties")
@EnableWebMvc
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
        // if true, the key of the message will be displayed if the key is not
        // found, instead of throwing a NoSuchMessageException
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        // # -1 : never reload, 0 always reload
        messageSource.setCacheSeconds(0);
        return messageSource;
    }
}

