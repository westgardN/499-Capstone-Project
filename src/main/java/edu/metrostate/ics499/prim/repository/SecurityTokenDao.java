package edu.metrostate.ics499.prim.repository;

import edu.metrostate.ics499.prim.model.SecurityToken;
import edu.metrostate.ics499.prim.model.User;

import java.util.List;

public interface SecurityTokenDao extends IRepository {

    /**
     * Returns the SecurityToken for the specified ID. Null is returned if the SecurityToken
     * does not exist.
     *
     * @param id the ID of the security token to find
     * @return the SecurityToken for the specified ID. Null is returned if the SecurityToken
     *         does not exist.
     */
    SecurityToken find(Long id);

    /**
     * Returns the SecurityToken for the specified token. Null is returned if the SecurityToken
     * does not exist.
     *
     * @param token the token of the security token to find
     * @return the SecurityToken for the specified token. Null is returned if the SecurityToken
     *         does not exist.
     */
    SecurityToken find(String token);

    /**
     * Returns the SecurityToken for the specified token. Null is returned if the SecurityToken
     * does not exist.
     *
     * @param token the token of the security token to find
     * @param notExpired if true, only tokens that have not expired are considered.
     * @return the SecurityToken for the specified token. Null is returned if the SecurityToken
     *         does not exist.
     */
    SecurityToken find(String token, boolean notExpired);

    /**
     * Returns all of the SecurityTokens for the specified user. An empty list is returned if the SecurityToken
     * does not exist.
     *
     * @param user the user to find SecurityTokens for
     * @return all of the SecurityTokens for the specified user. An empty list is returned if the SecurityToken
     *         does not exist.
     */
    List<SecurityToken> find(User user);

    /**
     * Returns the SecurityTokens for the specified user. An empty list is returned if the SecurityToken
     * does not exist.
     *
     * @param user the user to find SecurityTokens for
     * @param notExpired if true, only tokens that have not expired are considered.
     *
     * @return the SecurityTokens for the specified user. An empty list is returned if the SecurityToken
     *         does not exist.
     */
    List<SecurityToken> find(User user, boolean notExpired);

    /**
     * Returns the first SecurityTokens for the specified user. Null is returned if the SecurityToken
     * does not exist.
     *
     * @param user the user to find a SecurityToken for
     * @return the first SecurityTokens for the specified user. Null is returned if the SecurityToken
     *         does not exist.
     */
    SecurityToken findOne(User user);

    /**
     * Returns the first SecurityToken for the specified user. Null is returned if the SecurityToken
     * does not exist.
     *
     * @param user the user to find a SecurityToken for
     * @param notExpired if true, only tokens that have not expired are considered.
     *
     * @return the SecurityTokens for the specified user. An empty list is returned if the SecurityToken
     *         does not exist.
     */
    SecurityToken findOne(User user, boolean notExpired);

    /**
     * Returns a list of all of the valid SecurityTokens or an empty list if there are no tokens.
     *
     * @return a list of all of the valid SecurityTokens or an empty list if there are no tokens.
     */
    List<SecurityToken> findAll();

    /**
     * Returns a list of all of the SecurityTokens or an empty list if there are no tokens.
     *
     * @param expired if true, only expired tokens are considered; otherwise
     *                only valid tokens are considered.
     * @return a list of all of the SecurityTokens or an empty list if there are no tokens.
     */
    List<SecurityToken> findAll(boolean expired);

    /**
     * Removes the SecurityToken with the specified ID from persistence if it exists.
     *
     * @param id the ID of the token to delete
     *
     * @return the count of number of SecurityTokens that were removed.
     */
    int delete(Long id);

    /**
     * Removes the SecurityToken identified by the specified token string from persistence if it exists.
     *
     * @param token the token string to delete.
     *
     * @return the count of number of SecurityTokens that were removed.
     */
    int delete(String token);

    /**
     * Removes the SecurityTokens associated with the specified user from persistence if any exist.
     *
     * @param user the user to delete SecurityTokens for
     *
     * @return the count of number of SecurityTokens that were removed.
     */
    int delete(User user);

    /**
     * Removes all valid SecurityTokens from persistence if any exist.
     *
     * @return the count of number of SecurityTokens that were removed.
     */
    int deleteAll();

    /**
     * Removes all SecurityTokens from persistence if any exist.
     *
     * @param expired if true, then only expired tokens are removed; otherwise only valid are removed.
     *
     * @return the count of number of SecurityTokens that were removed.
     */
    int deleteAll(boolean expired);

    /**
     * Immediately saves the specified SecurityToken to persistence.
     *
     * @param securityToken the security token to persist
     *
     */
    void save(SecurityToken securityToken);

    /**
     * Deletes the specified SecurityToken from the persistence store.
     *
     * @param securityToken the SecurityToken to delete.
     */
    void delete(SecurityToken securityToken);
}
