package com.recruiters.web.controller.recruiter;


import com.recruiters.model.Bid;
import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.service.*;
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
 * Shows vacancy for recruiter
 */
@Controller
public class ShowVacancy {

    /** User utils for obtaining any session user information */
    @Autowired
    private UserUtils userUtils = null;
    /** Recruiter Service provides all Recruiter related methods */
    @Autowired
    private RecruiterService recruiterService = null;

    /**
     * Displays certain vacancy with method GET
     * @param vacancyId    Vacancy id
     * @param request      Http Request
     * @param response     Http Response
     * @return model and view with vacancy, Internal Server Error page if
     * something is wrong with obtaining data due to technical or any
     * other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/{locale}/recruiter-show-vacancy/{vacancyId}", method = RequestMethod.GET)
    public ModelAndView showVacancyById(
            @PathVariable final Long vacancyId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        ModelAndView showVacancy = new ModelAndView("recruiter/recruiter-show-vacancy.jade");
        try {
            User user = userUtils.getCurrentUser(request);
            Vacancy vacancy = recruiterService.findVacancy(vacancyId, user.getRecruiterId());
            showVacancy.addObject("vacancy", vacancy);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        } catch (NotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        return showVacancy;
    }

    /**
     * Accepts apply to vacancy with method POST
     * @param vacancyId    Vacancy id
     * @param locale       Locale
     * @param message      Custom message assigned to bid, no required
     * @param request      Http Request
     * @param response     Http Response
     * @return redirects to page with vacancies available for bid, Internal
     * Server Error page if something is wrong with obtaining data due to
     * technical or any other reasons,
     * Forbidden if this vacancy have deal already
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/{locale}/recruiter-show-vacancy/{vacancyId}", method = RequestMethod.POST)
    public String applyToVacancy(
            @PathVariable final Long vacancyId,
            @PathVariable final String locale,
            @RequestParam(value = "message", required = false) final String message,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws  Exception {
        try {
            User user = userUtils.getCurrentUser(request);
            recruiterService.applyRecruiterToVacancy(user.getRecruiterId(), vacancyId, message);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        } catch (NotAffiliatedException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }

        return "redirect:/" + locale + "/recruiter-find-new-vacancies";
    }

    /**
     * Displays certain vacancy with bid on it
     * @param bidId        Bd id
     * @param request      Http Request
     * @param response     Http Response
     * @return model and view with vacancy and bid status, Internal Server Error
     * page if something is wrong with obtaining data due to technical or any
     * other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/{locale}/recruiter-show-bid-vacancy/{bidId}", method = RequestMethod.GET)
    public ModelAndView showBidVacancyById(
            @PathVariable final Long bidId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        ModelAndView showBidVacancy = new ModelAndView("recruiter/recruiter-show-bid-vacancy.jade");
        try {
            User user = userUtils.getCurrentUser(request);
            Bid bid = recruiterService.findActiveBid(bidId, user.getRecruiterId());
            showBidVacancy.addObject("bid", bid);
        } catch (NotAffiliatedException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        } catch (NotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }

        return showBidVacancy;
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
