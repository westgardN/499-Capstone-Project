package edu.metrostate.ics499.prim.service;

import java.util.*;

import edu.metrostate.ics499.prim.datatransfer.UserDataTransfer;
import edu.metrostate.ics499.prim.exception.EmailExistsException;
import edu.metrostate.ics499.prim.exception.SsoIdExistsException;
import edu.metrostate.ics499.prim.exception.UsernameExistsException;
import edu.metrostate.ics499.prim.model.*;
import edu.metrostate.ics499.prim.repository.SecurityTokenDao;
import edu.metrostate.ics499.prim.repository.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The UserServiceImpl implements the UserService
 * interface for easily working with the Users.
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    @Qualifier("primUserDetailsService")
    UserDetailsService userDetailsService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SecurityTokenDao securityTokenDao;

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    /**
     * Finds and returns a User based on the primary key. Returns null if no user is found.
     *
     * @param id the id of the User to retrieve.
     * @return a User based on the primary key. Returns null if no user is found.
     */
    @Override
    public User findById(int id) {
        return userDao.findById(id);
    }

    /**
     * Finds and returns a User based on the username. Returns null if no user is found.
     *
     * @param username the username of the user.
     * @return a User based on the username. Returns null if no user is found.
     */
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    /**
     * Finds and returns a User based on the email. Returns null if no user is found.
     *
     * @param email the email of the user.
     * @return a User based on the email. Returns null if no user is found.
     */
    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    /**
     * Finds and returns a User based on the SSO Id. Returns null if no user is found.
     *
     * @param ssoId the ssoId of the user.
     * @return a User based on the SSO Id. Returns null if no user is found.
     */
    @Override
    public User findBySsoId(String ssoId) {
        return userDao.findBySsoId(ssoId);
    }

    /**
     * Immediately saves the specified User to the backing store.
     *
     * @param user the User to save.
     */
    @Override
    public void save(User user) {
        userDao.save(user);
    }

    /**
     * Updates the specified User in the backing store.
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends.
     *
     * @param user the User to update in the backing store.
     */
    @Override
    public void update(User user) {
        User entity = userDao.findById(user.getId());
        if(entity!=null){
            entity.setUsername(user.getUsername());
            if(!user.getPassword().equals(entity.getPassword())){
                entity.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            entity.setFirstName(user.getFirstName());
            entity.setLastName(user.getLastName());
            entity.setEmail(user.getEmail());
            entity.setSsoId(user.getSsoId());
            entity.setRoles(user.getRoles());
            entity.setActivatedOn(user.getActivatedOn());
            entity.setFailedLogins(user.getFailedLogins());
            entity.setLastVisitedFrom(user.getLastVisitedFrom());
            entity.setLastVisitedOn(user.getLastVisitedOn());
            entity.setStatus(user.getStatus());
        }
    }

    /**
     * Deletes a User from the backing store based on the Primary Key.
     *
     * @param id the id of the user.
     */
    @Override
    public void deleteById(int id) {
        deleteTokens(findById(id));
        userDao.deleteById(id);
    }

    /**
     * Deletes a User from the backing store based on the username.
     *
     * @param username the username of the user.
     */
    @Override
    public void deleteByUsername(String username) {
        deleteTokens(findByUsername(username));
        userDao.deleteByUsername(username);
    }

    /**
     * Deletes a User from the backing store based on the email.
     *
     * @param email the email of the user.
     */
    @Override
    public void deleteByEmail(String email) {
        deleteTokens(findByEmail(email));
        userDao.deleteByEmail(email);
    }

    /**
     * Deletes a User from the backing store based on the SSO Id.
     *
     * @param ssoId the SSO Id of the user.
     */
    @Override
    public void deleteBySsoId(String ssoId) {
        deleteTokens(findBySsoId(ssoId));
        userDao.deleteBySsoId(ssoId);
    }

    /**
     * Deletes all tokens for the specified user.
     *
     * @param user the User to delete tokens for.
     */
    @Override
    public void deleteTokens(User user) {
        if (user != null) {
            securityTokenDao.delete(user);
        }
    }

    /**
     * Returns a List of all users. If no users are found, an empty list is returned.
     *
     * @return a List of all users. If no users are found, an empty list is returned.
     */
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    /**
     * Registers a new user in to the PRIM system. Returns the newly registered User.
     *
     * @param userDataTransfer the UserDataTransfer to register the new user from.
     * @return the newly registered User.
     * @throws EmailExistsException    indicates that the e-mail address is not unique.
     * @throws UsernameExistsException indicates that the username is not unique.
     * @throws SsoIdExistsException    indicates that the ssoId is not unique.
     */
    @Override
    public User registerNewUser(final UserDataTransfer userDataTransfer) throws EmailExistsException, UsernameExistsException, SsoIdExistsException {

        if (!isUsernameUnique(null, userDataTransfer.getUsername())) {
            throw new UsernameExistsException("An account already exists with username: " + userDataTransfer.getUsername());
        }
        if (!isSsoIdUnique(null, userDataTransfer.getSsoId())) {
            throw new SsoIdExistsException("An account already exists with ssoId: " + userDataTransfer.getSsoId());
        }
        if (!isEmailUnique(null, userDataTransfer.getEmail())) {
            throw new EmailExistsException("An account already exists with email: " + userDataTransfer.getEmail());
        }

        final User user = new User();

        user.setUsername(userDataTransfer.getUsername());
        user.setSsoId(userDataTransfer.getSsoId());
        user.setEmail(userDataTransfer.getEmail());
        user.setPassword(passwordEncoder.encode(userDataTransfer.getPassword()));
        user.setFirstName(userDataTransfer.getFirstName());
        user.setLastName(userDataTransfer.getLastName());
        user.setRoles(roleService.getRoleSet(RoleType.USER));
        user.setStatus(UserStatus.NOT_ACTIVATED);
        user.setEnabled(false);

        save(user);

        return user;
    }

    /**
     * Returns true if the specified username is in fact unique. That is, if the username
     * is in the backing store for another user other than the one with the specified id, then it is not unique.
     *
     * @param id the id of the user record that we are checking against.
     * @param username the username we are checking for uniqueness.
     *
     * @return true if the username is unique
     */
    @Override
    public boolean isUsernameUnique(Integer id, String username) {
        User user = findByUsername(username);
        return ( user == null || ((id != null) && (Objects.equals(user.getId(), id))));
    }

    /**
     * Returns true if the specified email is in fact unique. That is, if the email
     * is in the backing store for another user other than the one with the specified id, then it is not unique.
     *
     * @param id the id of the user record that we are checking against.
     * @param email the email we are checking for uniqueness.
     *
     * @return true if the email is unique
     */
    @Override
    public boolean isEmailUnique(Integer id, String email) {
        User user = findByEmail(email);
        return ( user == null || ((id != null) && (Objects.equals(user.getId(), id))));
    }

    /**
     * Returns true if the specified ssoId is in fact unique. That is, if the ssoId
     * is in the backing store for another user other than the one with the specified id, then it is not unique.
     *
     * @param id the id of the user record that we are checking against.
     * @param ssoId the ssoId we are checking for uniqueness.
     *
     * @return true if the email is unique
     */
    @Override
    public boolean isSsoIdUnique(Integer id, String ssoId) {
        User user = findBySsoId(ssoId);
        return ( user == null || ((id != null) && (Objects.equals(user.getId(), id))));
    }

    /**
     * Returns a User reference for the specified SecurityToken string. If no user is found
     * or the SecurityToken has expired then null is returned.
     *
     * @param securityTokenString the SecurityToken string to use to find the User
     * @return a User reference for the specified SecurityToken string. If no user is found
     * or the SecurityToken has expired then null is returned.
     */
    @Override
    public User getUser(String securityTokenString) {
        return getUser(securityTokenString, true);
    }

    /**
     * Returns a User reference for the specified SecurityToken string. If no user is found null is returned.
     *
     * @param securityTokenString the SecurityToken string to use to find the User
     * @param notExpired          if true then only not expired tokens are considered
     * @return a User reference for the specified SecurityToken string. If no user is found null is returned.
     */
    @Override
    public User getUser(String securityTokenString, boolean notExpired) {
        SecurityToken securityToken = securityTokenDao.find(securityTokenString, notExpired);

        User user = null;

        if (securityToken != null && !securityToken.isExpired()) {
            user = securityToken.getUser();
        }

        return user;
    }

    /**
     * Creates a new SecurityToken for the specified user with the specified token. The newly created
     * SecurityToken is returned after persisting it to the store.
     *
     * @param user                the user the new token is for
     * @return a new SecurityToken for the specified user with the specified token.
     */
    @Override
    public SecurityToken createSecurityToken(User user) {
        final String token = UUID.randomUUID().toString();
        SecurityToken securityToken = new SecurityToken(user, token);

        securityTokenDao.save(securityToken);

        return securityToken;
    }

    /**
     * Updates the existing SecurityToken with a new unexpired token string.
     *
     * @param securityTokenString the token string to that identifies the SecurityToken to update.
     *
     * @return an updated SecurityToken with a new unexpired token string.
     */
    @Override
    public SecurityToken generateNewSecurityToken(SecurityToken securityToken) {
        if (securityToken == null) {
            throw new NullPointerException("the security token does not exist");
        }

        securityToken.update(UUID.randomUUID().toString());
        securityTokenDao.save(securityToken);

        return securityToken;
    }

    /**
     * Updates the existing SecurityToken with a new unexpired token string.
     *
     * @param securityTokenString the token string to that identifies the SecurityToken to update.
     *
     * @return an updated SecurityToken with a new unexpired token string.
     */
    @Override
    public SecurityToken generateNewSecurityToken(String securityTokenString) {
        SecurityToken securityToken = securityTokenDao.find(securityTokenString);

        return generateNewSecurityToken(securityToken);
    }

    /**
     * Returns a string that represents the state of the SecurityToken. The possible values
     * are valid, expired, and invalidToken. If the token is valid, the associated user account
     * is activated and set to enabled and the token is deleted.
     *
     * @param securityTokenString the token string to validate
     * @return a string that represents the state of the SecurityToken. The possible values
     * are valid, expired, and invalidToken.
     */
    @Override
    public String validateRegistrationToken(String securityTokenString) {
        String answer = TOKEN_VALID;

        final SecurityToken securityToken = securityTokenDao.find(securityTokenString);

        if (securityToken == null) {
            answer = TOKEN_INVALID;
        } else if (securityToken.isExpired()) {
            securityTokenDao.delete(securityToken);
            answer = TOKEN_EXPIRED;
        } else {
            User user = securityToken.getUser();

            user.setEnabled(true);
            user.setStatus(UserStatus.ACTIVE);
            user.setActivatedOn(new Date());

            userDao.save(user);
        }
        return answer;
    }

    /**
     * Returns a string that represents the state of the SecurityToken. The possible values
     * are valid, expired, and invalidToken. If the token is valid, the security context is
     * updated with the new token and the token is deleted.
     *
     * @param id                  The repository ID of the User the SecurityToken is for
     * @param securityTokenString the token string to validate
     * @return a string that represents the state of the SecurityToken. The possible values
     * are valid, expired, and invalidToken.
     */
    @Override
    public String validatePasswordToken(long id, String securityTokenString) {
        final SecurityToken securityToken = securityTokenDao.find(securityTokenString);
        String answer = TOKEN_VALID;

        if (securityToken == null || securityToken.getUser().getId() != id) {
            answer = TOKEN_INVALID;
        } else if (securityToken.isExpired()) {
            answer = TOKEN_EXPIRED;
            securityTokenDao.delete(securityToken);
        } else {
            final User user = securityToken.getUser();
            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getSsoId());

            final Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("Logging in {} user without a password: {}", authentication.isAuthenticated() ? "authenticated" : "unauthenticated", userDetails);
        }

        return answer;
    }

    /**
     * Deletes the specified SecurityToken if it exists.
     *
     * @param token the token to delete.
     */
    @Override
    public void deleteSecurityToken(String token) {
        securityTokenDao.delete(token);
    }

    /**
     * Deletes the specified SecurityToken if it exists.
     *
     * @param token the token to delete.
     */
    @Override
    public void deleteSecurityToken(SecurityToken token) {
        securityTokenDao.delete(token);
    }

    /**
     * Returns the SecurityToken instance for the specified token string. Null is returned if the
     * token does not exist.
     *
     * @param securityTokenString the SecurityToken string to use to find the SecurityToken
     * @return the SecurityToken instance for the specified token string. Null is returned if the
     * token does not exist.
     */
    @Override
    public SecurityToken getSecurityToken(String securityTokenString) {
        SecurityToken securityToken = securityTokenDao.find(securityTokenString);

        return securityToken;
    }

    /**
     * Returns the first SecurityToken instance found for the specified User. Null is returned if a token doesn't
     * exist for the specified User.
     *
     * @param user the User to use to find the SecurityToken
     * @return the first SecurityToken instance found for the specified User. Null is returned if a token doesn't
     * exist for the specified User.
     */
    @Override
    public SecurityToken getSecurityToken(User user) {
        return getSecurityToken(user, false);
    }

    /**
     * Returns the first SecurityToken instance found for the specified User. Null is returned if a token doesn't
     * exist for the specified User.
     *
     * @param user       the User to use to find the SecurityToken
     * @param notExpired if true only valid tokens are considered.
     * @return the first SecurityToken instance found for the specified User. Null is returned if a token doesn't
     * exist for the specified User.
     */
    @Override
    public SecurityToken getSecurityToken(User user, boolean notExpired) {
        SecurityToken securityToken = securityTokenDao.findOne(user, notExpired);

        return securityToken;
    }

    /**
     * Returns the SecurityTokens for the specified User. An empty list is returned if a token doesn't
     * exist for the specified User.
     *
     * @param user the User to use to find the SecurityToken
     * @return the SecurityTokens for the specified User. An empty list is returned if a token doesn't
     * exist for the specified User.
     */
    @Override
    public List<SecurityToken> getSecurityTokens(User user) {
        return getSecurityTokens(user, false);
    }

    /**
     * Returns the SecurityTokens for the specified User. An empty list is returned if a token doesn't
     * exist for the specified User.
     *
     * @param user       the User to use to find the SecurityToken
     * @param notExpired if true only valid tokens are considered.
     * @return the SecurityTokens for the specified User. An empty list is returned if a token doesn't
     * exist for the specified User.
     */
    @Override
    public List<SecurityToken> getSecurityTokens(User user, boolean notExpired) {
        List<SecurityToken> securityTokens = securityTokenDao.find(user, notExpired);

        return securityTokens;
    }

    /**
     * Encrypts and sets the password of the specified User to the specified new password
     *
     * @param user the User to change the password for
     * @param password the User's new password
     */
    @Override
    public void changePassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userDao.save(user);
    }

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
    @Override
    public boolean isCurrentPasswordValid(User user, String currentPassword) {
        return passwordEncoder.matches(currentPassword, user.getPassword());
    }

    /**
     * Records a failed login for the specified user. If the failure exceeds the max failed
     * consecutive logins the account is then locked.
     *
     * @param user the User to fail the login for.
     */
    @Override
    public void failLogin(User user) {
        user.setLastVisitedOn(new Date());
        user.setFailedLogins(user.getFailedLogins() + 1);
        if (user.getFailedLogins() >= 5) {
            user.setStatus(UserStatus.LOCKED);
        }
    }

    /**
     * Records a successful login for the specified user. A successful login resets the user's failed
     * logins.
     *
     * @param user the User to succeed the login for.
     */
    @Override
    public void successLogin(User user) {
        user.setLastVisitedOn(new Date());
        user.setFailedLogins(0);
    }
}
