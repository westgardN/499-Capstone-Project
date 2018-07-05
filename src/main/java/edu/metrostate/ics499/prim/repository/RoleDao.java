package edu.metrostate.ics499.prim.repository;

import java.util.List;

import edu.metrostate.ics499.prim.model.Role;

/**
 * The RoleDao interface defines the available persistence operations that can be performed for
 * a Role.
 */
public interface RoleDao {

    List<Role> findAll();

    Role findByType(String type);

    Role findById(int id);
}
