package com.academy.web.config;

import com.academy.cache.UserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Daniel Palonek on 2016-09-10.
 */
@Component
public class LogoutAuthenticationSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    UserCache cache;

    @Override
    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse rsp, Authentication auth)
            throws IOException, ServletException {
        cache.removeUserWebsites();
        cache.removeLoggedUsername();
    }
}
