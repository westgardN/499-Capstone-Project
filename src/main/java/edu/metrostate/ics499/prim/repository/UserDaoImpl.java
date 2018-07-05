package edu.metrostate.ics499.prim.repository;

import java.util.List;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import edu.metrostate.ics499.prim.model.User;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository("userDao")
public class UserDaoImpl  extends AbstractDao<Integer, User> implements UserDao {
    static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public User findById(int id) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<User> crit = builder.createQuery(User.class);
        Root<User> from = crit.from(User.class);
        Predicate clause = builder.equal(from.get("id"), id);
        crit.select(from).where(clause);
        TypedQuery<User> query = getSession().createQuery(crit);

        User user = query.getSingleResult();

        if (user != null) {
            Hibernate.initialize(user.getRoles());
        }

        return user;
    }

    @Override
    public User findByUsername(String username) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<User> crit = builder.createQuery(User.class);
        Root<User> from = crit.from(User.class);
        Predicate clause = builder.equal(from.get("username"), username);
        crit.select(from).where(clause);
        TypedQuery<User> query = getSession().createQuery(crit);

        User user = query.getSingleResult();

        if (user != null) {
            Hibernate.initialize(user.getRoles());
        }

        return user;
    }

    @Override
    public User findByEmail(String email) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<User> crit = builder.createQuery(User.class);
        Root<User> from = crit.from(User.class);
        Predicate clause = builder.equal(from.get("email"), email);
        crit.select(from).where(clause);
        TypedQuery<User> query = getSession().createQuery(crit);

        User user = query.getSingleResult();

        if (user != null) {
            Hibernate.initialize(user.getRoles());
        }

        return user;
    }

    @Override
    public User findBySsoId(String ssoId) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<User> crit = builder.createQuery(User.class);
        Root<User> from = crit.from(User.class);
        Predicate clause = builder.equal(from.get("ssoId"), ssoId);
        crit.select(from).where(clause);
        TypedQuery<User> query = getSession().createQuery(crit);

        User user = query.getSingleResult();

        if (user != null) {
            Hibernate.initialize(user.getRoles());
        }

        return user;
    }

    @Override
    public void save(User user) {
        persist(user);
    }

    @Override
    public void deleteById(int id) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<User> crit = builder.createQuery(User.class);
        Root<User> from = crit.from(User.class);
        Predicate clause = builder.equal(from.get("id"), id);
        crit.select(from).where(clause);
        TypedQuery<User> query = getSession().createQuery(crit);

        User user = query.getSingleResult();

        if (user != null) {
            delete(user);
        }
    }

    @Override
    public void deleteByUsername(String username) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<User> crit = builder.createQuery(User.class);
        Root<User> from = crit.from(User.class);
        Predicate clause = builder.equal(from.get("username"), username);
        crit.select(from).where(clause);
        TypedQuery<User> query = getSession().createQuery(crit);

        User user = query.getSingleResult();

        if (user != null) {
            delete(user);
        }
    }

    @Override
    public void deleteByEmail(String email) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<User> crit = builder.createQuery(User.class);
        Root<User> from = crit.from(User.class);
        Predicate clause = builder.equal(from.get("email"), email);
        crit.select(from).where(clause);
        TypedQuery<User> query = getSession().createQuery(crit);

        User user = query.getSingleResult();

        if (user != null) {
            delete(user);
        }
    }

    @Override
    public void deleteBySsoId(String ssoId) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<User> crit = builder.createQuery(User.class);
        Root<User> from = crit.from(User.class);
        Predicate clause = builder.equal(from.get("ssoId"), ssoId);
        crit.select(from).where(clause);
        TypedQuery<User> query = getSession().createQuery(crit);

        User user = query.getSingleResult();

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
