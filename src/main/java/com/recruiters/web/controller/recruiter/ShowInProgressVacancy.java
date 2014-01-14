package com.recruiters.web.controller.recruiter;

import com.recruiters.model.Deal;
import com.recruiters.model.User;
import com.recruiters.service.*;
import com.recruiters.service.SecurityException;
import com.recruiters.web.controller.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller Class for R6 "Show in progress vacancy"
 */
@Controller
public class ShowInProgressVacancy {

    @Autowired
    private UserUtils userUtils = null;
    @Autowired
    private RecruiterService recruiterService = null;

    @RequestMapping(value = "recruiter-show-in-progress-vacancy/{dealId}", method = RequestMethod.GET)
    public ModelAndView showInProgressVacancy(
            @PathVariable final Long dealId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        ModelAndView vacancyInProgress = new ModelAndView("recruiter/recruiter-show-in-progress-vacancy.jade");
        try {
            User user = userUtils.getCurrentUser(request);
            Deal deal = recruiterService.findDealForRecruiter(dealId, user.getRecruiterId());
            vacancyInProgress.addObject("deal", deal);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (SecurityException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }

        return vacancyInProgress;
    }

    public RecruiterService getRecruiterService() {
        return recruiterService;
    }

    public void setRecruiterService(final RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }
}
