package com.recruiters.web.controller.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Different date/time-related utils for controller layer
 */
public class DateTimeUtils {

    /** Date pattern */
    private static final  String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * Make next day to param date
     * @param date    Date
     * @return next day date
     */
    public Date nextDay(final Date date) {
        Calendar calDate = Calendar.getInstance();
        calDate.setTime(date);
        calDate.add(Calendar.DATE, 1);

        return calDate.getTime();
    }

    /**
     * Make previous day to param date
     * @param date    Date
     * @return previous day date
     */
    public Date previousDay(final Date date) {
        Calendar calDate = Calendar.getInstance();
        calDate.setTime(date);
        calDate.add(Calendar.DATE, -1);

        return calDate.getTime();
    }

    /**
     * Test if param date is today
     * @param date    Date
     * @return true if param date is today, otherwise false
     */
    public Boolean isToday(final Date date) {
        Date today = new Date();

        return DateUtils.isSameDay(date, today);
    }

    /**
     * Format Date to string used in urls
     * @param date    Date
     * @return string with date formatted with pattern
     */
    public String dateUrlFormat(final Date date) {
        SimpleDateFormat linkDateFormatter = new SimpleDateFormat(DATE_PATTERN);

        return linkDateFormatter.format(date);
    }

    /**
     * Return Date from url string based on defined pattern
     * @param urlDate    String date representation
     * @return date
     */
    public Date urlDateParse(final String urlDate) {
        SimpleDateFormat linkDateFormatter = new SimpleDateFormat(DATE_PATTERN);
        try {
            return linkDateFormatter.parse(urlDate);
        } catch (ParseException e) {
            return null;
        }
    }
}
