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
    static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

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

    @Override
    public void save(User user) {
        persist(user);
    }

    @Override
    public void deleteById(int id) {
        User user = findById(id);

        if (user != null) {
            delete(user);
        }
    }

    @Override
    public void deleteByUsername(String username) {
        User user = findByUsername(username);

        if (user != null) {
            delete(user);
        }
    }

    @Override
    public void deleteByEmail(String email) {
        User user = findByEmail(email);

        if (user != null) {
            delete(user);
        }
    }

    @Override
    public void deleteBySsoId(String ssoId) {
        User user = findBySsoId(ssoId);

        if (user != null) {
            delete(user);
        }
    }

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
