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

    public User findUserByUserName(final String username) {
        //TODO
        return new User(1L, "recruiter", "123123");
    }
}
