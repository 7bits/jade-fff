package com.recruiters.repository;

import com.recruiters.model.User;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;

/**
 * Repository for working with recruiter
 */
@Repository
public class UserRepository {

    /**
     * Method must return user model by credentials of request
     * @param request
     * @return
     */
    public User getCurrentUser(final HttpServletRequest request) {

        return this.findUserByUserName(request.getUserPrincipal().getName());
    }

    /**
     * Returns User POJO instance by username
     * @param username    Username
     * @return User POJO instance
     */
    public User findUserByUserName(final String username) {

        if (username.equals("recruiter")) {
            return new User(1L, "recruiter", "123123");
        } else if (username.equals("employer")) {
            return new User(2L, "employer", "123123");
        } else {
            return null;
        }
    }
}
