package com.recruiters.config;

import com.recruiters.web.helper.MessageResolver;
import com.recruiters.web.helper.UrlResolver;
import com.recruiters.web.utils.AttributeLocaleResolver;
import com.recruiters.web.utils.CustomJadeViewResolver;
import com.recruiters.web.utils.HandlerInterceptor;
import com.recruiters.web.utils.JsonViewResolver;
import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.spring.template.SpringTemplateLoader;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

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
     * Create Bean for  Multi-part Resolver
     * @return Multi-part resolver
     */
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    /**
     * Create Bean for Message Resolver
     * @return Message Resolver
     */
    @Bean
    public MessageResolver messageResolver() {
        return new MessageResolver();
    }


    /**
     * Create and configure bean for Url Resolver, used to generate all uris
     * and urls across whole application
     * @return Url resolver singleton
     */
    @Bean
    public UrlResolver urlResolver() {
        UrlResolver urlResolver = new UrlResolver();
        urlResolver.setProtocol(environment.getProperty("recruiter-server.protocol"));
        urlResolver.setServer(environment.getProperty("recruiter-server.server"));
        urlResolver.setPort(environment.getProperty("recruiter-server.port"));
        urlResolver.setApplicationName(environment.getProperty("recruiter-server.application-name"));
        return urlResolver;
    }

    /**
     * Configure content negotiation rules
     */
    @Override
    public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
        configurer.ignoreAcceptHeader(true);
        configurer.defaultContentType(MediaType.TEXT_HTML);
    }

    /**
     * Setup content negotiation view resolver
     */
    @Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager)
            throws Exception {
        List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
        ViewResolver jadeViewResolver = jadeViewResolver();
        ViewResolver jsonViewResolver = jsonViewResolver();
        ViewResolver jspViewResolver = jspViewResolver();
        resolvers.add(jadeViewResolver);
        resolvers.add(jsonViewResolver);
        resolvers.add(jspViewResolver);
        List<View> views = new ArrayList<View>();
        views.add(jsonViewResolver.resolveViewName(null, null));
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);
        resolver.setViewResolvers(resolvers);
        resolver.setDefaultViews(views);

        return resolver;
    }

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

    /**
     * JSON View Resolver
     * @return view resolver for json
     */
    @Bean
    public ViewResolver jsonViewResolver() {

        return new JsonViewResolver();
    }


    /**
     * JSP View Resolver configuration
     * @return view resolver for JSP
     */
    @Bean
    public ViewResolver jspViewResolver() {
        UrlBasedViewResolver jspViewResolver = new InternalResourceViewResolver();
        jspViewResolver.setViewClass(JstlView.class);
        jspViewResolver.setPrefix("/WEB-INF/view/");
        jspViewResolver.setSuffix(".jsp");
        jspViewResolver.setOrder(1);

        return jspViewResolver;
    }
}

