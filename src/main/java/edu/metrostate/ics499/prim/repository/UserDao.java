package edu.metrostate.ics499.prim.repository;

import java.util.List;

import edu.metrostate.ics499.prim.model.User;

/**
 * The UserDao interface defines the available persistence operations that can be performed for
 * a User.
 */
public interface UserDao extends IRepository {

    /**
     * Finds and returns a User based on the primary key. Returns null if no user is found.
     *
     * @param id the id of the User to retrieve.
     * @return a User based on the primary key. Returns null if no user is found.
     */
    User findById(int id);

    /**
     * Finds and returns a User based on the username. Returns null if no user is found.
     *
     * @param username the username of the user.
     * @return a User based on the username. Returns null if no user is found.
     */
    User findByUsername(String username);

    /**
     * Finds and returns a User based on the email. Returns null if no user is found.
     *
     * @param email the email of the user.
     * @return a User based on the email. Returns null if no user is found.
     */
    User findByEmail(String email);

    /**
     * Finds and returns a User based on the SSO Id. Returns null if no user is found.
     *
     * @param ssoId the ssoId of the user.
     * @return a User based on the SSO Id. Returns null if no user is found.
     */
    User findBySsoId(String ssoId);

    /**
     * Immediately saves the specified User to the backing store.
     *
     * @param user the User to save.
     */
    void save(User user);

    /**
     * Deletes a User from the backing store based on the Primary Key.
     *
     * @param id the id of the user.
     */
    void deleteById(int id);

    /**
     * Deletes a User from the backing store based on the username.
     *
     * @param username the username of the user.
     */
    void deleteByUsername(String username);

    /**
     * Deletes a User from the backing store based on the email.
     *
     * @param email the email of the user.
     */
    void deleteByEmail(String email);

    /**
     * Deletes a User from the backing store based on the SSO Id.
     *
     * @param ssoId the SSO Id of the user.
     */
    void deleteBySsoId(String ssoId);

    /**
     * Returns a List of all users. If no users are found, an empty list is returned.
     *
     * @return a List of all users. If no users are found, an empty list is returned.
     */
    List<User> findAll();

}