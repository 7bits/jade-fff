package com.recruiters.web.controller.utils;

import com.recruiters.model.User;
import com.recruiters.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Service Class for Employer
 */
@Service
public class UserUtils {

    @Autowired
    private UserRepository userRepository = null;

    /**
     * Detached user getter, should be combined with equal getter
     * in RecruiterService Class in future
     * @param request Http request
     * @return User POJO instance
     */
    public Long getCurrentUserId(final HttpServletRequest request) {

        Long userId = null;
        /*TODO: Remove DB usage, put userId to the session*/
        User user = userRepository.findUserByUsername(request.getUserPrincipal().getName());
        if (user != null) {
            userId = user.getId();
        }
        return userId;
    }
}
