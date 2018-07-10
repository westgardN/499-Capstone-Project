package edu.metrostate.ics499.prim.repository;

import java.util.List;

import edu.metrostate.ics499.prim.model.Role;
import edu.metrostate.ics499.prim.model.RoleType;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

@Repository("roleDao")
public class RoleDaoImpl extends AbstractDao<Integer, Role> implements RoleDao{

    /**
     * Returns a persistent Role object identified by the specified id.
     * If no Role with that id exists, null is returned.
     *
     * @param id the Id of the Role to find.
     *
     * @return a persistent Role object identified by the specified id.
     * If no Role with that id exists, null is returned.
     */
    public Role findById(int id) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Role> crit = builder.createQuery(Role.class);
        Root<Role> from = crit.from(Role.class);
        Predicate clause = builder.equal(from.get("id"), id);
        crit.select(from).where(clause);
        TypedQuery<Role> query = getSession().createQuery(crit);
        return query.getSingleResult();
    }

    /**
     * Returns a persistent Role object identified by the specified type.
     * If no Role of that type exists, null is returned.
     *
     * @param type the type of Role to find.
     *
     * @return a persistent Role object identified by the specified type.
     * If no Role of that type exists, null is returned.
     */
    public Role findByType(RoleType type) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Role> crit = builder.createQuery(Role.class);
        Root<Role> from = crit.from(Role.class);
        Predicate clause = builder.equal(from.get("type"), type);
        crit.select(from).where(clause);
        TypedQuery<Role> query = getSession().createQuery(crit);
        return query.getSingleResult();
    }

    /**
     * Returns all of the Roles found in the backing store as a List.
     *
     * @return all of the Roles found in the backing store as a List.
     */
    public List<Role> findAll(){
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Role> crit = builder.createQuery(Role.class);
        Root<Role> from = crit.from(Role.class);
        crit.select(from).orderBy(builder.asc(from.get("type")));
        TypedQuery<Role> query = getSession().createQuery(crit);
        return query.getResultList();
    }

}