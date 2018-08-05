package edu.metrostate.ics499.prim.repository;

import java.io.Serializable;

import java.lang.reflect.ParameterizedType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Abstract base class for all repositories. Provides basic functionality that all repositories need.
 *
 * @param <PK> The type of the Primary Key
 * @param <T> The type of persistent objects stored in the repository
 */
public abstract class AbstractDao<PK extends Serializable, T> {

    private final Class<T> persistentClass;

    /**
     * No-arg constructor to initialize the type
     */
    @SuppressWarnings("unchecked")
    public AbstractDao(){
        this.persistentClass =(Class<T>) (
                (ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Returns a Hibernate Session object that can be used to JPA operations.
     *
     * @return a Hibernate Session object that can be used to JPA operations.
     */
    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    /**
     * Returns an object of the class type based ont he Primary Key.
     *
     * @param key The identifier of the object to retrieve.
     *
     * @return an object of the class type based ont he Primary Key.
     */
    @SuppressWarnings("unchecked")
    public T getByKey(PK key) {
        return (T) getSession().get(persistentClass, key);
    }

    /**
     * Persists the specified object. Persistence only happens within a Transaction and the identifier
     * value of this object may not be retrieved immediately. Primarily used for saving new objects to the
     * backing store.
     *
     * @param entity The object to persist.
     *
     */
    public void persist(T entity) {
        getSession().persist(entity);
    }

    /**
     * Transitions the specified object from detached to persistent. Used to update an existing object the
     * backing store. Calling update on an object that doesn't exist in the backing store will result
     * in an exception being thrown by Hibernate.
     *
     * @param entity The object to transition.
     */
    public void update(T entity) {
        getSession().update(entity);
    }

    /**
     * Deletes the specified object from the backing store. If the object doesn't exist, an exception from
     * Hibernate is thrown.
     *
     * @param entity The object to delete.
     */
    public void delete(T entity) {
        getSession().delete(entity);
    }

    /**
     * A shortcut method to quickly get access to the session's JPA criteria builder instance.
     *
     * @return a JPA CriteriaBuilder that can be used to build and execute queries.
     */
    public CriteriaBuilder getCriteriaBuilder() {
        return getSession().getCriteriaBuilder();
    }

}