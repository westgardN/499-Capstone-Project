package edu.metrostate.ics499.prim.service;

import edu.metrostate.ics499.prim.model.SocialNetworkRegistration;
import edu.metrostate.ics499.prim.provider.InteractionProvider;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Post;

import java.util.List;

/**
 * The FacebookService provides an interface for registering with Facebook and
 * retrieving data from Facebook.
 */
public interface FacebookService extends InteractionProvider {

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
     * Returns true if a non-expired Facebook registration exists
     *
     * @return true if a non-expired Facebook registration exists
     */
    boolean isRegistered();

    /**
     * Returns the authorized Facebook object.
     *
     * @return the authorized Facebook object.
     */
    Facebook getFaceBook();

    /**
     * Returns a list of all supported post types from the specified accounts
     *
     * @param facebook the Facebook account to retrieve the data from.
     *
     * @return a list of all supported post types from the specified account.
     */
    List<Post> getAllPostTypeItems(Facebook facebook);

    /**
     * Returns a list of all supported post types from the authenticated accounts.
     *
     * @return a list of all supported post types from the authenticated accounts.
     */
    List<Post> getAllPostTypeItems();

    /**
     * Returns a list of all non-expired Facebook instances.
     */
    List<Facebook> getAllNonExpiredFacebooks();
}
