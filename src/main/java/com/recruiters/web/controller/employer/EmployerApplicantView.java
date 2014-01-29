package com.recruiters.web.controller.employer;

import com.recruiters.model.Applicant;
import com.recruiters.model.User;
import com.recruiters.service.EmployerService;
import com.recruiters.service.NotAffiliatedException;
import com.recruiters.service.NotFoundException;
import com.recruiters.service.ServiceException;
import com.recruiters.web.controller.utils.UserUtils;
import com.recruiters.web.helper.UrlResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Applicant view and actions for employer
 */
@Controller
public class EmployerApplicantView {

    /** Employer Service provides all Employer related methods */
    @Autowired
    private EmployerService employerService = null;
    /** User utils for obtaining any session user information */
    @Autowired
    private UserUtils userUtils = null;
    /** Url Builder */
    @Autowired
    private UrlResolver urlResolver;

    /**
     * Show applicant details
     * @param applicantId    Id of applicant
     * @param request        Http request
     * @param response       Http response
     * @return model and view with applicant details,
     * Forbidden page if requested applicant is not related to any vacancy
     * of current employer,
     * Not Found page if there is no applicant with such id,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/employer-applicant-show/{applicantId}", method = RequestMethod.GET)
    public ModelAndView applicantShow(
            @PathVariable final Long applicantId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        ModelAndView showApplicant = new ModelAndView("employer/employer-applicant-show.jade");
        try {
            User user = userUtils.getCurrentUser(request);
            Applicant applicant = employerService.findApplicant(applicantId, user.getEmployerId());
            showApplicant.addObject("applicant", applicant);
        } catch (NotAffiliatedException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        } catch (NotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        return showApplicant;
    }

    /**
     * Approve applicant
     * @param applicantId    Id of applicant
     * @param request        Http request
     * @param response       Http response
     * @return redirects to list of vacancies currently in work if the action
     * was successful,
     * Forbidden page if this applicant is not related to any vacancy
     * of current employer,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/employer-applicant-show/apply/{applicantId}", method = RequestMethod.GET)
    public String applicantApply(
            @PathVariable final Long applicantId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        try {
            User user = userUtils.getCurrentUser(request);
            employerService.applyApplicant(applicantId, user.getEmployerId());
        } catch (NotAffiliatedException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
        Locale locale = RequestContextUtils.getLocale(request);

        return  urlResolver.buildRedirectUri("employer-progress-vacancies-list", locale);
    }


    /**
     * Decline applicant
     * @param applicantId    Id of applicant
     * @param request        Http request
     * @param response       Http response
     * @return redirects to list of vacancies currently in work if the action
     * was successful,
     * Forbidden page if this applicant is not related to any vacancy
     * of current employer,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/employer-applicant-show/decline/{applicantId}", method = RequestMethod.GET)
    public String applicantDecline(
            @PathVariable final Long applicantId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        try {
            User user = userUtils.getCurrentUser(request);
            employerService.declineApplicant(applicantId, user.getEmployerId());
            Applicant applicant = employerService.findApplicant(applicantId, user.getEmployerId());
            Locale locale = RequestContextUtils.getLocale(request);

            return urlResolver.buildRedirectUriLongParam(
                    "employer-progress-vacancy-show",
                    applicant.getDeal().getId(),
                    locale
            );
        } catch (NotAffiliatedException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        } catch (NotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    public EmployerService getEmployerService() {
        return employerService;
    }

    public void setEmployerService(final EmployerService employerService) {
        this.employerService = employerService;
    }
}
