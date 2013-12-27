package com.recruiters.repository;

import com.recruiters.model.User;
import com.recruiters.repository.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;

/**
 * Repository for working with recruiter
 */
@Repository
public class UserRepository {

    @Autowired
    private UserMapper userMapper = null;

    /**
     * Method must return user model by credentials of request
     * @param request
     * @return
     */
    public User getCurrentUser(final HttpServletRequest request) {

        return this.findUserByUserName(request.getUserPrincipal().getName());
    }

    /**
     * Method must return user by given id
     * @param id
     * @return
     */
    public User getById(final Long id) {
        return this.getUserMapper().getById(id);
    }

    /**
     * Returns User POJO instance by username
     * @param username Username
     * @return User POJO instance
     */
    public User findUserByUserName(final String username) {

        return this.getUserMapper().findUserByUserName(username);
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(final UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
