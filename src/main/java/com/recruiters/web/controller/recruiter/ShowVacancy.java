package com.recruiters.web.controller.recruiter;


import com.recruiters.model.Bid;
import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.service.*;
import com.recruiters.service.exception.NotAffiliatedException;
import com.recruiters.service.exception.NotFoundException;
import com.recruiters.service.exception.ServiceException;
import com.recruiters.web.controller.utils.UserUtils;
import com.recruiters.web.helper.UrlResolver;
import com.recruiters.web.service.JsonService;
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
import java.util.Map;


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
    /** Url Builder */
    @Autowired
    private UrlResolver urlResolver;
    /** Json converter service */
    @Autowired
    private JsonService jsonService;

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
    @RequestMapping(value = "/recruiter-show-vacancy/{vacancyId}", method = RequestMethod.GET)
    public ModelAndView showVacancyById(
            @PathVariable final Long vacancyId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        ModelAndView showVacancy = new ModelAndView("recruiter/recruiter-vacancy.jade");
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
    @RequestMapping(value = "/recruiter-show-vacancy/{vacancyId}", method = RequestMethod.POST)
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
            return null;
        } catch (NotAffiliatedException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }
        Locale locale = RequestContextUtils.getLocale(request);

        return urlResolver.buildRedirectUri("recruiter-find-new-vacancies", locale);
    }

    /**
     * Shows bid in json format
     * @param bidId        Bid id
     * @param request      Http Request
     * @param response     Http Response
     * @return json formatted bid information, Internal Server Error
     * page if something is wrong with obtaining data due to technical or any
     * other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/recruiter-bid-show.json", method = RequestMethod.GET)
    public Map<String, Map<String,String>> showBidVacancyById(
            @RequestParam(value = "bidId", required = true) final Long bidId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        Bid bid;
        try {
            User user = userUtils.getCurrentUser(request);
            bid = recruiterService.findActiveBid(bidId, user.getRecruiterId());
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
        Locale locale = RequestContextUtils.getLocale(request);

        return jsonService.recruiterShowBid(bid, locale);
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
