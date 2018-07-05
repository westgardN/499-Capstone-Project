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

    @Override
    public Role findById(int id) {
        return dao.findById(id);
    }

    @Override
    public Role findByType(String type) {
        return dao.findByType(type);
    }

    @Override
    public List<Role> findAll() {
        return dao.findAll();
    }
}
