package com.recruiters.service;

import com.recruiters.model.User;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class EmployerService {

    public User findUserByUsername(final String username) {
        return new User(2L, "employer", "123123");
    }
}
