package com.recruiters.web.helper;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Jade Helper for resolving messages by code
 */
@Component
public class MessageResolver {
    // Cannot be autowired
    private MessageSource messageSource;
    private Locale locale;

    public MessageResolver() {}

    public MessageResolver(final MessageSource messageSource, final Locale locale) {
        this.messageSource = messageSource;
        this.locale = locale;
    }

    /**
     * Main method, resolves messages by code
     * @param code code to resolve
     * @return message
     */
    public String message(final String code) {

        try {
            return messageSource.getMessage(code, null, locale);
        } catch (Exception e) {
            return code;
        }
    }
}
