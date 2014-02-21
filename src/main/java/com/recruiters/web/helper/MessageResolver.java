package com.recruiters.web.helper;

import com.recruiters.model.status.ApplicantStatus;
import com.recruiters.model.status.BidStatus;
import com.recruiters.model.status.DealStatus;
import com.recruiters.model.status.VacancyStatus;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Message Resolver is used for all localization
 * and localized formatting purposes
 */
public class MessageResolver {
    @Autowired
    private MessageSource messageSource;

    public MessageResolver() {}

    /**
     * Resolve message by code
     * @param code      Code to resolve
     * @param locale    Locale to use
     * @return localised message
     */
    public String message(final String code, final Locale locale) {
        try {
            return messageSource.getMessage(code, null, locale);
        } catch (Exception e) {
            return code;
        }
    }

    /**
     * Resolve localised date by Date type time argument
     * @param date             Input Date type time
     * @param locale           Locale to use
     * @return localised time
     */
    public String date(final Date date, final Locale locale) {
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

    /**
     * Resolve message for bid status
     * @param bidStatus    Bid status
     * @param locale       Locale to use
     * @return localised bid status
     */
    public String bidStatus(final BidStatus bidStatus, final Locale locale) {
        String stringStatus = "";
        if (bidStatus != null) {
            String messageCode = bidStatus.getClass().getSimpleName() + "." + bidStatus.getName();
            stringStatus = messageSource.getMessage(messageCode, null, locale);
        }
        return stringStatus;
    }

    /**
     * Resolve message for deal status
     * @param dealStatus    Deal status
     * @param locale        Locale to use
     * @return localised deal status
     */
    public String dealStatus(final DealStatus dealStatus, final Locale locale) {
        String stringStatus = "";
        if (dealStatus != null) {
            String messageCode = dealStatus.getClass().getSimpleName() + "." + dealStatus.getName();
            stringStatus = messageSource.getMessage(messageCode, null, locale);
        }
        return stringStatus;
    }

    /**
     * Resolve message for applicant status
     * @param applicantStatus    Applicant status
     * @param locale             Locale to use
     * @return localised applicant status
     */
    public String applicantStatus(final ApplicantStatus applicantStatus, final Locale locale) {
        String stringStatus = "";
        if (applicantStatus != null) {
            String messageCode = applicantStatus.getClass().getSimpleName() + "." + applicantStatus.getName();
            stringStatus = messageSource.getMessage(messageCode, null, locale);
        }
        return stringStatus;
    }

    /**
     * Resolve message for vacancy status
     * @param vacancyStatus    Vacancy status
     * @param locale           Locale to use
     * @return localised vacancy status
     */
    public String vacancyStatus(final VacancyStatus vacancyStatus, final Locale locale) {
        String stringStatus = "";
        if (vacancyStatus != null) {
            String messageCode = vacancyStatus.getClass().getSimpleName() + "." + vacancyStatus.getName();
            stringStatus = messageSource.getMessage(messageCode, null, locale);
        }
        return stringStatus;
    }

    /**
     * Building tooltip for applicants
     * @param unseenApplicantCount      Number of unseen applicants
     * @param allApplicantCount         Total number of applicants
     * @param rejectedApplicantCount    Number of rejected applicants
     * @param viewedApplicantCount      Number of applicants already viewed by employer
     * @param locale                    Locale to use
     * @return localised tooltip
     */
    public String applicantsTooltip(
            final Long unseenApplicantCount,
            final Long allApplicantCount,
            final Long rejectedApplicantCount,
            final Long viewedApplicantCount,
            final Locale locale
    ) {
        return messageSource.getMessage("recruiter-active-deals.table.applicants.tooltip", new Object[] {
                unseenApplicantCount,
                allApplicantCount,
                rejectedApplicantCount,
                viewedApplicantCount
        }, locale);
    }


    /**
     * Building tooltip for bids
     * @param unseenBidCount      Number of unseen bids
     * @param allBidCount         Total number of bids
     * @param rejectedBidCount    Number of rejected bids
     * @param viewedBidCount      Number of bids already viewed by employer
     * @param locale                    Locale to use
     * @return localised tooltip
     */
    public String bidsTooltip(
            final Long unseenBidCount,
            final Long allBidCount,
            final Long rejectedBidCount,
            final Long viewedBidCount,
            final Locale locale
    ) {
        return messageSource.getMessage("employer-recruiter-search.table.bids-tooltip", new Object[] {
                unseenBidCount,
                allBidCount,
                rejectedBidCount,
                viewedBidCount
        }, locale);
    }

    /**
     * Currency range formatter
     * @param from    From $
     * @param to      To $
     * @return localised currency range
     */
    public String currencyRange(final Long from, final Long to, final Locale locale) {

        return messageSource.getMessage("currency.range", new Object[] {from, to}, locale);
    }

    /**
     * Check if date is Today
     * @param date    input time
     * @return true if date is today, otherwise false
     */
    private Boolean dateIsToday(final Date date) {
        if (date == null) {
            return false;
        }
        Date currentDate = new Date();
        return DateUtils.isSameDay(date, currentDate);
    }

    /**
     * Check if date is tomorrow
     * @param date    input time
     * @return true if date is tomorrow, otherwise false
     */
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

    /**
     * Check if date is yesterday
     * @param date    input time
     * @return true if date is yesterday, otherwise false
     */
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
