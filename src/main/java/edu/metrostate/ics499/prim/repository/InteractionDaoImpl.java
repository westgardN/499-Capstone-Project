package edu.metrostate.ics499.prim.repository;

import edu.metrostate.ics499.prim.model.Interaction;
import edu.metrostate.ics499.prim.model.InteractionType;
import edu.metrostate.ics499.prim.model.SocialNetwork;
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
import java.util.List;

@Repository("interactionDao")
public class InteractionDaoImpl extends AbstractDao<Integer, Interaction> implements InteractionDao {
    static final Logger logger = LoggerFactory.getLogger(InteractionDaoImpl.class);

    /**
     * Returns a persistent Interaction object identified by the specified id.
     * If no Interaction with that id exists, null is returned.
     *
     * @param id the Interaction Id to retrieve.
     * @return a persistent Interaction object identified by the specified id.
     * If no Interaction with that id exists, null is returned.
     */
    @Override
    public Interaction findById(int id) {
        Interaction interaction = null;

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Interaction> crit = builder.createQuery(Interaction.class);
        Root<Interaction> from = crit.from(Interaction.class);
        Predicate clause = builder.equal(from.get("id"), id);
        crit.select(from).where(clause);
        TypedQuery<Interaction> query = getSession().createQuery(crit);

        try {
            interaction = query.getSingleResult();
        } catch (NoResultException ex) {
            logger.info("No Interaction found with id : {}", id);
        }

        return interaction;
    }

    /**
     * Returns a List of persistent Interactions for the specified Social Network. If no Interactions exist,
     * an empty List is returned.
     *
     * @param socialNetwork the Social Network to find Interactions for.
     * @return a List of persistent Interactions for the specified Social Network. If no Interactions exist,
     * an empty List is returned.
     */
    @Override
    public List<Interaction> findBySocialNetwork(SocialNetwork socialNetwork) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Interaction> crit = builder.createQuery(Interaction.class);
        Root<Interaction> from = crit.from(Interaction.class);
        Predicate clause = builder.equal(from.get("socialNetwork"), socialNetwork);
        crit.select(from).where(clause);
        TypedQuery<Interaction> query = getSession().createQuery(crit);

        return query.getResultList();
    }

    /**
     * Returns a List of persistent Interactions for the specified type. If no Interactions exist,
     * an empty List is returned.
     *
     * @param interactionType the type to find Interactions for.
     * @return a List of persistent Interactions for the specified type. If no Interactions exist,
     * an empty List is returned.
     */
    @Override
    public List<Interaction> findByType(InteractionType interactionType) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Interaction> crit = builder.createQuery(Interaction.class);
        Root<Interaction> from = crit.from(Interaction.class);
        Predicate clause = builder.equal(from.get("source"), interactionType);
        crit.select(from).where(clause);
        TypedQuery<Interaction> query = getSession().createQuery(crit);

        return query.getResultList();
    }

    /**
     * Returns a List of persistent Interactions for the specified flag. If no Interactions exist,
     * an empty List is returned.
     *
     * @param flag the flag to find Interactions for.
     * @return a List of persistent Interactions for the specified flag. If no Interactions exist,
     * an empty List is returned.
     */
    @Override
    public List<Interaction> findByFlag(String flag) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Interaction> crit = builder.createQuery(Interaction.class);
        Root<Interaction> from = crit.from(Interaction.class);
        Predicate clause = builder.equal(from.get("flag"), flag);
        crit.select(from).where(clause);
        TypedQuery<Interaction> query = getSession().createQuery(crit);

        return query.getResultList();
    }

    /**
     * Returns a List of persistent Interactions that have no sentiment score. If no Interactions exist,
     * an empty List is returned.
     *
     * @return a List of persistent Interactions that have no sentiment score. If no Interactions exist,
     * an empty List is returned.
     */
    @Override
    public List<Interaction> findWithoutSentiment() {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Interaction> crit = builder.createQuery(Interaction.class);
        Root<Interaction> from = crit.from(Interaction.class);
        Predicate clause = builder.isNull(from.get("sentiment"));
        crit.select(from).where(clause);
        TypedQuery<Interaction> query = getSession().createQuery(crit);

        return query.getResultList();
    }

    /**
     * Returns a List of persistent Interactions that have no message. If no Interactions exist,
     * an empty List is returned.
     *
     * @return a List of persistent Interactions that have no message. If no Interactions exist,
     * an empty List is returned.
     */
    @Override
    public List<Interaction> findWithNoMessage() {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Interaction> crit = builder.createQuery(Interaction.class);
        Root<Interaction> from = crit.from(Interaction.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(builder.lessThan(from.get("sentiment"), 0));
        predicates.add(builder.isNull(from.get("message")));
        predicates.add(builder.isEmpty(from.get("message")));
        Predicate clause = builder.or((predicates.toArray(new Predicate[predicates.size()])));
        crit.select(from).where(clause);
        TypedQuery<Interaction> query = getSession().createQuery(crit);

        return query.getResultList();
    }

    /**
     * Returns a List of all persistent Interactions. If no Interactions exist,
     * an empty List is returned.
     *
     * @return a List of all persistent Interactions. If no Interactions exist,
     * an empty List is returned.
     */
    @Override
    public List<Interaction> findAll() {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Interaction> crit = builder.createQuery(Interaction.class);
        Root<Interaction> from = crit.from(Interaction.class);
        crit.select(from).orderBy(builder.asc(from.get("createdTime")));
        TypedQuery<Interaction> query = getSession().createQuery(crit);
        return query.getResultList();
    }

    /**
     * Immediately saves the specified Interaction to the backing store.
     *
     * @param interaction the Interaction to save.
     */
    @Override
    public void save(Interaction interaction) {
        persist(interaction);
    }

    /**
     * Deletes the spcified Interaction from the backing store. If this Interaction has responses, then it will not
     * be deleted.
     *
     * @param id the id of the Interaction to delete.
     */
    @Override
    public void deleteById(int id) {
        Interaction interaction = findById(id);

        if (interaction != null) {
            delete(interaction);
        }
    }
}
