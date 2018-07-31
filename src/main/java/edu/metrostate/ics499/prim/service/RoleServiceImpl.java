package edu.metrostate.ics499.prim.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.metrostate.ics499.prim.model.RoleType;
import edu.metrostate.ics499.prim.repository.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.metrostate.ics499.prim.model.Role;

/**
 * The RoleService implementation class
 */
@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService{
    @Autowired
    RoleDao dao;

    /**
     * Returns a persistent Role object identified by the specified id.
     * If no Role with that id exists, null is returned.
     *
     * @param id the Id of the Role to retrieve.
     *
     * @return a persistent Role object identified by the specified id.
     * If no Role with that id exists, null is returned.
     */
    @Override
    public Role findById(int id) {
        return dao.findById(id);
    }

    /**
     * Returns a persistent Role object identified by the specified type.
     * If no Role of that type exists, null is returned.
     *
     * @param type The type of the Role to retrieve.
     *
     * @return a persistent Role object identified by the specified type.
     * If no Role of that type exists, null is returned.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Role findByType(RoleType type) {
        return dao.findByType(type);
    }

    /**
     * Returns all of the Roles found in the backing store as a List.
     *
     * @return all of the Roles found in the backing store as a List.
     */
    @Override
    public List<Role> findAll() {
        return dao.findAll();
    }

    /**
     * Returns a Set containing the requested Role if it was found. Otherwise
     * and empty set is returned.
     *
     * @param type the Role to get
     * @return a Set containing the requested Role or an empty set if the
     * requested Role doesn't exist.
     */
    @Override
    public Set<Role> getRoleSet(RoleType type) {
        Set roles = new HashSet<>();

        Role role = findByType(type);

        if (role != null) {
            roles.add(role);
        }

        return roles;
    }
}
