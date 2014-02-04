package com.recruiters.web.utils;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import java.util.Locale;

/**
 * JSON View Resolver
 */
public class JsonViewResolver implements ViewResolver{
    @Override
    public View resolveViewName(final String s, final Locale locale) throws Exception {
        MappingJacksonJsonView view = new MappingJacksonJsonView();
        view.setPrettyPrint(true);      // Lay the JSON out to be nicely readable
        return view;
    }
}
