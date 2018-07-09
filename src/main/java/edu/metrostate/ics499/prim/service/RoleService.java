package edu.metrostate.ics499.prim.service;

import java.util.List;

import edu.metrostate.ics499.prim.model.Role;

/**
 * An interface for managing a Role by id or type that a service class would implement.
 */
public interface RoleService {

    /**
     * Returns a persistent Role object identified by the specified id.
     * If no Role with that id exists, null is returned.
     *
     * @return a persistent Role object identified by the specified id.
     * If no Role with that id exists, null is returned.
     */
    Role findById(int id);

    /**
     * Returns a persistent Role object identified by the specified type.
     * If no Role of that type exists, null is returned.
     *
     * @return a persistent Role object identified by the specified type.
     * If no Role of that type exists, null is returned.
     */
    Role findByType(String type);

    /**
     * Returns all of the Roles found in the backing store as a List.
     *
     * @return all of the Roles found in the backing store as a List.
     */
    List<Role> findAll();

}