package com.recruiters.web.controller.employer;

import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.service.*;
import com.recruiters.web.controller.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Show list of recruiter applications
 */
@Controller
public class RecruiterSearch {

    @Autowired
    private EmployerService employerService = null;
    @Autowired
    private UserUtils userUtils = null;

    /**
     * Show vacancies with recruiter offers (bids)
     * @param request     Http Request
     * @param response    Http Response
     * @return model and view with list of vacancies with bid count for each,
     * Internal Server Error page if something is wrong with obtaining data
     * due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/employer-recruiter-search")
    public ModelAndView employerVacanciesBids(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {

        ModelAndView vacanciesBids = new ModelAndView("employer/employer-recruiter-search");
        try {
            User user = userUtils.getCurrentUser(request);
            List<Vacancy> vacancies = employerService.findVacanciesForEmployer(user.getEmployerId());
            vacanciesBids.addObject("vacancies", vacancies);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }

        return vacanciesBids;
    }

    public EmployerService getEmployerService() {
        return employerService;
    }

    public void setEmployerService(final EmployerService employerService) {
        this.employerService = employerService;
    }
}
