package com.recruiters.web.controller.recruiter;

import com.recruiters.model.Deal;
import com.recruiters.model.User;
import com.recruiters.service.RecruiterService;
import com.recruiters.service.ServiceException;
import com.recruiters.web.controller.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Controller Class for "In progress vacancies"
 */
@Controller
public class RecruiterDeals {

    @Autowired
    private UserUtils userUtils = null;
    @Autowired
    private RecruiterService recruiterService = null;
    /**
     * Controller for "in progress vacancies"
     * @return model and view with list of active vacancies
     */
    @RequestMapping(value = "recruiter-active-deals", method = RequestMethod.GET)
    public ModelAndView showMyVacancies(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws  Exception {
        ModelAndView activeDeals = new ModelAndView("recruiter/recruiter-active-deals.jade");
        try {
            User user = userUtils.getCurrentUser(request);
            List<Deal> deals = recruiterService.findActiveDealsForRecruiter(user.getRecruiterId());
            activeDeals.addObject("deals", deals);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return activeDeals;
    }

    public RecruiterService getRecruiterService() {
        return recruiterService;
    }

    public void setRecruiterService(final RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }
}
