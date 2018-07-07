package edu.metrostate.ics499.prim.repository;

import edu.metrostate.ics499.prim.model.SocialNetworkRegistration;

import java.util.List;

/**
 * The SocialNetworkRegistrationDao interface defines the operations that can be performed for an
 * SocialNetworkRegistration.
 */
public interface SocialNetworkRegistrationDao {

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
    List<SocialNetworkRegistration> findBySocialNetwork(String socialNetwork);

    /**
     * Returns a List of all persistent SocialNetworkRegistrations. If no SocialNetworkRegistrations exist,
     * an empty List is returned.
     *
     * @return a List of all persistent SocialNetworkRegistrations. If no SocialNetworkRegistrations exist,
     * an empty List is returned.
     */
    List<SocialNetworkRegistration> findAll();

    /**
     * Deletes the specified SocialNetworkRegistration from the backing store.
     *
     * @param id the id of the SocialNetworkRegistration to delete.
     */
    void deletebyId(int id);

    /**
     * Deletes all SocialNetworkRegistrations from the backing store for the specified Social Network.
     *
     * @param socialNetwork the Social Network to delete registrations for.
     */
    void deleteBySocialNetwork(String socialNetwork);

}
