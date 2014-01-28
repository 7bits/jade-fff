package com.recruiters.config;

import com.recruiters.repository.UserRepository;
import com.recruiters.web.utils.SuccessLoginHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring Security Configuration
 */
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    /**
     * Setup success login handler
     * @return success login handler
     */
    @Bean
    public SuccessLoginHandler successLoginHandler() {

        return new SuccessLoginHandler(userRepository);
    }

    /**
     * Configure spring security
     * @param http    Http Security
     * @throws Exception if fails
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .formLogin()
                    .loginPage("/")
                    .loginProcessingUrl("/**/j_spring_security_check")
                    .usernameParameter("j_username")
                    .passwordParameter("j_password")
                    .defaultSuccessUrl("/")
                    .failureUrl("/?error=true")
                    .successHandler(successLoginHandler())
                    .permitAll()
                    .and()
                .logout()
                    .logoutUrl("/**/j_spring_security_logout")
                    .logoutSuccessUrl("/")
                    .permitAll()
                    .and()
                .authorizeRequests()
                .antMatchers("/*/recruiter*/**").hasRole("RECRUITER")
                .antMatchers("/*/employer*/**").hasRole("EMPLOYER")
                .antMatchers("/*/*/recruiter*/**").hasRole("RECRUITER")
                .antMatchers("/*/*/employer*/**").hasRole("EMPLOYER")
                .antMatchers("/*/j_*").permitAll()
                .antMatchers("/*/*/j_*").permitAll()
                    .antMatchers("/*").permitAll()
                    .anyRequest().anonymous();

    }

    /**
     * Setup authentication manager
     * @return authentication manager
     * @throws Exception if fails
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Configure authentication manager
     * @param auth    Authentication manager
     * @throws Exception if fails
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userRepository);
    }

    /**
     * Configure Web Security
     * @param builder    Web security builder
     * @throws Exception if fails
     */
    @Override
    public void configure(final WebSecurity builder) throws Exception {
        builder
                .ignoring()
                    .antMatchers("/resources/**");
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}