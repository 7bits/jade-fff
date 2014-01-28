package com.recruiters.config;

import com.recruiters.web.helper.MessageResolver;
import com.recruiters.web.helper.UrlResolver;
import com.recruiters.web.utils.AttributeLocaleResolver;
import com.recruiters.web.utils.HandlerInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Spring Web Configuration
 */
@Configuration
@PropertySource({"classpath:server.properties", "classpath:language.properties"})
@EnableWebMvc
@ComponentScan(basePackages = { "com.recruiters" })
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Inject
    private org.springframework.core.env.Environment environment;

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
        handlerInterceptor.setMessageSource(messageSource());
        handlerInterceptor.setMessageResolver(messageResolver());
        handlerInterceptor.setUrlResolver(urlResolver());
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
        AttributeLocaleResolver localeResolver = new AttributeLocaleResolver(
                countryLanguages,
                new Locale(defaultLocale)
        );
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

    @Bean
    public MessageResolver messageResolver() {
        return new MessageResolver();
    }

    @Bean
    public UrlResolver urlResolver() {
        UrlResolver urlResolver = new UrlResolver();
        urlResolver.setProtocol(environment.getProperty("recruiter-server.protocol"));
        urlResolver.setServer(environment.getProperty("recruiter-server.server"));
        urlResolver.setPort(environment.getProperty("recruiter-server.port"));
        urlResolver.setApplicationName(environment.getProperty("recruiter-server.application-name"));
        return urlResolver;
    }
}

