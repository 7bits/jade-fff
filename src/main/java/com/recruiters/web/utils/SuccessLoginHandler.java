package com.recruiters.web.utils;

import com.recruiters.repository.exception.RepositoryException;
import com.recruiters.repository.UserRepository;
import com.recruiters.web.helper.UrlResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * Custom login controller
 */
public class SuccessLoginHandler implements AuthenticationSuccessHandler {

    private UserRepository userRepository = null;
    @Autowired
    private UrlResolver urlResolver;
    @Autowired
    private AttributeLocaleResolver attributeLocaleResolver;


    public SuccessLoginHandler(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Login Handler, saves user to session
     * @param request           Http Request
     * @param response          Http Response
     * @param authentication    Authentication
     * @throws IOException if user information cannot be obtained
     * @throws ServletException if servlet fails
     */
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
        Locale locale = attributeLocaleResolver.resolveLocale(request);
        response.sendRedirect(urlResolver.buildFullUri("", locale));
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
