package com.recruiters.web.helper;

import com.recruiters.model.ApplicantStatus;
import com.recruiters.model.BidStatus;
import com.recruiters.model.DealStatus;
import com.recruiters.model.VacancyStatus;
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
 * Message Resolver for all localisation formatting purposes
 * for use inside templates
 */
@Component
public class MessageResolver {
    // Cannot auto-wire
    private MessageSource messageSource;
    // Cannot auto-wire
    private Locale locale;

    public MessageResolver() {}

    /**
     * Constructor with required objects needed for localisation
     * @param messageSource    Spring message source
     * @param locale           Requester locale
     */
    public MessageResolver(final MessageSource messageSource, final Locale locale) {
        this.messageSource = messageSource;
        this.locale = locale;
    }

    /**
     * Resolve message by code
     * @param code code to resolve
     * @return localised message
     */
    public String message(final String code) {

        try {
            return messageSource.getMessage(code, null, locale);
        } catch (Exception e) {
            return code;
        }
    }

    /**
     * Resolve localised date by Date type time argument
     * @param date    input Date type time
     * @return localised time
     */
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

    /**
     * Resolve message for bid status
     * @param bidStatus    Bid status
     * @return localised bid status
     */
    public String bidStatus(final BidStatus bidStatus) {
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
     * @return localised deal status
     */
    public String dealStatus(final DealStatus dealStatus) {
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
     * @return localised applicant status
     */
    public String applicantStatus(final ApplicantStatus applicantStatus) {
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
     * @return localised vacancy status
     */
    public String vacancyStatus(final VacancyStatus vacancyStatus) {
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
     * @return localised tooltip
     */
    public String applicantsTooltip(
            final Long unseenApplicantCount,
            final Long allApplicantCount,
            final Long rejectedApplicantCount,
            final Long viewedApplicantCount
    ) {
        return messageSource.getMessage("recruiter-active-deals.table.applicants.tooltip", new Object[] {
                unseenApplicantCount,
                allApplicantCount,
                rejectedApplicantCount,
                viewedApplicantCount
        }, locale);
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
