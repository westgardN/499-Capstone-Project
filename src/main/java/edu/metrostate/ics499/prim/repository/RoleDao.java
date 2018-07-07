package edu.metrostate.ics499.prim.repository;

import java.util.List;

import edu.metrostate.ics499.prim.model.Role;

/**
 * The RoleDao interface defines the available persistence operations that can be performed for
 * a Role.
 */
public interface RoleDao {

    /**
     * Returns all of the Roles found in the backing store as a List.
     *
     * @return all of the Roles found in the backing store as a List.
     */
    List<Role> findAll();

    /**
     * Returns a persistent Role object identified by the specified type.
     * If no Role of that type exists, null is returned.
     *
     * @return a persistent Role object identified by the specified type.
     * If no Role of that type exists, null is returned.
     */
    Role findByType(String type);

    /**
     * Returns a persistent Role object identified by the specified id.
     * If no Role with that id exists, null is returned.
     *
     * @return a persistent Role object identified by the specified id.
     * If no Role with that id exists, null is returned.
     */
    Role findById(int id);
}
