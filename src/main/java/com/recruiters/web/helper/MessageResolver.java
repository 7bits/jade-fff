package com.recruiters.web.helper;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * Jade Helper for resolving messages by code
 */
@Component
public class MessageResolver {
    // Cannot be autowired
    private MessageSource messageSource;

    public MessageResolver() {}

    public MessageResolver(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Main method, resolves messages by code
     * @param code code to resolve
     * @return message
     */
    public String get(final String code) {

        try {
            return messageSource.getMessage(code, null, null);
        } catch (Exception e) {
            return code;
        }
    }
}
