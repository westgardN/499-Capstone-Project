package edu.metrostate.ics499.prim.repository;

import edu.metrostate.ics499.prim.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
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
     * Returns a persistent Interaction object identified by the specified message id.
     * If no Interaction with that message id exists, null is returned.
     *
     * @param messageId the Interaction Message Id to retrieve.
     * @param socialNetwork the Social Network associated with the message.
     *
     * @return a persistent Interaction object identified by the specified message id.
     * If no Interaction with that id exists, null is returned.
     */
    @Override
    public Interaction findBySocialNetworkAndMessageId(String messageId, SocialNetwork socialNetwork) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Interaction> crit = builder.createQuery(Interaction.class);
        Root<Interaction> from = crit.from(Interaction.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(builder.equal(from.get("messageId"), messageId));
        predicates.add(builder.equal(from.get("socialNetwork"), socialNetwork));
        Predicate clause = builder.and((predicates.toArray(new Predicate[predicates.size()])));
        crit.select(from).where(clause);
        TypedQuery<Interaction> query = getSession().createQuery(crit);

        List<Interaction> result = query.getResultList();

        return result.isEmpty() ? null : result.get(0);
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
        Predicate clause = builder.equal(from.get("type"), interactionType);
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
    public List<Interaction> findByFlag(InteractionFlag flag) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Interaction> crit = builder.createQuery(Interaction.class);
        Root<Interaction> from = crit.from(Interaction.class);
        Predicate clause = builder.equal(from.get("flag"), flag);
        crit.select(from).where(clause);
        TypedQuery<Interaction> query = getSession().createQuery(crit);

        return query.getResultList();
    }

    /**
     * Returns a List of persistent Interactions for the specified state. If no Interactions exist,
     * an empty List is returned.
     *
     * @param state the flag to find Interactions for.
     * @return a List of persistent Interactions for the specified state. If no Interactions exist,
     * an empty List is returned.
     */
    @Override
    public List<Interaction> findByState(InteractionState state) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Interaction> crit = builder.createQuery(Interaction.class);
        Root<Interaction> from = crit.from(Interaction.class);
        Predicate clause = builder.equal(from.get("state"), state);
        crit.select(from).where(clause).orderBy(builder.asc(from.get("createdTime")), builder.desc(from.get("sentiment")));
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

    @Override
    public boolean interactionMessageExists(Interaction interaction) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Long> crit = builder.createQuery(Long.class);
        Root<Interaction> from = crit.from(Interaction.class);
        Predicate clause = builder.equal(from.get("messageId"), interaction.getMessageId());
        crit.select(builder.count(from)).where(clause);
        TypedQuery<Long> query = getSession().createQuery(crit);

        return query.getSingleResult() > 0;
    }

    /**
     * Returns a List of all Open persistent Interactions. If no Open Interactions exist,
     * an empty List is returned.
     *
     * @return a List of all Open persistent Interactions. If no Open Interactions exist,
     * an empty List is returned.
     */
    @Override
    public List<Interaction> findAllOpen() {
        return findByState(InteractionState.OPEN);
    }

    /**
     * Returns a List of all Closed persistent Interactions. If no Closed Interactions exist,
     * an empty List is returned.
     *
     * @return a List of all Closed persistent Interactions. If no Closed Interactions exist,
     * an empty List is returned.
     */
    @Override
    public List<Interaction> findAllClosed() {
        return findByState(InteractionState.CLOSED);
    }

    /**
     * Returns a List of all Deferred persistent Interactions. If no Deferred Interactions exist,
     * an empty List is returned.
     *
     * @return a List of all Deferred persistent Interactions. If no Deferred Interactions exist,
     * an empty List is returned.
     */
    @Override
    public List<Interaction> findAllDeferred() {
        return findByState(InteractionState.FOLLOWUP);
    }

    /**
     * Returns a List of all Deleted persistent Interactions. If no Deleted Interactions exist,
     * an empty List is returned.
     *
     * @return a List of all Deleted persistent Interactions. If no Deleted Interactions exist,
     * an empty List is returned.
     */
    @Override
    public List<Interaction> findAllDeleted() {
        return findByState(InteractionState.IGNORED);
    }
}
