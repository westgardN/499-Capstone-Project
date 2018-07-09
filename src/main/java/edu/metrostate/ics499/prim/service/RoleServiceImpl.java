package edu.metrostate.ics499.prim.service;

import java.util.List;

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
     * @return a persistent Role object identified by the specified type.
     * If no Role of that type exists, null is returned.
     */
    @Override
    public Role findByType(String type) {
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
}
