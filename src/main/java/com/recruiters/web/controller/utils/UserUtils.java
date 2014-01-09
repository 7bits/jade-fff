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
     * Finds user by session info and returns its id
     * @param request Http request
     * @return User id
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


    /**
     * Finds user by session info
     * @param request Http request
     * @return User POJO instance
     */
    public User getCurrentUser(final HttpServletRequest request) {

        Long userId = null;
        /*TODO: Remove DB usage, put user to the session*/
        User user = userRepository.findUserByUsername(request.getUserPrincipal().getName());
        if (user != null) {
            return user;
        }
        return null;
    }
}
