package com.recruiters.web.helper;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Jade Helper for resolving messages by code
 */
@Component
public class MessageResolver {
    // Cannot be autowired
    private MessageSource messageSource;
    private Locale locale;
    private static final Long MILLISECONDS_IN_ONE_SECOND = 1000L;

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

    public String date(final Date date) {
        Date currentDate = new Date();
        Long dateDifferenceInSeconds = (currentDate.getTime() - date.getTime())/MILLISECONDS_IN_ONE_SECOND;
        String stringDate = "";
        if (dateDifferenceInSeconds < 60 && dateDifferenceInSeconds > 0) {
            stringDate = messageSource.getMessage("date.ago.less.minute", null, locale);
        } else if (dateDifferenceInSeconds < 120 && dateDifferenceInSeconds > 59) {
            stringDate = messageSource.getMessage("date.ago.1.minute", null, locale);
        } else if (dateDifferenceInSeconds < 180 && dateDifferenceInSeconds > 119) {
            stringDate = messageSource.getMessage("date.ago.2.minutes", null, locale);
        } else if (dateDifferenceInSeconds < 240 && dateDifferenceInSeconds > 179) {
            stringDate = messageSource.getMessage("date.ago.3.minutes", null, locale);
        } else if (dateDifferenceInSeconds < 300 && dateDifferenceInSeconds > 239) {
            stringDate = messageSource.getMessage("date.ago.4.minutes", null, locale);
        } else if (dateDifferenceInSeconds < 3600 && dateDifferenceInSeconds > 299) {
            Long minutes = dateDifferenceInSeconds/60;
            stringDate = messageSource.getMessage("date.ago.more.minutes",
                    new Object[]{minutes}, locale);
        } else if (dateDifferenceInSeconds < 7200 && dateDifferenceInSeconds > 3599) {
            stringDate = messageSource.getMessage("date.ago.1.hour", null, locale);
        } else if (dateDifferenceInSeconds < 10800 && dateDifferenceInSeconds > 7199) {
            stringDate = messageSource.getMessage("date.ago.2.hours", null, locale);
        } else if (dateDifferenceInSeconds < 14400 && dateDifferenceInSeconds > 10799) {
            stringDate = messageSource.getMessage("date.ago.3.hours", null, locale);
        } else if (dateDifferenceInSeconds < 18000 && dateDifferenceInSeconds > 14399) {
            stringDate = messageSource.getMessage("date.ago.4.hours", null, locale);
        } else if (dateDifferenceInSeconds < 86400 && dateDifferenceInSeconds > 17999) {
            Long hours = dateDifferenceInSeconds/3600;
            stringDate = messageSource.getMessage("date.ago.more.hours",
                    new Object[]{hours}, locale);
        } else if (dateDifferenceInSeconds < 172800 && dateDifferenceInSeconds > 86399) {
            stringDate = messageSource.getMessage("date.ago.1.day", null, locale);
        } else if (dateDifferenceInSeconds > 171199) {
            stringDate = DateFormat.getDateInstance(DateFormat.LONG, locale).format(date);
        } else if (dateDifferenceInSeconds > -60 && dateDifferenceInSeconds < 1) {
            stringDate = messageSource.getMessage("date.in.less.minute", null, locale);
        } else if (dateDifferenceInSeconds > -120 && dateDifferenceInSeconds < -59) {
            stringDate = messageSource.getMessage("date.in.1.minute", null, locale);
        } else if (dateDifferenceInSeconds > -180 && dateDifferenceInSeconds < -119) {
            stringDate = messageSource.getMessage("date.in.2.minutes", null, locale);
        } else if (dateDifferenceInSeconds > -240 && dateDifferenceInSeconds < -179) {
            stringDate = messageSource.getMessage("date.in.3.minutes", null, locale);
        } else if (dateDifferenceInSeconds > -300 && dateDifferenceInSeconds < -239) {
            stringDate = messageSource.getMessage("date.in.4.minutes", null, locale);
        } else if (dateDifferenceInSeconds > -3600 && dateDifferenceInSeconds < -299) {
            Long minutes = -dateDifferenceInSeconds/60;
            stringDate = messageSource.getMessage("date.in.more.minutes",
                    new Object[]{minutes}, locale);
        } else if (dateDifferenceInSeconds > -7200 && dateDifferenceInSeconds < -3599) {
            stringDate = messageSource.getMessage("date.in.1.hour", null, locale);
        } else if (dateDifferenceInSeconds > -10800 && dateDifferenceInSeconds < -7199) {
            stringDate = messageSource.getMessage("date.in.2.hours", null, locale);
        } else if (dateDifferenceInSeconds > -14400 && dateDifferenceInSeconds < -10799) {
            stringDate = messageSource.getMessage("date.in.3.hours", null, locale);
        } else if (dateDifferenceInSeconds > -18000 && dateDifferenceInSeconds < -14399) {
            stringDate = messageSource.getMessage("date.in.4.hours", null, locale);
        } else if (dateDifferenceInSeconds > -86400 && dateDifferenceInSeconds < -17999) {
            Long hours = -dateDifferenceInSeconds/3600;
            stringDate = messageSource.getMessage("date.in.more.hours",
                    new Object[]{hours}, locale);
        } else if (dateDifferenceInSeconds > -172800 && dateDifferenceInSeconds < -86399) {
            stringDate = messageSource.getMessage("date.in.1.day", null, locale);
        } else if (dateDifferenceInSeconds < -171199) {
            stringDate = DateFormat.getDateInstance(DateFormat.LONG, locale).format(date);
        }
        return stringDate;
    }
}
