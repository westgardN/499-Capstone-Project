package edu.metrostate.ics499.prim.component;

import edu.metrostate.ics499.prim.model.User;
import edu.metrostate.ics499.prim.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * The AuthenticationSuccessHandlerImpl class is used by Spring after a user has successfully authenticated
 * with the system.
 */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional(readOnly = false)
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        //set our response to OK status
        response.setStatus(HttpServletResponse.SC_OK);

        boolean admin = false;

        // Retrieve the persistent User that is logged in.
        User user = userService.findBySsoId(authentication.getName());

        logger.info("User : {}", user);

        if (user == null) {
            logger.info("User not found");
            throw new UsernameNotFoundException("Username not found");
        }

        // Update the current user's last logged in time, ip address, and reset failed logins to 0
        userService.successLogin(user);

        // Redirect to the home endpoint.
        response.sendRedirect("/");
    }}
