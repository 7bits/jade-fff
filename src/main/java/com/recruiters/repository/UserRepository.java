package com.recruiters.repository;

import com.recruiters.model.User;
import com.recruiters.repository.exception.RepositoryException;
import com.recruiters.repository.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

/**
 * Repository implementing all methods related to
 * User Entity manipulations with MyBatis
 */
@Repository
public class UserRepository implements UserDetailsService {

    /** MyBatis User Mapper */
    @Autowired
    private UserMapper userMapper = null;

    /**
     * Find User and return User POJO instance by username
     * @param username    User username
     * @return User POJO instance
     * @throws com.recruiters.repository.exception.RepositoryException if input parameter is incorrect or there
     * were any technical issues
     */
    public User findByUsername(final String username) throws RepositoryException {
        if (username == null) {
            throw new RepositoryException("username is null");
        }
        try {

            return userMapper.findByUsername(username);
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Update user and return its instance
     * @param user    User POJO instance
     * @return User POJO instance if there were no any technical issues
     * @throws RepositoryException if input parameter is incorrect or there
     * were any technical issues
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

    /**
     * Obtain User Details by username
     * Required by Spring Security
     * @param username    User username
     * @return UserDetails type user details
     * @throws UsernameNotFoundException if username was not found or there
     * were any technical issues while obtaining it
     */
    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
        try {
            UserDetails userDetails = this.findByUsername(username);
            if (userDetails != null) {
                return userDetails;
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("User details cannot be obtained " +
                    "because of error: ", e);
        }
        throw new UsernameNotFoundException("There are no user details for this username");
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(final UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
