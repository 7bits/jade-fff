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
    public User update(final User user) throws RepositoryException {
        if (user == null) {
            throw new RepositoryException("user is null");
        }
        try {

            userMapper.update(user);
            return user;
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

       return this.findByUsername(username);
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(final UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
