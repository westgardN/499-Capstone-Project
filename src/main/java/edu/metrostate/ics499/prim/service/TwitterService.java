package edu.metrostate.ics499.prim.service;

import edu.metrostate.ics499.prim.model.SocialNetworkRegistration;
import edu.metrostate.ics499.prim.provider.InteractionProvider;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.Tweet;

import java.util.List;

/**
 * The TwitterService provides an interface for registering with Twitter and
 * retrieving data from Twitter.
 */
public interface TwitterService extends InteractionProvider {

    /**
     * Builds the Twitter Authorization URL for use with OAth2.
     * This URL is returned to the application so that the user can authorize access to our app.
     *
     * @return The authorization URL to pass to the client.
     */
    String buildAuthorizationUrl();

    /**
     *  Registers Twitter using the specified token and verification code.
     *
     * @param oauthToken the token received from the client request.
     * @param oauthVerifier the verification code from the client request.
     */
    void registerTwitter(String oauthToken, String oauthVerifier);

    /**
     * Refreshes the specified SocialNetworkRegistration token.
     *
     * @param socialNetworkRegistration the registration to refresh authorization token to for.
     */
    void refreshToken(SocialNetworkRegistration socialNetworkRegistration);

    /**
     * Returns true if a non-expired Twitter registration exists
     *
     * @return true if a non-expired Twitter registration exists
     */
    boolean isRegistered();

    /**
     * Returns the authorized Twitter object.
     *
     * @return the authorized Twitter object.
     */
    Twitter getTwitter();

    /**
     * Returns a list of all supported post types from the specified accounts
     *
     * @param twitter the Twitter account to retrieve the data from.
     *
     * @return a list of all supported post types from the specified account.
     */
    List<Tweet> getAllTweetTypeItems(Twitter twitter);

    /**
     * Returns a list of all supported post types from the authenticated accounts.
     *
     * @return a list of all supported post types from the authenticated accounts.
     */
    List<Tweet> getAllTweetTypeItems();

    /**
     * Returns a list of all supported post types from the specified accounts
     *
     * @param twitter the Twitter account to retrieve the data from.
     *
     * @return a list of all supported post types from the specified account.
     */
    List<Tweet> getAllTweetItems(Twitter twitter);

    /**
     * Returns a list of all supported post types from the authenticated accounts.
     *
     * @return a list of all supported post types from the authenticated accounts.
     */
    List<Tweet> getAllTweetItems();

    /**
     * Returns a list of all supported post types from the specified accounts
     *
     * @param twitter the Twitter account to retrieve the data from.
     *
     * @return a list of all supported post types from the specified account.
     */
    List<Tweet> getAllRetweetItems(Twitter twitter);

    /**
     * Returns a list of all supported post types from the authenticated accounts.
     *
     * @return a list of all supported post types from the authenticated accounts.
     */
    List<Tweet> getAllRetweetItems();

    /**
     * Returns a list of all supported post types from the specified accounts
     *
     * @param twitter the Twitter account to retrieve the data from.
     *
     * @return a list of all supported post types from the specified account.
     */
    List<Tweet> getAllMentionItems(Twitter twitter);

    /**
     * Returns a list of all supported post types from the authenticated accounts.
     *
     * @return a list of all supported post types from the authenticated accounts.
     */
    List<Tweet> getAllMentionItems();

    /**
     * Returns a list of all non-expired Twitter instances.
     */
    List<Twitter> getAllNonExpiredTwitters();
}
