package edu.metrostate.ics499.prim.service;

import java.util.List;

import edu.metrostate.ics499.prim.model.Role;

/**
 * An interface for managing a Role by id or type that a service class would implement.
 */
public interface RoleService {

    Role findById(int id);

    Role findByType(String type);

    List<Role> findAll();

}