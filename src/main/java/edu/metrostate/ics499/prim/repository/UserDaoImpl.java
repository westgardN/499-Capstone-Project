package edu.metrostate.ics499.prim.repository;

import java.util.List;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import edu.metrostate.ics499.prim.model.User;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    /**
     * Finds and returns a User based on the primary key. Returns null if no user is found.
     *
     * @param id the id of the User to retrieve.
     * @return a User based on the primary key. Returns null if no user is found.
     */
    @Override
    public User findById(int id) {
        User user = null;

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<User> crit = builder.createQuery(User.class);
        Root<User> from = crit.from(User.class);
        Predicate clause = builder.equal(from.get("id"), id);
        crit.select(from).where(clause);
        TypedQuery<User> query = getSession().createQuery(crit);

        try {
            user = query.getSingleResult();

            Hibernate.initialize(user.getRoles());
        } catch (NoResultException ex) {
            logger.info("No User found with id : {}", id);
        }

        return user;
    }

    /**
     * Finds and returns a User based on the username. Returns null if no user is found.
     *
     * @param username the username of the user.
     * @return a User based on the username. Returns null if no user is found.
     */
    @Override
    public User findByUsername(String username) {
        User user = null;

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<User> crit = builder.createQuery(User.class);
        Root<User> from = crit.from(User.class);
        Predicate clause = builder.equal(from.get("username"), username);
        crit.select(from).where(clause);
        TypedQuery<User> query = getSession().createQuery(crit);

        try {
            user = query.getSingleResult();

            Hibernate.initialize(user.getRoles());
        } catch (NoResultException ex) {
            logger.info("No User found with username : {}", username);
        }

        return user;
    }

    /**
     * Finds and returns a User based on the email. Returns null if no user is found.
     *
     * @param email the email of the user.
     * @return a User based on the email. Returns null if no user is found.
     */
    @Override
    public User findByEmail(String email) {
        User user = null;

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<User> crit = builder.createQuery(User.class);
        Root<User> from = crit.from(User.class);
        Predicate clause = builder.equal(from.get("email"), email);
        crit.select(from).where(clause);
        TypedQuery<User> query = getSession().createQuery(crit);

        try {
            user = query.getSingleResult();

            Hibernate.initialize(user.getRoles());
        } catch (NoResultException ex) {
            logger.info("No User found with email : {}", email);
        }

        return user;
    }

    /**
     * Finds and returns a User based on the SSO Id. Returns null if no user is found.
     *
     * @param ssoId the ssoId of the user.
     * @return a User based on the SSO Id. Returns null if no user is found.
     */
    @Override
    public User findBySsoId(String ssoId) {
        User user = null;

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<User> crit = builder.createQuery(User.class);
        Root<User> from = crit.from(User.class);
        Predicate clause = builder.equal(from.get("ssoId"), ssoId);
        crit.select(from).where(clause);
        TypedQuery<User> query = getSession().createQuery(crit);

        try {
            user = query.getSingleResult();

            Hibernate.initialize(user.getRoles());
        } catch (NoResultException ex) {
            logger.info("No User found with ssoId : {}", ssoId);
        }

        return user;
    }

    /**
     * Immediately saves the specified User to the backing store.
     *
     * @param user the User to save.
     */
    @Override
    public void save(User user) {
        persist(user);
    }

    /**
     * Deletes a User from the backing store based on the Primary Key.
     *
     * @param id the id of the user.
     */
    @Override
    public void deleteById(int id) {
        User user = findById(id);

        if (user != null) {
            delete(user);
        }
    }

    /**
     * Deletes a User from the backing store based on the username.
     *
     * @param username the username of the user.
     */
    @Override
    public void deleteByUsername(String username) {
        User user = findByUsername(username);

        if (user != null) {
            delete(user);
        }
    }

    /**
     * Deletes a User from the backing store based on the email.
     *
     * @param email the email of the user.
     */
    @Override
    public void deleteByEmail(String email) {
        User user = findByEmail(email);

        if (user != null) {
            delete(user);
        }
    }

    /**
     * Deletes a User from the backing store based on the SSO Id.
     *
     * @param ssoId the SSO Id of the user.
     */
    @Override
    public void deleteBySsoId(String ssoId) {
        User user = findBySsoId(ssoId);

        if (user != null) {
            delete(user);
        }
    }

    /**
     * Returns a List of all users. If no users are found, an empty list is returned.
     *
     * @return a List of all users. If no users are found, an empty list is returned.
     */
    @Override
    public List<User> findAll() {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<User> crit = builder.createQuery(User.class);
        Root<User> from = crit.from(User.class);
        crit.select(from).orderBy(builder.asc(from.get("username")));
        TypedQuery<User> query = getSession().createQuery(crit);
        return query.getResultList();
    }
}
