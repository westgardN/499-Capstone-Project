package edu.metrostate.ics499.prim.service;

import java.util.List;

import edu.metrostate.ics499.prim.datatransfer.UserDataTransfer;
import edu.metrostate.ics499.prim.exception.EmailExistsException;
import edu.metrostate.ics499.prim.exception.SsoIdExistsException;
import edu.metrostate.ics499.prim.exception.UsernameExistsException;
import edu.metrostate.ics499.prim.model.SecurityToken;
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
     * Deletes all tokens for the specified user.
     *
     * @param user the User to delete tokens for.
     */
    void deleteTokens(final User user);

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

    /**
     * Returns a User reference for the specified SecurityToken string. If no user is found
     * or the SecurityToken has expired then null is returned.
     *
     * @param securityTokenString the SecurityToken string to use to find the User
     *
     * @return a User reference for the specified SecurityToken string. If no user is found
     * or the SecurityToken has expired then null is returned.
     */
    User getUser(String securityTokenString);

    /**
     * Returns a User reference for the specified SecurityToken string. If no user is found null is returned.
     *
     * @param securityTokenString the SecurityToken string to use to find the User
     * @param notExpired if true then only not expired tokens are considered
     *
     * @return a User reference for the specified SecurityToken string. If no user is found null is returned.
     */
    User getUser(String securityTokenString, boolean notExpired);

    /**
     * Creates a new SecurityToken for the specified user with the specified token. The newly created
     * SecurityToken is returned after persisting it to the store.
     *
     * @param user the user the new token is for
     *
     * @return a new SecurityToken for the specified user with the specified token.
     */
    SecurityToken createSecurityToken(User user);

    /**
     * Updates the existing SecurityToken with a new unexpired token string.
     *
     * @param securityToken the token to create a new token string for.
     *
     * @return an updated SecurityToken with a new unexpired token string.
     */
    SecurityToken generateNewSecurityToken(final SecurityToken securityToken);

    /**
     * Updates the existing SecurityToken with a new unexpired token string.
     *
     * @param securityTokenString the token string to that identifies the SecurityToken to update.
     *
     * @return an updated SecurityToken with a new unexpired token string.
     */
    SecurityToken generateNewSecurityToken(final String securityTokenString);

    /**
     * Returns a string that represents the state of the SecurityToken. The possible values
     * are valid, expired, and invalidToken. If the token is valid, the associated user account
     * is activated and set to enabled and the token is deleted.
     *
     * @param securityTokenString the token string to validate
     *
     * @return a string that represents the state of the SecurityToken. The possible values
     *         are valid, expired, and invalidToken.
     */
    String validateRegistrationToken(String securityTokenString);

    /**
     * Returns a string that represents the state of the SecurityToken. The possible values
     * are valid, expired, and invalidToken. If the token is valid, the security context is
     * updated with the new token and the token is deleted.
     *
     * @param id The repository ID of the User the SecurityToken is for
     * @param securityTokenString the token string to validate
     *
     * @return a string that represents the state of the SecurityToken. The possible values
     *         are valid, expired, and invalidToken.
     */
    String validatePasswordToken(long id, String securityTokenString);

    /**
     * Deletes the specified SecurityToken if it exists.
     *
     * @param token the token to delete.
     */
    void deleteSecurityToken(String token);

    /**
     * Deletes the specified SecurityToken if it exists.
     *
     * @param token the token to delete.
     */
    void deleteSecurityToken(SecurityToken token);

    /**
     * Returns the SecurityToken instance for the specified token string. Null is returned if the
     * token does not exist.
     *
     * @param securityTokenString the SecurityToken string to use to find the SecurityToken
     *
     * @return the SecurityToken instance for the specified token string. Null is returned if the
     *         token does not exist.
     */
    SecurityToken getSecurityToken(String securityTokenString);

    /**
     * Returns the first SecurityToken instance found for the specified User. Null is returned if a token doesn't
     * exist for the specified User.
     *
     * @param user the User to use to find the SecurityToken
     *
     * @return the first SecurityToken instance found for the specified User. Null is returned if a token doesn't
     *         exist for the specified User.
     */
    SecurityToken getSecurityToken(User user);

    /**
     * Returns the first SecurityToken instance found for the specified User. Null is returned if a token doesn't
     * exist for the specified User.
     *
     * @param user the User to use to find the SecurityToken
     * @param notExpired if true only valid tokens are considered.
     *
     * @return the first SecurityToken instance found for the specified User. Null is returned if a token doesn't
     *         exist for the specified User.
     */
    SecurityToken getSecurityToken(User user, boolean notExpired);

    /**
     * Returns the SecurityTokens for the specified User. An empty list is returned if a token doesn't
     * exist for the specified User.
     *
     * @param user the User to use to find the SecurityToken
     *
     * @return the SecurityTokens for the specified User. An empty list is returned if a token doesn't
     *         exist for the specified User.
     */
    List<SecurityToken> getSecurityTokens(User user);

    /**
     * Returns the SecurityTokens for the specified User. An empty list is returned if a token doesn't
     * exist for the specified User.
     *
     * @param user the User to use to find the SecurityToken
     * @param notExpired if true only valid tokens are considered.
     *
     * @return the SecurityTokens for the specified User. An empty list is returned if a token doesn't
     *         exist for the specified User.
     */
    List<SecurityToken> getSecurityTokens(User user, boolean notExpired);

    /**
     * Encrypts and sets the password of the specified User to the specified new password
     *
     * @param user the User to change the password for
     * @param password the User's new password
     */
    void changePassword(final User user, final String password);

    /**
     * Returns true if the specified password string matches the current password for the
     * specified user; otherwise false is returned.
     *
     * @param user the User to validate the password for
     * @param currentPassword the User's current password
     *
     * @return true if the specified password string matches the current password for the
     *         specified user; otherwise false is returned.
     */
    boolean isCurrentPasswordValid(final User user, final String currentPassword);

    /**
     * Records a failed login for the specified user. If the failure exceeds the max failed
     * consecutive logins the account is then locked.
     *
     * @param user the User to fail the login for.
     */
    void failLogin(User user);

    /**
     * Records a successful login for the specified user. A successful login resets the user's failed
     * logins.
     *
     * @param user the User to succeed the login for.
     */
    void successLogin(User user);
}