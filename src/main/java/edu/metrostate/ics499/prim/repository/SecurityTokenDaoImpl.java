package edu.metrostate.ics499.prim.repository;

import edu.metrostate.ics499.prim.model.SecurityToken;
import edu.metrostate.ics499.prim.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository("securityTokenDao")
public class SecurityTokenDaoImpl extends AbstractDao<Integer, SecurityToken> implements SecurityTokenDao {
    private static final Logger logger = LoggerFactory.getLogger(SecurityTokenDaoImpl.class);

    private SecurityToken find(String name, Object value) {
        SecurityToken securityToken = null;

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<SecurityToken> crit = builder.createQuery(SecurityToken.class);
        Root<SecurityToken> from = crit.from(SecurityToken.class);
        Predicate clause = builder.equal(from.get(name), value);
        crit.select(from).where(clause);
        TypedQuery<SecurityToken> query = getSession().createQuery(crit);

        try {
            securityToken = query.getSingleResult();

        } catch (NoResultException ex) {
            logger.info("No SecurityToken found with {}: {}", name, value);
        }

        return securityToken;
    }

    private List<SecurityToken> find(String name, Object value, boolean notExpired) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<SecurityToken> crit = builder.createQuery(SecurityToken.class);
        Root<SecurityToken> from = crit.from(SecurityToken.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(builder.equal(from.get(name), value));

        if (notExpired) {
            Predicate expirationDatePred = builder.greaterThan(from.get("expirationDate"), new Date());

            predicates.add(builder.or(expirationDatePred, from.get("expirationDate").isNull()));
        }

        Predicate clause = builder.and((predicates.toArray(new Predicate[predicates.size()])));
        crit.select(from).where(clause).orderBy(builder.asc(from.get("createdDate")));
        TypedQuery<SecurityToken> query = getSession().createQuery(crit);
        List<SecurityToken> results = query.getResultList();

        if (results.isEmpty()) {
            logger.info("No SecurityTokens found with {}: {}", name, value);
        }

        return results;
    }

    private SecurityToken findOne(String name, Object value, boolean notExpired) {
        SecurityToken answer = null;

        List<SecurityToken> securityTokens = find(name, value, notExpired);

        if (!securityTokens.isEmpty()) {
            answer = securityTokens.get(0);
        }

        return answer;
    }

    /**
     * Returns the SecurityToken for the specified ID. Null is returned if the SecurityToken
     * does not exist.
     *
     * @param id the ID of the security token to find
     * @return the SecurityToken for the specified ID. Null is returned if the SecurityToken
     * does not exist.
     */
    @Override
    public SecurityToken find(Long id) {
        return find("id", id);
    }

    /**
     * Returns the SecurityToken for the specified token. Null is returned if the SecurityToken
     * does not exist.
     *
     * @param token the token of the security token to find
     * @return the SecurityToken for the specified token. Null is returned if the SecurityToken
     * does not exist.
     */
    @Override
    public SecurityToken find(String token) {
        return find("token", token);
    }

    /**
     * Returns the SecurityToken for the specified token. Null is returned if the SecurityToken
     * does not exist.
     *
     * @param token      the token of the security token to find
     * @param notExpired if true, only tokens that have not expired are considered.
     * @return the SecurityToken for the specified token. Null is returned if the SecurityToken
     * does not exist.
     */
    @Override
    public SecurityToken find(String token, boolean notExpired) {
        return findOne("token", token, notExpired);
    }

    /**
     * Returns all of the SecurityTokens for the specified user. An empty list is returned if the SecurityToken
     * does not exist.
     *
     * @param user the user to find SecurityTokens for
     * @return all of the SecurityTokens for the specified user. An empty list is returned if the SecurityToken
     * does not exist.
     */
    @Override
    public List<SecurityToken> find(User user) {
        return find(user, false);
    }

    /**
     * Returns the SecurityTokens for the specified user. An empty list is returned if the SecurityToken
     * does not exist.
     *
     * @param user       the user to find SecurityTokens for
     * @param notExpired if true, only tokens that have not expired are considered.
     * @return the SecurityTokens for the specified user. An empty list is returned if the SecurityToken
     * does not exist.
     */
    @Override
    public List<SecurityToken> find(User user, boolean notExpired) {
        return find("user", user, notExpired);
    }

    /**
     * Returns the first SecurityTokens for the specified user. Null is returned if the SecurityToken
     * does not exist.
     *
     * @param user the user to find a SecurityToken for
     * @return the first SecurityTokens for the specified user. Null is returned if the SecurityToken
     * does not exist.
     */
    @Override
    public SecurityToken findOne(User user) {
        return findOne(user, false);
    }

    /**
     * Returns the first SecurityToken for the specified user. Null is returned if the SecurityToken
     * does not exist.
     *
     * @param user       the user to find a SecurityToken for
     * @param notExpired if true, only tokens that have not expired are considered.
     * @return the SecurityTokens for the specified user. An empty list is returned if the SecurityToken
     * does not exist.
     */
    @Override
    public SecurityToken findOne(User user, boolean notExpired) {
        return findOne("user", user, notExpired);
    }

    /**
     * Returns a list of all of the valid SecurityTokens or an empty list if there are no tokens.
     *
     * @return a list of all of the valid SecurityTokens or an empty list if there are no tokens.
     */
    @Override
    public List<SecurityToken> findAll() {
        return findAll(false);
    }

    /**
     * Returns a list of all of the SecurityTokens or an empty list if there are no tokens.
     *
     * @param expired if true, only expired tokens are considered; otherwise
     *                only valid tokens are considered.
     * @return a list of all of the SecurityTokens or an empty list if there are no tokens.
     */
    @Override
    public List<SecurityToken> findAll(boolean expired) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<SecurityToken> crit = builder.createQuery(SecurityToken.class);
        Root<SecurityToken> from = crit.from(SecurityToken.class);
        List<Predicate> predicates = new ArrayList<Predicate>();

        if (expired) {
            predicates.add(builder.lessThanOrEqualTo(from.get("expirationDate"), new Date()));
        } else {
            Predicate expirationDatePred = builder.greaterThan(from.get("expirationDate"), new Date());

            predicates.add(builder.or(expirationDatePred, from.get("expirationDate").isNull()));
        }

        Predicate clause = builder.and((predicates.toArray(new Predicate[predicates.size()])));
        crit.select(from).where(clause).orderBy(builder.asc(from.get("createdDate")));
        TypedQuery<SecurityToken> query = getSession().createQuery(crit);
        List<SecurityToken> results = query.getResultList();

        if (results.isEmpty()) {
            logger.info("No SecurityTokens found");
        }

        return results;
    }

    /**
     * Removes the SecurityToken with the specified ID from persistence if it exists.
     *
     * @param id the ID of the token to delete
     */
    @Override
    public int delete(Long id) {
        int result = 0;

        SecurityToken securityToken = find(id);

        if (securityToken != null) {
            delete(securityToken);
            ++result;
        }

        return result;
    }

    /**
     * Removes the SecurityToken identified by the specified token string from persistence if it exists.
     *
     * @param token the token string to delete.
     */
    @Override
    public int delete(String token) {
        int result = 0;

        SecurityToken securityToken = find(token);

        if (securityToken != null) {
            delete(securityToken);
            ++result;
        }

        return result;
    }

    /**
     * Removes the SecurityTokens associated with the specified user from persistence if any exist.
     *
     * @param user the user to delete SecurityTokens for
     */
    @Override
    public int delete(User user) {
        int result = 0;

        List<SecurityToken> securityTokens = find(user);

        for (SecurityToken securityToken : securityTokens) {
            delete(securityToken);
            ++result;
        }

        return result;
    }

    /**
     * Removes all valid SecurityTokens from persistence if any exist.
     */
    @Override
    public int deleteAll() {
        return deleteAll(false);
    }

    /**
     * Removes all SecurityTokens from persistence if any exist.
     *
     * @param expired if true, then only expired tokens are removed; otherwise only valid are removed.
     */
    @Override
    public int deleteAll(boolean expired) {
        String queryString = "DELETE FROM SecurityToken st";

        if (expired) {
            queryString += " WHERE st.expirationDate IS NOT NULL AND st.expirationDate <= :date";
        } else {
            queryString += " WHERE st.expirationDate IS NULL OR st.expirationDate > :date";
        }

        Query query = getSession().createQuery(queryString);

        query.setParameter("date", new Date());

        int deletedCount = query.executeUpdate();

        return deletedCount;
    }

    /**
     * Immediately saves the specified SecurityToken to persistence.
     *
     * @param securityToken the security token to presist
     */
    @Override
    public void save(SecurityToken securityToken) {

        persist(securityToken);
    }
}
