package com.recruiters.web.controller.recruiter;

import com.recruiters.model.Deal;
import com.recruiters.model.User;
import com.recruiters.service.exception.NotAffiliatedException;
import com.recruiters.service.exception.NotFoundException;
import com.recruiters.service.RecruiterService;
import com.recruiters.service.exception.ServiceException;
import com.recruiters.web.controller.utils.UserUtils;
import com.recruiters.web.helper.UrlResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Show progress of vacancy (deal) for recruiter and all related actions
 */
@Controller
public class ShowInProgressVacancy {

    /** User utils for obtaining any session user information */
    @Autowired
    private UserUtils userUtils = null;
    /** Recruiter Service provides all Recruiter related methods */
    @Autowired
    private RecruiterService recruiterService = null;
    /** Url Builder */
    @Autowired
    private UrlResolver urlResolver;

    /**
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
    @RequestMapping(value = "/recruiter-show-in-progress-vacancy/{dealId}", method = RequestMethod.GET)
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
            return null;
        } catch (NotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        } catch (NotAffiliatedException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }

        return vacancyInProgress;
    }

    /**
     * Leave feedback for deal ended
     * @param feedback    Feedback
     * @param dealId      Id of deal
     * @param request     Http request
     * @param response    Http response
     * @return redirect to "vacancies list" page if everything goes fine,
     * Forbidden page if this deal does not belong to this recruiter,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/recruiter-deal-leave-feedback", method = RequestMethod.POST)
    public String leaveFeedback(
            @RequestParam(value = "feedback", required = false) final String feedback,
            @RequestParam(value = "id", required = true) final Long dealId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        try {
            User user = userUtils.getCurrentUser(request);
            recruiterService.leaveFeedback(dealId, feedback, user.getRecruiterId());
        } catch (NotAffiliatedException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
        Locale locale = RequestContextUtils.getLocale(request);

        return  urlResolver.buildRedirectUri("recruiter-active-deals", locale);
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
