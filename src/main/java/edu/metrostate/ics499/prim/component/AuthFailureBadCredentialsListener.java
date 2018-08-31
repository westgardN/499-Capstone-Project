package edu.metrostate.ics499.prim.component;

import edu.metrostate.ics499.prim.model.User;
import edu.metrostate.ics499.prim.model.UserStatus;
import edu.metrostate.ics499.prim.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * The AuthFailureListener class is called by Spring whenever there is an authentication failure.
 * This class then updates the user the failure was for and potentially locks the user account if too
 * many failures.
 */
@Component
public class AuthFailureBadCredentialsListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    private static final Logger logger = LoggerFactory.getLogger(AuthFailureBadCredentialsListener.class);

    @Autowired
    private UserService userService;

    /**
     * Called when an authentication failure occurs due to bad credentials.
     *
     * We use this to increment the failed logins count for the user and if need be, lock the account.
     *
     * @param event the event to respond to
     */
    @Override
    @Transactional
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        Object username = event.getAuthentication().getPrincipal();
        logger.info("Failed login using username [" + username + "]");

        // Retrieve the persistent User that tried to log in.
        User user = userService.findBySsoId(username.toString());

        logger.info("User : {}", user);

        if (user == null) {
            logger.info("Username not found");
        } else {
            userService.failLogin(user);

            logger.info("User : {}", user);
        }
    }
}
