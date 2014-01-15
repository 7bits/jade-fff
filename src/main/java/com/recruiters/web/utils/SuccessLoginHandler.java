package com.recruiters.web.utils;

import com.recruiters.repository.RepositoryException;
import com.recruiters.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * custom login controller
 */
public class SuccessLoginHandler implements AuthenticationSuccessHandler {

    private UserRepository userRepository = null;

    public SuccessLoginHandler(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Authentication authentication
    ) throws IOException, ServletException {
        try {
            request.getSession().setAttribute(
                    "currentUser",
                    userRepository.findByUsername(authentication.getName())
            );
        } catch (RepositoryException e) {
            throw new IOException("Cannot obtain user information: ", e);
        }
        response.sendRedirect(request.getContextPath() + "/");
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
