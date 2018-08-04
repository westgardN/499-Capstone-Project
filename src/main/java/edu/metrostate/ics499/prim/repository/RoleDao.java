package edu.metrostate.ics499.prim.repository;

import java.util.List;

import edu.metrostate.ics499.prim.model.Role;
import edu.metrostate.ics499.prim.model.RoleType;

/**
 * The RoleDao interface defines the available persistence operations that can be performed for
 * a Role.
 */
public interface RoleDao extends IRepository {

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
     * @param type the type of Role to find.
     *
     * @return a persistent Role object identified by the specified type.
     * If no Role of that type exists, null is returned.
     */
    Role findByType(RoleType type);

    /**
     * Returns a persistent Role object identified by the specified id.
     * If no Role with that id exists, null is returned.
     *
     * @param id the Id of the Role to find.
     *
     * @return a persistent Role object identified by the specified id.
     * If no Role with that id exists, null is returned.
     */
    Role findById(int id);
}
