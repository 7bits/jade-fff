package com.recruiters.web.helper;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.print.attribute.standard.DateTimeAtCompleted;
import java.text.DateFormat;
import java.util.Calendar;
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
        String stringDate = "";
        if (date == null) {
            return stringDate;
        }
        if (dateIsToday(date)) {
            stringDate = messageSource.getMessage("date.today", null, locale);
        } else if (dateIsTomorrow(date)) {
            stringDate = messageSource.getMessage("date.tomorrow", null, locale);
        } else if (dateIsYesterday(date)) {
            stringDate = messageSource.getMessage("date.yesterday", null, locale);
        } else {
            stringDate = DateFormat.getDateInstance(DateFormat.LONG, locale).format(date);
        }
        return stringDate;
    }

    private Boolean dateIsToday(final Date date) {
        if (date == null) {
            return false;
        }
        Date currentDate = new Date();
        return DateUtils.isSameDay(date, currentDate);
    }

    private Boolean dateIsTomorrow(final Date date) {
        if (date == null) {
            return false;
        }
        Calendar calDate = Calendar.getInstance();
        calDate.setTime(date);
        // Deduct 1 day from input date, if it will be than
        // equal with current date - input date is tomorrow
        calDate.add(Calendar.DATE, -1);
        Date currentDate = new Date();
        Calendar calCurrentDate = Calendar.getInstance();
        calCurrentDate.setTime(currentDate);
        return DateUtils.isSameDay(calDate, calCurrentDate);
    }

    private Boolean dateIsYesterday(final Date date) {
        if (date == null) {
            return false;
        }
        Calendar calDate = Calendar.getInstance();
        calDate.setTime(date);
        // Add 1 day from input date, if it will be than
        // equal with current date - input date is yesterday
        calDate.add(Calendar.DATE, 1);
        Date currentDate = new Date();
        Calendar calCurrentDate = Calendar.getInstance();
        calCurrentDate.setTime(currentDate);
        return DateUtils.isSameDay(calDate, calCurrentDate);
    }
}
