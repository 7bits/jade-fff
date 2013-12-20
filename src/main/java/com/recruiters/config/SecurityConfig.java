//package com.recruiters.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@EnableWebSecurity
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(final HttpSecurity http) throws Exception {
//        http
//                .formLogin()
//                    .loginPage("/")
//                    .defaultSuccessUrl("/")
//                    .failureUrl("/?error=true")
//                    .permitAll()
//                    .and()
//                .logout()
//                    .logoutUrl("/logout")
//                    .logoutSuccessUrl("/")
//                    .permitAll()
//                    .and()
//                .authorizeRequests()
//                    .antMatchers("/recruiter*").hasRole("ROLE_RECRUITER")
//                    .antMatchers("/employer*").hasRole("ROLE_EMPLOYER")
//                    .antMatchers("/").anonymous();
//    }
//
//    @Override
//    protected void configure(final AuthenticationManagerBuilder registry) throws Exception {
//        registry
//                .inMemoryAuthentication()
//                    .withUser("recruiter")
//                        .password("123123")
//                        .roles("ROLE_RECRUITER")
//                        .and()
//                    .withUser("employer")
//                .password("123123")
//                .roles("ROLE_EMPLOYER");
//    }
//
//    @Override
//    public void configure(final WebSecurity builder) throws Exception {
//        builder
//                .ignoring()
//                    .antMatchers("/resources/**");
//    }
//}