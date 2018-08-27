package edu.metrostate.ics499.prim.component;

import java.util.UUID;
import edu.metrostate.ics499.prim.event.OnNewUserRegistrationCompleteEvent;
import edu.metrostate.ics499.prim.model.SecurityToken;
import edu.metrostate.ics499.prim.model.User;
import edu.metrostate.ics499.prim.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class NewUserRegistrationCompleteListener implements ApplicationListener<OnNewUserRegistrationCompleteEvent> {
    private static final Logger logger = LoggerFactory.getLogger(NewUserRegistrationCompleteListener.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment environment;

    /**
     * Responds to the registration of a new user.
     *
     * @param event the event to respond to
     */
    @Override
    @Transactional
    public void onApplicationEvent(OnNewUserRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(final OnNewUserRegistrationCompleteEvent event) {
        final User user = event.getUser();
        final SecurityToken token = userService.createSecurityToken(user);

        final SimpleMailMessage email = constructEmailMessage(event, user, token.getToken());
        javaMailSender.send(email);
        logger.info("New user registration e-mail has been sent.");
    }

    private final SimpleMailMessage constructEmailMessage(final OnNewUserRegistrationCompleteEvent event, final User user, final String token) {
        final String recipientAddress = user.getEmail();
        final String subject = "Registration Confirmation";
        final String confirmationUrl = event.getAppUrl() + "/user/registrationConfirm?token=" + token;
        final String message = messageSource.getMessage("message.regSuccessEmail", null, event.getLocale());
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(environment.getProperty("support.email"));
        return email;
    }
}
