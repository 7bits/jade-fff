package com.recruiters.config;

import com.recruiters.web.controller.iterceptor.HandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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
        registry.addInterceptor(handlerInterceptor);
    }

}

