package edu.metrostate.ics499.prim.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class PrimAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Qualifier("messageSource")
    @Autowired
    private MessageSource messages;


    /**
     * Performs the redirect or forward to the {@code defaultFailureUrl} if set, otherwise
     * returns a 401 error code.
     * <p>
     * If redirecting or forwarding, {@code saveException} will be called to cache the
     * exception for use in the target view.
     *
     * @param request
     * @param response
     * @param exception
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = messages.getMessage("message.badCredentials", null, java.util.Locale.getDefault());

        if (exception.getMessage().contains("locked")) {
            errorMessage = messages.getMessage("auth.message.locked", null, java.util.Locale.getDefault());
        } else if (exception.getMessage().contains("disabled")) {
            errorMessage = messages.getMessage("auth.message.disabled", null, java.util.Locale.getDefault());
        }

        saveException(request, exception);
//        super.onAuthenticationFailure(request, response, exception);
        getRedirectStrategy().sendRedirect(request, response, "/login?error=" + HtmlUtils.htmlEscape(errorMessage));
    }
}