package com.recruiters.config;

import com.recruiters.repository.UserRepository;
import com.recruiters.repository.mapper.UserMapper;
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

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin()
                    .loginPage("/")
                    .loginProcessingUrl("/j_spring_security_check")
                    .usernameParameter("j_username")
                    .passwordParameter("j_password")
                    .defaultSuccessUrl("/")
                    .failureUrl("/?error=true")
                    .permitAll()
                    .and()
                .logout()
                    .logoutUrl("/j_spring_security_logout")
                    .logoutSuccessUrl("/")
                    .permitAll()
                    .and()
                .authorizeRequests()
                    .antMatchers("/recruiter*").hasRole("RECRUITER")
                    .antMatchers("/employer*").hasRole("EMPLOYER")
                    .anyRequest().anonymous();
    }

//    @Override
//    protected void configure(final AuthenticationManagerBuilder registry) throws Exception {
//        registry
//                .authenticationProvider();
//    }

//    @Override
//    protected void configure(final AuthenticationManagerBuilder registry) throws Exception {
//        registry
//                .inMemoryAuthentication()
//                    .withUser("recruiter")
//                        .password("123123")
//                        .roles("RECRUITER")
//                        .and()
//                    .withUser("employer")
//                        .password("123123")
//                        .roles("EMPLOYER");
//    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userRepository);
    }

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