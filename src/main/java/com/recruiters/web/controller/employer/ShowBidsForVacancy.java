package com.recruiters.web.controller.employer;

import com.recruiters.model.Bid;
import com.recruiters.model.Employer;
import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.service.EmployerService;
import com.recruiters.service.ServiceSecurityException;
import com.recruiters.service.ServiceTechnicalException;
import com.recruiters.web.controller.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Controller Class showing bids for exact vacancy
 */
@Controller
public class ShowBidsForVacancy {

    @Autowired
    private EmployerService employerService = null;
    @Autowired
    private UserUtils userUtils = null;

    /**
     * Controller showing bids for exact vacancy
     * @param vacancyId  Id of vacancy
     * @param request    Http request
     * @return model and view with data
     */
    @RequestMapping(value = "employer-show-recruiter-bids/{vacancyId}")
    public ModelAndView employerVacanciesBids(
            @PathVariable final Long vacancyId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {

        ModelAndView recruiterBids = new ModelAndView("employer/employer-show-recruiter-bids.jade");
        try {
            User user = userUtils.getCurrentUser(request);
            List<Bid> bids = employerService.findBidsForVacancy(vacancyId, user.getEmployerId());
            Vacancy vacancy = employerService.findVacancy(vacancyId, user.getEmployerId());
            recruiterBids.addObject("bids", bids);
            recruiterBids.addObject("vacancy", vacancy);
        } catch (ServiceTechnicalException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ServiceSecurityException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return recruiterBids;
    }

    public EmployerService getEmployerService() {
        return employerService;
    }

    public void setEmployerService(final EmployerService employerService) {
        this.employerService = employerService;
    }
}
