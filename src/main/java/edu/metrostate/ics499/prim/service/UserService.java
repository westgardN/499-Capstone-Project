package edu.metrostate.ics499.prim.service;

import java.util.List;

import edu.metrostate.ics499.prim.datatransfer.UserDataTransfer;
import edu.metrostate.ics499.prim.exception.EmailExistsException;
import edu.metrostate.ics499.prim.exception.SsoIdExistsException;
import edu.metrostate.ics499.prim.exception.UsernameExistsException;
import edu.metrostate.ics499.prim.model.User;

/**
 * An interface for working with and managing a Users.
 */
public interface UserService {

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
     * Updates the specified User in the backing store.
     *
     * @param user the user to update.
     */
    void update(User user);

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

    /**
     * Registers a new user in to the PRIM system. Returns the newly registered User.
     * Throws an exception if the username, ssoId, or email address are not unique.
     *
     * @param userDataTransfer the UserDataTransfer to register the new user from.
     *
     * @return the newly registered User.
     * @throws EmailExistsException indicates that the e-mail address is not unique.
     * @throws UsernameExistsException indicates that the username is not unique.
     * @throws SsoIdExistsException indicates that the ssoId is not unique.
     */
    User registerNewUser(UserDataTransfer userDataTransfer) throws EmailExistsException, UsernameExistsException, SsoIdExistsException;

    /**
     *
     * @param user
     * @param password
     */
    void changePassword(User user, String password);

    boolean isCurrentPasswordValid(User user, String password);

    /**
     * Returns true if the specified username is in fact unique. That is, if the username
     * is in the backing store for another user other than the one with the specified id, then it is not unique.
     *
     * @param id the id of the user record that we are checking against.
     * @param username the username we are checking for uniqueness.
     *
     * @return true if the username is unique
     */
    boolean isUsernameUnique(Integer id, String username);

    /**
     * Returns true if the specified email is in fact unique. That is, if the email
     * is in the backing store for another user other than the one with the specified id, then it is not unique.
     *
     * @param id the id of the user record that we are checking against.
     * @param email the email we are checking for uniqueness.
     *
     * @return true if the email is unique
     */
    boolean isEmailUnique(Integer id, String email);

    /**
     * Returns true if the specified ssoId is in fact unique. That is, if the ssoId
     * is in the backing store for another user other than the one with the specified id, then it is not unique.
     *
     * @param id the id of the user record that we are checking against.
     * @param ssoId the ssoId we are checking for uniqueness.
     *
     * @return true if the email is unique
     */
    boolean isSsoIdUnique(Integer id, String ssoId);

}