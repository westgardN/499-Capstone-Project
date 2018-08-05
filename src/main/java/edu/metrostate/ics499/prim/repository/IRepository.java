package edu.metrostate.ics499.prim.repository;

import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;

public interface IRepository {
    /**
     * Returns a Hibernate Session object that can be used to JPA operations.
     *
     * @return a Hibernate Session object that can be used to JPA operations.
     */
    Session getSession();

    /**
     * A shortcut method to quickly get access to the session's JPA criteria builder instance.
     *
     * @return a JPA CriteriaBuilder that can be used to build and execute queries.
     */
    CriteriaBuilder getCriteriaBuilder();
}
