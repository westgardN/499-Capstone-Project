package edu.metrostate.ics499.prim.repository;

import java.util.List;

import edu.metrostate.ics499.prim.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

@Repository("roleDao")
public class RoleDaoImpl extends AbstractDao<Integer, Role> implements RoleDao{

    public Role findById(int id) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Role> crit = builder.createQuery(Role.class);
        Root<Role> from = crit.from(Role.class);
        Predicate clause = builder.equal(from.get("id"), id);
        crit.select(from).where(clause);
        TypedQuery<Role> query = getSession().createQuery(crit);
        return query.getSingleResult();
    }

    public Role findByType(String type) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Role> crit = builder.createQuery(Role.class);
        Root<Role> from = crit.from(Role.class);
        Predicate clause = builder.equal(from.get("type"), type);
        crit.select(from).where(clause);
        TypedQuery<Role> query = getSession().createQuery(crit);
        return query.getSingleResult();
    }

    public List<Role> findAll(){
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Role> crit = builder.createQuery(Role.class);
        Root<Role> from = crit.from(Role.class);
        crit.select(from).orderBy(builder.asc(from.get("type")));
        TypedQuery<Role> query = getSession().createQuery(crit);
        return query.getResultList();
    }

}