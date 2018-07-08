package edu.metrostate.ics499.prim.service;

import edu.metrostate.ics499.prim.model.SocialNetworkRegistration;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Post;

import java.util.List;

public interface FacebookService {

    /**
     * Builds the Facebook Authorization URL for use with OAth2.
     * This URL is returned to the application so that the user can authorize access to our app.
     *
     * @return The authorization URL to pass to the client.
     */
    String buildAuthorizationUrl();

    /**
     *  Registers Facebook using the specified verification code.
     *
     * @param code the verification code received from the client request.
     */
    void registerFacebook(String code);

    /**
     * Refreshes the specified SocialNetworkRegistration token.
     *
     * @param socialNetworkRegistration the registration to refresh authorization token to for.
     */
    void refreshToken(SocialNetworkRegistration socialNetworkRegistration);

    /**
     * Returns the authorized Facebook object.
     *
     * @return the authorized Facebook object.
     */
    Facebook getFaceBook();

    /**
     * Returns a list of all supported post types from the authenticated accounts.
     *
     * @return a list of all supported post types from the authenticated accounts.
     */
    List<Post> getAllPostTypeItems();
}
