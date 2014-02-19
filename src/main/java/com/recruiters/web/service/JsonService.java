package com.recruiters.web.service;

import com.recruiters.model.Applicant;
import com.recruiters.model.Bid;
import com.recruiters.model.ChatMessage;
import com.recruiters.model.Deal;
import com.recruiters.model.Vacancy;
import com.recruiters.model.status.VacancyStatus;
import com.recruiters.web.helper.MessageResolver;
import com.recruiters.web.helper.UrlResolver;
import org.apache.commons.lang3.StringEscapeUtils;
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
            currentVacancyJson.put("title", StringEscapeUtils.escapeHtml4(vacancy.getTitle()));
            currentVacancyJson.put("description", StringEscapeUtils.escapeHtml4(vacancy.getDescription()));
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
                    "urlText",
                    messageResolver.message("recruiter-find-new-vacancies.more", locale)
            );
            vacanciesJson.add(currentVacancyJson);
        }

        return vacanciesJson;
    }

    /**
     * Convert List of deals to Json ready list of maps for
     * use in employer filtered list of deals view
     * @param deals    List of deals
     * @param locale       Request locale
     * @return list of maps
     */
    public List<Map<String,String>> employerDealsFilteredList(
            final List<Deal> deals,
            final Locale locale
    ) {
        List<Map<String,String>> dealsJson = new ArrayList<Map<String, String>>();
        for (Deal deal: deals) {
            Map<String, String> currentDealJson = new HashMap<String, String>();
            currentDealJson.put("title", StringEscapeUtils.escapeHtml4(deal.getVacancy().getTitle()));
            currentDealJson.put(
                    "description",
                    StringEscapeUtils.escapeHtml4(deal.getVacancy().getDescription())
            );
            currentDealJson.put("created", messageResolver.date(deal.getVacancy().getCreationDate(), locale));
            currentDealJson.put("updated", messageResolver.date(deal.getLastModified(), locale));
            currentDealJson.put(
                    "status",
                    messageResolver.dealStatus(deal.getStatus(), locale)
            );
            currentDealJson.put("recruiter", deal.getRecruiter().getUser().getFirstName() + " " +
            deal.getRecruiter().getUser().getLastName());
            currentDealJson.put(
                    "recruiterUrl",
                    urlResolver.buildFullUri("/employer-show-recruiter-profile/", deal.getRecruiter().getId(), locale)
            );
            currentDealJson.put(
                    "terms",
                    StringEscapeUtils.escapeHtml4(deal.getBid().getMessage())
                    );
            currentDealJson.put("bids", deal.getBidCount().toString());
            currentDealJson.put(
                    "url",
                    urlResolver.buildFullUri("/employer-progress-vacancy-show/", deal.getId(), locale)
            );
            currentDealJson.put(
                    "urlText",
                    messageResolver.message("employer-progress-vacancies-list.more", locale)
            );
            currentDealJson.put("unseenApplicantCount", deal.getUnseenApplicantCount().toString());
            currentDealJson.put("allApplicantCount", deal.getAllApplicantCount().toString());
            currentDealJson.put("rejectedApplicantCount", deal.getRejectedApplicantCount().toString());
            currentDealJson.put("viewedApplicantCount", deal.getViewedApplicantCount().toString());
            currentDealJson.put(
                    "applicantsTooltip",
                    messageResolver.applicantsTooltip(
                            deal.getUnseenApplicantCount(),
                            deal.getAllApplicantCount(),
                            deal.getRejectedApplicantCount(),
                            deal.getViewedApplicantCount(), locale)
            );

            dealsJson.add(currentDealJson);
        }

        return dealsJson;
    }

    /**
     * Convert List of ChatMessages to Json ready list of maps for use in view
     * @param messages     List of messages
     * @param locale       Request locale
     * @return list of maps
     */
    public List<Map<String,String>> chatMessageList(
            final List<ChatMessage> messages,
            final Locale locale
    ) {
        List<Map<String,String>> messagesJson = new ArrayList<Map<String, String>>();
        for (ChatMessage message: messages) {
            Map<String, String> currentMessageJson = new HashMap<String, String>();
            currentMessageJson.put("id", message.getId().toString());
            if (message.getRecruiter() != null) {
                currentMessageJson.put("from", message.getRecruiter().getUser().getFirstName() +
                        " " + message.getRecruiter().getUser().getLastName());
            } else if (message.getEmployer() != null) {
                currentMessageJson.put("from", message.getEmployer().getUser().getFirstName() +
                        " " + message.getEmployer().getUser().getLastName());
            }
            currentMessageJson.put("date", messageResolver.date(message.getDate(), locale));
            currentMessageJson.put("message", message.getMessage());
            messagesJson.add(currentMessageJson);
        }
        return messagesJson;
    }


    /**
     * Convert List of deals to Json ready list of maps for
     * use in employer control panel to display deals
     * needed feedback to leave
     * @param deals    List of deals
     * @param locale       Request locale
     * @return list of maps
     */
    public List<Map<String,String>> employerDealsForFeedback(
            final List<Deal> deals,
            final Locale locale
    ) {
        List<Map<String,String>> dealsJson = new ArrayList<Map<String, String>>();
        for (Deal deal: deals) {
            Map<String, String> currentDealJson = new HashMap<String, String>();
            currentDealJson.put("vacancy", deal.getVacancy().getTitle());
            currentDealJson.put(
                    "vacancyUrl",
                    urlResolver.buildFullUri("/employer-progress-vacancy-show/", deal.getId(), locale)
            );
            currentDealJson.put("updated", messageResolver.date(deal.getLastModified(), locale));
            currentDealJson.put(
                    "status",
                    messageResolver.dealStatus(deal.getStatus(), locale)
            );
            currentDealJson.put("recruiter", deal.getRecruiter().getUser().getFirstName() + " " +
                    deal.getRecruiter().getUser().getLastName());
            currentDealJson.put(
                    "recruiterUrl",
                    urlResolver.buildFullUri("/employer-show-recruiter-profile/", deal.getRecruiter().getId(), locale)
            );
            currentDealJson.put(
                    "feedback",
                    messageResolver.message("employer-control-panel.feedback.leave", locale)
            );
            currentDealJson.put(
                    "feedbackUrl",
                    urlResolver.buildFullUri("/employer-progress-vacancy-show/", deal.getId(), locale)
            );

            dealsJson.add(currentDealJson);
        }

        return dealsJson;
    }


    /**
     * Convert List of applicants to Json ready list of maps for
     * use in employer control panel to display new applicants
     * @param applicants   List of applicants
     * @param locale       Request locale
     * @return list of maps
     */
    public List<Map<String,String>> employerNewApplicants(
            final List<Applicant> applicants,
            final Locale locale
    ) {
        List<Map<String,String>> applicantsJson = new ArrayList<Map<String, String>>();
        for (Applicant applicant: applicants) {
            Map<String, String> currentApplicantJson = new HashMap<String, String>();
            currentApplicantJson.put("vacancy", applicant.getDeal().getVacancy().getTitle());
            currentApplicantJson.put(
                    "vacancyUrl",
                    urlResolver.buildFullUri(
                            "/employer-progress-vacancy-show/",
                            applicant.getDeal().getId(),
                            locale
                    )
            );
            currentApplicantJson.put("updated", messageResolver.date(applicant.getDateCreated(), locale));
            currentApplicantJson.put("recruiter", applicant.getDeal().getRecruiter().getUser().getFirstName() +
                    " " + applicant.getDeal().getRecruiter().getUser().getLastName());
            currentApplicantJson.put(
                    "recruiterUrl",
                    urlResolver.buildFullUri(
                            "/employer-show-recruiter-profile/",
                            applicant.getDeal().getRecruiter().getId(),
                            locale
                    )
            );
            currentApplicantJson.put("applicant", applicant.getFirstName() + " " + applicant.getLastName());
            currentApplicantJson.put(
                    "applicantUrl",
                    urlResolver.buildFullUri(
                            "/employer-applicant-show/",
                            applicant.getId(),
                            locale
                    )
            );

            applicantsJson.add(currentApplicantJson);
        }

        return applicantsJson;
    }


    /**
     * Convert List of bids to Json ready list of maps for
     * use in employer control panel to display new bids
     * @param bids         List of bids
     * @param locale       Request locale
     * @return list of maps
     */
    public List<Map<String,String>> employerNewBids(
            final List<Bid> bids,
            final Locale locale
    ) {
        List<Map<String,String>> bidsJson = new ArrayList<Map<String, String>>();
        for (Bid bid: bids) {
            Map<String, String> currentBidJson = new HashMap<String, String>();
            currentBidJson.put("vacancy", bid.getVacancy().getTitle());
            currentBidJson.put(
                    "vacancyUrl",
                    urlResolver.buildFullUri(
                            "/employer-show-recruiter-bids/",
                            bid.getVacancy().getId(),
                            locale
                    )
            );
            currentBidJson.put("updated", messageResolver.date(bid.getDateCreated(), locale));
            currentBidJson.put("recruiter", bid.getRecruiter().getUser().getFirstName() +
                    " " + bid.getRecruiter().getUser().getLastName());
            currentBidJson.put(
                    "recruiterUrl",
                    urlResolver.buildFullUri(
                            "/employer-show-recruiter-profile/",
                            bid.getRecruiter().getId(),
                            locale
                    )
            );
            currentBidJson.put("bid", messageResolver.message("employer-control-panel.bids.view", locale));
            currentBidJson.put(
                    "bidUrl",
                    urlResolver.buildFullUri(
                            "/employer-recruiter-show/",
                            bid.getId(),
                            locale
                    )
            );

            bidsJson.add(currentBidJson);
        }

        return bidsJson;
    }


    /**
     * Convert List of deals to Json ready list of maps for
     * use in recruiter control panel to display deals
     * needed feedback to leave
     * @param deals    List of deals
     * @param locale       Request locale
     * @return list of maps
     */
    public List<Map<String,String>> recruiterDealsForFeedback(
            final List<Deal> deals,
            final Locale locale
    ) {
        List<Map<String,String>> dealsJson = new ArrayList<Map<String, String>>();
        for (Deal deal: deals) {
            Map<String, String> currentDealJson = new HashMap<String, String>();
            currentDealJson.put("vacancy", deal.getVacancy().getTitle());
            currentDealJson.put(
                    "vacancyUrl",
                    urlResolver.buildFullUri("/recruiter-show-in-progress-vacancy/", deal.getId(), locale)
            );
            currentDealJson.put("updated", messageResolver.date(deal.getLastModified(), locale));
            currentDealJson.put(
                    "status",
                    messageResolver.dealStatus(deal.getStatus(), locale)
            );
            currentDealJson.put("employer", deal.getVacancy().getEmployer().getUser().getFirstName() + " " +
                    deal.getVacancy().getEmployer().getUser().getLastName());
            currentDealJson.put(
                    "employerUrl",
                    urlResolver.buildFullUri(
                            "/recruiter-show-employer-profile/",
                            deal.getVacancy().getEmployer().getId(),
                            locale
                    )
            );
            currentDealJson.put(
                    "feedback",
                    messageResolver.message("recruiter-control-panel.feedback.leave", locale)
            );
            currentDealJson.put(
                    "feedbackUrl",
                    urlResolver.buildFullUri("/recruiter-show-in-progress-vacancy/", deal.getId(), locale)
            );

            dealsJson.add(currentDealJson);
        }

        return dealsJson;
    }


    /**
     * Convert List of deals to Json ready list of maps for
     * use in recruiter control panel to display unseen deals
     * @param deals    List of deals
     * @param locale       Request locale
     * @return list of maps
     */
    public List<Map<String,String>> recruiterNewDeals(
            final List<Deal> deals,
            final Locale locale
    ) {
        List<Map<String,String>> dealsJson = new ArrayList<Map<String, String>>();
        for (Deal deal: deals) {
            Map<String, String> currentDealJson = new HashMap<String, String>();
            currentDealJson.put("vacancy", deal.getVacancy().getTitle());
            currentDealJson.put(
                    "vacancyUrl",
                    urlResolver.buildFullUri("/recruiter-show-in-progress-vacancy/", deal.getId(), locale)
            );
            currentDealJson.put("created", messageResolver.date(deal.getDateCreated(), locale));
            currentDealJson.put("employer", deal.getVacancy().getEmployer().getUser().getFirstName() + " " +
                    deal.getVacancy().getEmployer().getUser().getLastName());
            currentDealJson.put(
                    "employerUrl",
                    urlResolver.buildFullUri(
                            "/recruiter-show-employer-profile/",
                            deal.getVacancy().getEmployer().getId(),
                            locale
                    )
            );
            currentDealJson.put(
                    "deal",
                    messageResolver.message("recruiter-control-panel.deals.view", locale)
            );
            currentDealJson.put(
                    "dealUrl",
                    urlResolver.buildFullUri("/recruiter-show-in-progress-vacancy/", deal.getId(), locale)
            );

            dealsJson.add(currentDealJson);
        }

        return dealsJson;
    }


    /**
     * Convert List of bids to Json ready list of maps for
     * use in employer control panel to display status of last bids
     * @param bids         List of bids
     * @param locale       Request locale
     * @return list of maps
     */
    public List<Map<String,String>> recruiterLastBids(
            final List<Bid> bids,
            final Locale locale
    ) {
        List<Map<String,String>> bidsJson = new ArrayList<Map<String, String>>();
        for (Bid bid: bids) {
            Map<String, String> currentBidJson = new HashMap<String, String>();
            currentBidJson.put("vacancy", bid.getVacancy().getTitle());
            currentBidJson.put(
                    "vacancyUrl",
                    urlResolver.buildFullUri(
                            "/recruiter-show-bid-vacancy/",
                            bid.getVacancy().getId(),
                            locale
                    )
            );
            currentBidJson.put("updated", messageResolver.date(bid.getDateCreated(), locale));
            currentBidJson.put("employer", bid.getVacancy().getEmployer().getUser().getFirstName() + " " +
                    bid.getVacancy().getEmployer().getUser().getLastName());
            currentBidJson.put(
                    "employerUrl",
                    urlResolver.buildFullUri(
                            "/recruiter-show-employer-profile/",
                            bid.getVacancy().getEmployer().getId(),
                            locale
                    )
            );
            if (bid.getViewed()) {
                currentBidJson.put("viewed", messageResolver.message("recruiter-control-panel.bids.viewed", locale));
            } else {
                currentBidJson.put("viewed", "");
            }
            currentBidJson.put("status", messageResolver.bidStatus(bid.getStatus(), locale));
            currentBidJson.put("bid", messageResolver.message("recruiter-control-panel.bids.view", locale));
            currentBidJson.put(
                    "bidUrl",
                    urlResolver.buildFullUri(
                            "/recruiter-show-bid-vacancy/",
                            bid.getId(),
                            locale
                    )
            );

            bidsJson.add(currentBidJson);
        }

        return bidsJson;
    }


    /**
     * Convert List of Vacancies to Json ready list of maps for use in view
     * @param vacancies    List of vacancies
     * @param locale       Request locale
     * @return list of maps
     */
    public List<Map<String,String>> employerVacanciesFilteredList(
            final List<Vacancy> vacancies,
            final Locale locale
    ) {
        List<Map<String,String>> vacanciesJson = new ArrayList<Map<String, String>>();
        for (Vacancy vacancy: vacancies) {
            Map<String, String> currentVacancyJson = new HashMap<String, String>();
            currentVacancyJson.put("title", StringEscapeUtils.escapeHtml4(vacancy.getTitle()));
            currentVacancyJson.put("description", StringEscapeUtils.escapeHtml4(vacancy.getDescription()));
            currentVacancyJson.put("created", messageResolver.date(vacancy.getCreationDate(), locale));
            currentVacancyJson.put("updated", messageResolver.date(vacancy.getLastModified(), locale));
            currentVacancyJson.put(
                    "status",
                    messageResolver.vacancyStatus(vacancy.getStatus(), locale)
            );
            if (vacancy.getStatus().equals(VacancyStatus.UNPUBLISHED)) {
                currentVacancyJson.put(
                        "url",
                        urlResolver.buildFullUri("/employer-vacancy-edit/", vacancy.getId(), locale)
                );
                currentVacancyJson.put(
                        "urlText",
                        messageResolver.message("employer-recruiter-search.table.edit", locale)
                );
            } else {
                currentVacancyJson.put(
                        "url",
                        urlResolver.buildFullUri("/employer-show-recruiter-bids/", vacancy.getId(), locale)
                );
                currentVacancyJson.put(
                        "urlText",
                        messageResolver.message("employer-recruiter-search.table.more", locale)
                );
            }
            currentVacancyJson.put("unseenBidCount", vacancy.getUnseenBidCount().toString());
            currentVacancyJson.put("allBidCount", vacancy.getBidCount().toString());
            currentVacancyJson.put("rejectedBidCount", vacancy.getRejectedBidCount().toString());
            currentVacancyJson.put("viewedBidCount", vacancy.getViewedBidCount().toString());
            currentVacancyJson.put(
                    "bidsTooltip",
                    messageResolver.bidsTooltip(
                            vacancy.getUnseenBidCount(),
                            vacancy.getBidCount(),
                            vacancy.getRejectedBidCount(),
                            vacancy.getViewedBidCount(), locale)
            );

            vacanciesJson.add(currentVacancyJson);
        }

        return vacanciesJson;
    }


    /**
     * Convert Bid to map for use in view
     * @param bid          Bid
     * @param locale       Request locale
     * @return map with bid
     */
    public Map<String, Map<String,String>> employerShowBid(
            final Bid bid,
            final Locale locale
    ) {
        Map<String, String> bidJson = new HashMap<String, String>();
        bidJson.put("popupTitle", messageResolver.message("employer-recruiter-show.bid.title", locale));
        bidJson.put("headCreated", messageResolver.message("employer-recruiter-show.bid.created", locale));
        bidJson.put("created", messageResolver.date(bid.getDateCreated(), locale));
        bidJson.put("headMessage", messageResolver.message("employer-recruiter-show.bid.message", locale));
        bidJson.put("message", StringEscapeUtils.escapeHtml4(bid.getMessage()));
        bidJson.put("headRecruiter", messageResolver.message("employer-recruiter-show.bid.recruiter", locale));
        bidJson.put("recruiter", bid.getRecruiter().getUser().getFirstName() + " " +
                bid.getRecruiter().getUser().getLastName());
        bidJson.put(
                "recruiterUrl",
                urlResolver.buildFullUri("/employer-show-recruiter-profile/", bid.getRecruiter().getId(), locale)
        );

        Map<String,Map<String,String>> mapJson = new HashMap<String, Map<String, String>>();
        mapJson.put("bid", bidJson);
        return mapJson;
    }
}
