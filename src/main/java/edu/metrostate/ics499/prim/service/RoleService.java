package edu.metrostate.ics499.prim.service;

import java.util.List;
import java.util.Set;

import edu.metrostate.ics499.prim.model.Role;
import edu.metrostate.ics499.prim.model.RoleType;

/**
 * An interface for managing a Role by id or type that a service class would implement.
 */
public interface RoleService {

    /**
     * Returns a persistent Role object identified by the specified id.
     * If no Role with that id exists, null is returned.
     *
     * @param id the Id of the Role to retrieve.
     *
     * @return a persistent Role object identified by the specified id.
     * If no Role with that id exists, null is returned.
     */
    Role findById(int id);

    /**
     * Returns a persistent Role object identified by the specified type.
     * If no Role of that type exists, null is returned.
     *
     * @param type The type of the Role to retrieve.
     *
     * @return a persistent Role object identified by the specified type.
     * If no Role of that type exists, null is returned.
     */
    Role findByType(RoleType type);

    /**
     * Returns all of the Roles found in the backing store as a List.
     *
     * @return all of the Roles found in the backing store as a List.
     */
    List<Role> findAll();

    /**
     * Returns a Set containing the requested Role if it was found. Otherwise
     * and empty set is returned.
     *
     * @param type the Role to get
     *
     * @return a Set containing the requested Role or an empty set if the
     * requested Role doesn't exist.
     */
    Set<Role> getRoleSet(RoleType type);
}