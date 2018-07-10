package edu.metrostate.ics499.prim.repository;

import edu.metrostate.ics499.prim.model.SocialNetwork;
import edu.metrostate.ics499.prim.model.SocialNetworkRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository("socialNetworkRegistrationDao")
public class SocialNetworkRegistrationDaoImpl extends AbstractDao<Integer, SocialNetworkRegistration> implements SocialNetworkRegistrationDao {
    static final Logger logger = LoggerFactory.getLogger(SocialNetworkRegistrationDaoImpl.class);

    /**
     * Returns a persistent SocialNetworkRegistration object identified by the specified id.
     * If no SocialNetworkRegistration with that id exists, null is returned.
     *
     * @param id The Id of the SocialNetworkRegistration to retrieve.
     * @return a persistent SocialNetworkRegistration object identified by the specified id.
     * If no SocialNetworkRegistration with that id exists, null is returned.
     */
    @Override
    public SocialNetworkRegistration findById(int id) {
        SocialNetworkRegistration socialNetworkRegistration = null;

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<SocialNetworkRegistration> crit = builder.createQuery(SocialNetworkRegistration.class);
        Root<SocialNetworkRegistration> from = crit.from(SocialNetworkRegistration.class);
        Predicate clause = builder.equal(from.get("id"), id);
        crit.select(from).where(clause);
        TypedQuery<SocialNetworkRegistration> query = getSession().createQuery(crit);

        try {
            socialNetworkRegistration = query.getSingleResult();
        } catch (NoResultException ex) {
            logger.info("No SocialNetworkRegistration found with id : {}", id);
        }

        return socialNetworkRegistration;
    }

    /**
     * Returns a List of persistent SocialNetworkRegistrations for the specified Social Network.
     * If no SocialNetworkRegistrations exist, an empty List is returned.
     *
     * @param socialNetwork The Social Network to retrieve a list of SocialNetworkRegistrations for.
     * @return a List of persistent SocialNetworkRegistrations for the specified Social Network.
     * If no SocialNetworkRegistrations exist, an empty List is returned.
     */
    @Override
    public List<SocialNetworkRegistration> findBySocialNetwork(SocialNetwork socialNetwork) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<SocialNetworkRegistration> crit = builder.createQuery(SocialNetworkRegistration.class);
        Root<SocialNetworkRegistration> from = crit.from(SocialNetworkRegistration.class);
        Predicate clause = builder.equal(from.get("socialNetwork"), socialNetwork);
        crit.select(from).where(clause);
        TypedQuery<SocialNetworkRegistration> query = getSession().createQuery(crit);

        return query.getResultList();
    }

    /**
     * Returns a List of persistent SocialNetworkRegistrations for the specified Social Network.
     * The registrations are have not expired. If no SocialNetworkRegistrations exist, an empty List is returned.
     *
     * @param socialNetwork The Social Network to retrieve a list of non-expired SocialNetworkRegistrations for.
     * @return a List of persistent SocialNetworkRegistrations for the specified Social Network.
     * The registrations are have not expired. If no SocialNetworkRegistrations exist, an empty List is returned.
     */
    @Override
    public List<SocialNetworkRegistration> findNonExpiredBySocialNetwork(SocialNetwork socialNetwork) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<SocialNetworkRegistration> crit = builder.createQuery(SocialNetworkRegistration.class);
        Root<SocialNetworkRegistration> from = crit.from(SocialNetworkRegistration.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(builder.greaterThanOrEqualTo(from.get("expires"), new Date()));
        predicates.add(builder.equal(from.get("socialNetwork"), socialNetwork));
        Predicate clause = builder.and((predicates.toArray(new Predicate[predicates.size()])));
        crit.select(from).where(clause);
        TypedQuery<SocialNetworkRegistration> query = getSession().createQuery(crit);

        return query.getResultList();
    }

    /**
     * Returns true if at least one non-expired registration exists; otherwise false is returned.
     *
     * @param socialNetwork The social network to check for registrations.
     * @return true if at least one non-expired registration exists; otherwise false is returned.
     */
    @Override
    public boolean isRegistered(SocialNetwork socialNetwork) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Long> crit = builder.createQuery(Long.class);
        Root<SocialNetworkRegistration> from = crit.from(SocialNetworkRegistration.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(builder.greaterThanOrEqualTo(from.get("expires"), new Date()));
        predicates.add(builder.equal(from.get("socialNetwork"), socialNetwork));
        Predicate clause = builder.and((predicates.toArray(new Predicate[predicates.size()])));
        crit.select(builder.count(from)).where(clause);
        TypedQuery<Long> query = getSession().createQuery(crit);

        return query.getSingleResult() > 0;
    }

    /**
     * Returns a persistent SocialNetworkRegistration object identified by the specified id.
     * If no SocialNetworkRegistration with that token exists, null is returned.
     *
     * @param token the access token to retrieve
     * @return a persistent SocialNetworkRegistration object identified by the specified id.
     * If no SocialNetworkRegistration with that token exists, null is returned.
     */
    @Override
    public SocialNetworkRegistration findByToken(String token) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<SocialNetworkRegistration> crit = builder.createQuery(SocialNetworkRegistration.class);
        Root<SocialNetworkRegistration> from = crit.from(SocialNetworkRegistration.class);
        Predicate clause = builder.equal(from.get("token"), token);
        crit.select(from).where(clause);
        TypedQuery<SocialNetworkRegistration> query = getSession().createQuery(crit);

        SocialNetworkRegistration socialNetworkRegistration = null;

        try {
            socialNetworkRegistration = query.getSingleResult();
        } catch (NoResultException ex) {
            logger.info("No SocialNetworkRegistration found with token : {}", token);
        }

        return socialNetworkRegistration;
    }

    /**
     * Returns a List of all persistent SocialNetworkRegistrations. If no SocialNetworkRegistrations exist,
     * an empty List is returned.
     *
     * @return a List of all persistent SocialNetworkRegistrations. If no SocialNetworkRegistrations exist,
     * an empty List is returned.
     */
    @Override
    public List<SocialNetworkRegistration> findAll() {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<SocialNetworkRegistration> crit = builder.createQuery(SocialNetworkRegistration.class);
        Root<SocialNetworkRegistration> from = crit.from(SocialNetworkRegistration.class);
        crit.select(from).orderBy(builder.asc(from.get("id")));
        TypedQuery<SocialNetworkRegistration> query = getSession().createQuery(crit);
        return query.getResultList();
    }

    /**
     * Immediately saves the specified SocialNetworkRegistration to the backing store.
     *
     * @param socialNetworkRegistration the SocialNetworkRegistration to save.
     */
    @Override
    public void save(SocialNetworkRegistration socialNetworkRegistration) {
        persist(socialNetworkRegistration);
    }

    /**
     * Deletes the specified SocialNetworkRegistration from the backing store.
     *
     * @param id the id of the SocialNetworkRegistration to delete.
     */
    @Override
    public void deleteById(int id) {
        SocialNetworkRegistration socialNetworkRegistration = findById(id);

        if (socialNetworkRegistration != null) {
            delete(socialNetworkRegistration);
        }
    }

    /**
     * Deletes all SocialNetworkRegistrations from the backing store for the specified Social Network.
     *
     * @param socialNetwork the Social Network to delete registrations for.
     */
    @Override
    public void deleteBySocialNetwork(SocialNetwork socialNetwork) {
        findBySocialNetwork(socialNetwork).forEach(socialNetworkRegistration -> delete(socialNetworkRegistration));
    }
}
