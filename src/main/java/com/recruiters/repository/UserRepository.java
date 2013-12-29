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
     * @param username
     * @return
     */
    public User findUserByUsername(final String username) {

        return this.findByUsername(username);
    }

    /**
     * Method must return user by given id
     * @param id
     * @return
     */
    public User findById(final Long id) {
        return this.getUserMapper().findById(id);
    }

    /**
     * Returns User POJO instance by username
     * @param username Username
     * @return User POJO instance
     */
    public User findByUsername(final String username) {

        return this.getUserMapper().findByUsername(username);
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(final UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
