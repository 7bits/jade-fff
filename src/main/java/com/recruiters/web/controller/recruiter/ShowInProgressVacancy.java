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

    /** User utils for obtaining any session user information */
    @Autowired
    private UserUtils userUtils = null;
    /** Recruiter Service provides all Recruiter related methods */
    @Autowired
    private RecruiterService recruiterService = null;

    /**
     * Controller for R6 "Show in progress vacancy"
     * Displays deal for certain vacancy
     * @param dealId      Id of deal to display
     * @param request     Http Request
     * @param response    Http Response
     * @return model and view with deal, Forbidden page if deal requested
     * is not related to current recruiter, Internal Server Error page if
     * something is wrong with obtaining data due to technical or any
     * other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
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

    public UserUtils getUserUtils() {
        return userUtils;
    }

    public void setUserUtils(final UserUtils userUtils) {
        this.userUtils = userUtils;
    }
}
