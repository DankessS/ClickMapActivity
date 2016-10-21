package com.academy.web.config;

import com.academy.cache.UserCache;
import com.academy.service.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Daniel Palonek on 2016-09-03.
 */
@Component
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    CacheLoader cacheLoader;

    @Autowired
    WebsiteService websiteService;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Override
    public void onAuthenticationSuccess(javax.servlet.http.HttpServletRequest req, HttpServletResponse rsp, Authentication auth) throws IOException, ServletException {
//        String url = req.getParameter("url");
//        if(url == null) {
//            redirectStrategy.sendRedirect(req,rsp,"/");
//            return;
//        }
        StringBuilder stringBuilder = new StringBuilder();
        for(GrantedAuthority grantedAuthority: auth.getAuthorities()) {
            if("ROLE_USER".equals(grantedAuthority.getAuthority()) || "ROLE_ADMIN".equals(grantedAuthority.getAuthority())) {
                stringBuilder.append("user/#");
                cacheLoader.load(auth.getName());
                break;
            }
        }
//        stringBuilder.append(url);
        redirectStrategy.sendRedirect(req,rsp,stringBuilder.toString());
    }


}
