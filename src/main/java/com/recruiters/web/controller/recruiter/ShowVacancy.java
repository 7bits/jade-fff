package com.recruiters.web.controller.recruiter;


import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.service.RecruiterService;
import com.recruiters.service.ServiceException;
import com.recruiters.web.controller.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Controller Class for R2 "Show Vacancy"
 */
@Controller
public class ShowVacancy {

    @Autowired
    private UserUtils userUtils = null;
    @Autowired
    private RecruiterService recruiterService = null;

    /**
     * Controller for R2 "Show vacancy"
     * @return model and view with one vacancy
     */
    @RequestMapping(value = "recruiter-show-vacancy/{vacancyId}", method = RequestMethod.GET)
    public ModelAndView showVacancyById(
            @PathVariable final Long vacancyId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        ModelAndView showVacancy = new ModelAndView("recruiter/recruiter-show-vacancy.jade");
        try {
            Vacancy vacancy = recruiterService.findVacancy(vacancyId);
            showVacancy.addObject("vacancy", vacancy);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return showVacancy;
    }

    /**
     * Controller for R2 "Show vacancy"
     * @return model and view with one vacancy
     */


    @RequestMapping(value = "recruiter-show-vacancy/{vacancyId}", method = RequestMethod.POST)
    public String applyToVacancy(
            @PathVariable final Long vacancyId,
            @RequestParam(value = "message", required = false) final String message,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws  Exception {
        try {
            User user = userUtils.getCurrentUser(request);
            recruiterService.applyRecruiterToVacancy(user.getRecruiterId(), vacancyId, message);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return "redirect:/recruiter-find-new-vacancies";
    }

    /**
     * Controller for "Show vacancy from active bids"
     * @return model and view with one vacancy
     */
    @RequestMapping(value = "recruiter-show-bid-vacancy/{vacancyId}", method = RequestMethod.GET)
    public ModelAndView showBidVacancyById(
            @PathVariable final Long vacancyId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        ModelAndView showVacancy = new ModelAndView("recruiter/recruiter-show-bid-vacancy.jade");
        try {
            User user = userUtils.getCurrentUser(request);
            Vacancy vacancy = recruiterService.findVacancyWithActiveBid(vacancyId, user.getRecruiterId());
            showVacancy.addObject("vacancy", vacancy);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return showVacancy;
    }


    public RecruiterService getRecruiterService() {
        return recruiterService;
    }

    public void setRecruiterService(final RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }
}
