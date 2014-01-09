package com.recruiters.repository;

import com.recruiters.model.User;
import com.recruiters.repository.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;

/**
 * Repository for working with recruiter
 */
@Repository
public class UserRepository implements UserDetailsService {

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
     * Returns User POJO instance by username
     * @param username Username
     * @return User POJO instance
     */
    public User findByUsername(final String username) {

        return this.getUserMapper().findByUsername(username);
    }

    /**
     * Updating user
     * @param user    User POJO
     * @return true if update not fails, otherwise false
     */
    public Boolean update(final User user) {
        try {
            userMapper.update(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        User user = this.findUserByUsername(username);

        return user;
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(final UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
