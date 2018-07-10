package edu.metrostate.ics499.prim.component;

import edu.metrostate.ics499.prim.model.User;
import edu.metrostate.ics499.prim.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * The AuthFailureListener class is called by Spring whenever there is an authentication failure.
 * This class then updates the user the failure was for and potentially locks the user account if too
 * many failures.
 */
@Component
public class AuthFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Called when an authentication failure occurs due to bad credentials.
     *
     * We use this to increment the failed logins count for the user and if need be, lock the account.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        Object username = event.getAuthentication().getPrincipal();
        logger.info("Failed login using username [" + username + "]");

        // Retrieve the persistent User that trieed to log in.
        User user = userService.findBySsoId(username.toString());

        logger.info("User : {}", user);

        if (user == null) {
            logger.info("Username not found");
        } else {
            // Update the current user's last logged in time, ip address, and reset failed logins to 0
            user.setLastVisitedOn(new Date());
            user.setFailedLogins(user.getFailedLogins() + 1);
            logger.info("User : {}", user);
        }
    }
}
