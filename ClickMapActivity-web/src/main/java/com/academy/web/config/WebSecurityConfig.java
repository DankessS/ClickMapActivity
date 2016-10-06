package com.academy.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by Daniel Palonek on 2016-09-03.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")        
    UserDetailsService userDetailsService;

    @Autowired
    LoginAuthenticationSuccessHandler loginSuccessHandler;

    @Autowired
    LogoutAuthenticationSuccessHandler logoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/user/**")
                    .access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .successHandler(loginSuccessHandler)
                    .failureUrl(RedirectUrls.ERROR_LOGIN)
                    .and()
                .logout()
                    .logoutSuccessHandler(logoutSuccessHandler)
                    .logoutSuccessUrl("/")
                .and().csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {
           @Override
           public String encode(CharSequence rawPassword) {
               return rawPassword.toString();
           }

           @Override
           public boolean matches(CharSequence rawPassword, String encodedPassword) {
               return encodedPassword.contentEquals(rawPassword);
           }
       });
    }
}
