package edu.metrostate.ics499.prim.component;

import edu.metrostate.ics499.prim.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureLockedEvent;
import org.springframework.stereotype.Component;

/**
 * The AuthFailureLockedListener class is called by Spring whenever there is an authentication failure
 * where the user account is locked.
 */
@Component
public class AuthFailureDisabledListener implements ApplicationListener<AuthenticationFailureLockedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(AuthFailureDisabledListener.class);

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
    public void onApplicationEvent(AuthenticationFailureLockedEvent event) {
        Object username = event.getAuthentication().getPrincipal();
        logger.info("Failed login using username [" + username + "] due to disabled account.");
    }
}
