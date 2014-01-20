package com.recruiters.web.controller.utils;

import com.recruiters.model.User;
import com.recruiters.repository.UserRepository;
import com.recruiters.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * User-related session utilities
 */
@Service
public class UserUtils {

    @Autowired
    private UserRepository userRepository = null;

    /**
     * Find user by session info and return POJO with user info
     * @param request Http Request
     * @return User POJO instance
     * @throws ServiceException is there are no user assigned to session
     */
    public User getCurrentUser(final HttpServletRequest request) throws ServiceException {
        User user =  (User) request.getSession().getAttribute("currentUser");
        if (user == null) {
            throw new ServiceException("User is null");
        }

        return user;
    }
}
