package edu.metrostate.ics499.prim.service;

import edu.metrostate.ics499.prim.model.SocialNetwork;
import edu.metrostate.ics499.prim.model.SocialNetworkRegistration;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.oauth2.AccessGrant;

import java.util.List;

/**
 * The SocialNetworkRegistrationService provides an interface for easily working with SocialNetworkRegistrations.
 */
public interface SocialNetworkRegistrationService {

    /**
     * Returns a persistent SocialNetworkRegistration object identified by the specified id.
     * If no SocialNetworkRegistration with that id exists, null is returned.
     *
     * @param id The Id of the SocialNetworkRegistration to retrieve.
     *
     * @return a persistent SocialNetworkRegistration object identified by the specified id.
     * If no SocialNetworkRegistration with that id exists, null is returned.
     */
    SocialNetworkRegistration findById(int id);

    /**
     * Returns a List of persistent SocialNetworkRegistrations for the specified Social Network.
     * If no SocialNetworkRegistrations exist, an empty List is returned.
     *
     * @param socialNetwork The Social Network to retrieve a list of SocialNetworkRegistrations for.
     *
     * @return a List of persistent SocialNetworkRegistrations for the specified Social Network.
     * If no SocialNetworkRegistrations exist, an empty List is returned.
     */
    List<SocialNetworkRegistration> findBySocialNetwork(SocialNetwork socialNetwork);

    /**
     * Returns a List of persistent SocialNetworkRegistrations for the specified Social Network.
     * The registrations are have not expired. If no SocialNetworkRegistrations exist, an empty List is returned.
     *
     * @param socialNetwork The Social Network to retrieve a list of non-expired SocialNetworkRegistrations for.
     *
     * @return a List of persistent SocialNetworkRegistrations for the specified Social Network.
     * The registrations are have not expired. If no SocialNetworkRegistrations exist, an empty List is returned.
     */
    List<SocialNetworkRegistration> findNonExpiredBySocialNetwork(SocialNetwork socialNetwork);

    /**
     * Returns true if at least one non-expired registration exists; otherwise false is returned.
     *
     * @param socialNetwork The social network to check for registrations.
     *
     * @return true if at least one non-expired registration exists; otherwise false is returned.
     */
    boolean isRegistered(SocialNetwork socialNetwork);

    /**
     * Returns a persistent SocialNetworkRegistration object identified by the specified id.
     * If no SocialNetworkRegistration with that token exists, null is returned.
     *
     * @param token the access token to retrieve
     *
     * @return a persistent SocialNetworkRegistration object identified by the specified id.
     * If no SocialNetworkRegistration with that token exists, null is returned.
     */
    SocialNetworkRegistration findByToken(String token);

    /**
     * Returns a List of all persistent SocialNetworkRegistrations. If no SocialNetworkRegistrations exist,
     * an empty List is returned.
     *
     * @return a List of all persistent SocialNetworkRegistrations. If no SocialNetworkRegistrations exist,
     * an empty List is returned.
     */
    List<SocialNetworkRegistration> findAll();

    /**
     * Immediately saves the specified SocialNetworkRegistration to the backing store.
     *
     * @param socialNetworkRegistration the SocialNetworkRegistration to save.
     */
    void save(SocialNetworkRegistration socialNetworkRegistration);

    /**
     * Updates the persistent SocialNetworkRegistration based on the specified SocialNetworkRegistration.
     *
     * @param socialNetworkRegistration the SocialNetworkRegistration to update.
     */
    void update(SocialNetworkRegistration socialNetworkRegistration);

    /**
     * Registers a social network in the database based on the provided OAuth Grant.
     *
     * @param socialNetwork the social network to register.
     * @param accessGrant the OAuth Token received from registration.
     * @param name the social network name
     *
     * @return returns true if registration was successful; false otherwise.
     */
    boolean register(SocialNetwork socialNetwork, OAuthToken accessGrant, String name);

    /**
     * Registers a social network in the database based on the provided OAuth Grant.
     *
     * @param socialNetwork the social network to register.
     * @param accessGrant the OAuth Grant received from registration.
     * @param name the social network name
     *
     * @return returns true if registration was successful; false otherwise.
     */
    boolean register(SocialNetwork socialNetwork, AccessGrant accessGrant, String name);

    /**
     * Deletes the specified SocialNetworkRegistration from the backing store.
     *
     * @param id the id of the SocialNetworkRegistration to delete.
     */
    void deleteById(int id);

    /**
     * Deletes all SocialNetworkRegistrations from the backing store for the specified Social Network.
     *
     * @param socialNetwork the Social Network to delete registrations for.
     */
    void deleteBySocialNetwork(SocialNetwork socialNetwork);

    /**
     * Returns true if the registration has expired.
     *
     * @param socialNetworkRegistration the registraiton to check.
     *
     * @return true if the registration has expired.
     */
    boolean isExpired(SocialNetworkRegistration socialNetworkRegistration);
}
