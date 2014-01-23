package com.recruiters.web.controller.recruiter;

import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.service.RecruiterService;
import com.recruiters.service.ServiceException;
import com.recruiters.web.controller.utils.UserUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Show Vacancies for recruiter with all available actions
 */
@Controller
public class AllAvailableVacancies {

    /** Recruiter Service provides all Recruiter related methods */
    @Autowired
    private RecruiterService recruiterService = null;
    /** User utils for obtaining any session user information */
    @Autowired
    private UserUtils userUtils = null;

    /**
     * Shows vacancies with filter applied for recruiter
     * @param request        Http Request
     * @param response       Http Response
     * @param date           Date in format ddMMyyyy, for example 23012013
     * @param searchText     Search text, can be empty, so will not be used
     * @param showVacancies  Show vacancies without bids or not
     * @param showBids       Show bids or not
     * @param showDeals      Show deals or not
     * @return model and view with list of vacancies,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/{locale}/recruiter-find-new-vacancies", method = RequestMethod.GET)
    public ModelAndView showFilteredAvailableVacancies(
            final HttpServletRequest request,
            final HttpServletResponse response,
            @RequestParam(value = "searchText", required = false) final String searchText,
            @RequestParam(value = "showVacancies", required = false) final Boolean showVacancies,
            @RequestParam(value = "showBids", required = false) final Boolean showBids,
            @RequestParam(value = "showDeals", required = false) final Boolean showDeals,
            @RequestParam(value = "date", required = false) final String date
    ) throws Exception {
        ModelAndView filteredVacancies = new ModelAndView("recruiter/recruiter-find-new-vacancies.jade");
        filteredVacancies.addObject("searchText", searchText);
        filteredVacancies.addObject("showVacancies", showVacancies);
        filteredVacancies.addObject("showBids", showBids);
        filteredVacancies.addObject("showDeals", showDeals);
        String requestDate = "";
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat linkDateFormatter = new SimpleDateFormat("ddMMyyyy");
        Date today = new Date();
        Date paramDate;

        if (date == null) {
            requestDate = dateFormatter.format(today);
        } else {
            paramDate = linkDateFormatter.parse(date);
            requestDate = dateFormatter.format(paramDate);
        }
        Date curDate = dateFormatter.parse(requestDate);
        String curDateParam = linkDateFormatter.format(curDate);
        filteredVacancies.addObject("curDateParam", curDateParam);
        String dateLink = "?searchText=";
        dateLink += searchText == null ? "" : searchText;
        dateLink += "&showVacancies=";
        dateLink += showVacancies == null ? "" : showVacancies;
        dateLink += "&showBids=";
        dateLink += showBids == null ? "" : showBids;
        dateLink += "&showDeals=";
        dateLink += showDeals == null ? "" : showDeals;

        // If requested date is not today
        if (!DateUtils.isSameDay(curDate, today)) {
            Calendar calDate = Calendar.getInstance();
            calDate.setTime(curDate);
            calDate.add(Calendar.DATE, 1);
            Date nextDate = calDate.getTime();
            String nextDateLink = dateLink + "&date=" + linkDateFormatter.format(nextDate);
            filteredVacancies.addObject("nextDate", nextDate);
            filteredVacancies.addObject("nextDateLink", nextDateLink);
        }
        Calendar calDate = Calendar.getInstance();
        calDate.setTime(curDate);
        calDate.add(Calendar.DATE, -1);
        Date prevDate = calDate.getTime();
        String prevDateLink = dateLink + "&date=" + linkDateFormatter.format(prevDate);
        filteredVacancies.addObject("curDate", curDate);
        filteredVacancies.addObject("prevDate", prevDate);
        filteredVacancies.addObject("prevDateLink", prevDateLink);

        try {
            User user = userUtils.getCurrentUser(request);
            List<Vacancy> vacancies = recruiterService
                    .findFilteredVacanciesForRecruiter(user.getRecruiterId(), requestDate, searchText, showVacancies, showBids, showDeals);
            filteredVacancies.addObject("vacancies", vacancies);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return filteredVacancies;
    }

    public RecruiterService getRecruiterService() {
        return recruiterService;
    }

    public void setRecruiterService(final RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }

    public UserUtils getUserUtils() {
        return userUtils;
    }

    public void setUserUtils(final UserUtils userUtils) {
        this.userUtils = userUtils;
    }
}