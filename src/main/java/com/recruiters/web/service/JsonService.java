package com.recruiters.web.service;

import com.recruiters.model.Vacancy;
import com.recruiters.web.helper.MessageResolver;
import com.recruiters.web.helper.UrlResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Creates json maps for web layer
 */
@Component
public class JsonService {
    @Autowired
    private MessageResolver messageResolver;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UrlResolver urlResolver;

    /**
     * Convert List of Vacancies to Json ready list of maps for use in view
     * @param vacancies    List of vacancies
     * @param locale       Request locale
     * @return list of maps
     */
    public List<Map<String,String>> recruiterVacanciesFilteredList(
            final List<Vacancy> vacancies,
            final Locale locale
    ) {
        List<Map<String,String>> vacanciesJson = new ArrayList<Map<String, String>>();
        for (Vacancy vacancy: vacancies) {
            Map<String, String> currentVacancyJson = new HashMap<String, String>();
            currentVacancyJson.put("title", vacancy.getTitle());
            currentVacancyJson.put("description", vacancy.getDescription());
            currentVacancyJson.put("created", messageResolver.date(vacancy.getCreationDate(), locale));
            if (vacancy.getDealId() != 0L) {
                currentVacancyJson.put(
                        "status",
                        messageResolver.message("recruiter-find-new-vacancies.table.deal", locale)
                );
                currentVacancyJson.put(
                        "url",
                        urlResolver.buildFullUri("/recruiter-show-in-progress-vacancy/", vacancy.getDealId(), locale)
                );
            } else if (vacancy.getBidId() != 0L) {
                currentVacancyJson.put(
                        "status",
                        messageResolver.message("recruiter-find-new-vacancies.table.bid", locale)
                );
                currentVacancyJson.put(
                        "url",
                        urlResolver.buildFullUri("/recruiter-show-bid-vacancy/", vacancy.getBidId(), locale)
                );
            } else {
                currentVacancyJson.put(
                        "status",
                        messageResolver.message("recruiter-find-new-vacancies.table.vacancy", locale)
                );
                currentVacancyJson.put(
                        "url",
                        urlResolver.buildFullUri("/recruiter-show-vacancy/", vacancy.getId(), locale)
                );
            }
            currentVacancyJson.put(
                    "urltext",
                    messageResolver.message("recruiter-find-new-vacancies.more", locale)
            );
            vacanciesJson.add(currentVacancyJson);
        }
        return vacanciesJson;
    }
}
