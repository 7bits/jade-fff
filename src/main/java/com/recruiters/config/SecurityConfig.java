//package com.recruiters.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@EnableWebSecurity
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    protected void registerAuthentication(final AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("recruiter").password("123123123").roles("RECRUITER").and()
//                .withUser("employer").password("123123123").roles("EMPLOYER");
//
//    }
//
//    @Override
//    protected void configure(final HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/recruiter*").hasRole("RECRUITER")
//                .antMatchers("/employer*").hasRole("EMPLOYER")
//                .anyRequest().anonymous();
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//}