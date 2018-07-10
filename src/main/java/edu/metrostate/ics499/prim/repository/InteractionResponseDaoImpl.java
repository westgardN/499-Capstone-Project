package edu.metrostate.ics499.prim.repository;

import edu.metrostate.ics499.prim.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository("interactionResponseDao")
public class InteractionResponseDaoImpl extends AbstractDao<Integer, InteractionResponse> implements InteractionResponseDao {
    static final Logger logger = LoggerFactory.getLogger(InteractionResponseDaoImpl.class);

    /**
     * Returns a persistent InteractionResponse object identified by the specified id.
     * If no InteractionResponse with that id exists, null is returned.
     *
     * @param id The Id of the InteractionResponse to retrieve.
     * @return a persistent InteractionResponse object identified by the specified id.
     * If no InteractionResponse with that id exists, null is returned.
     */
    @Override
    public InteractionResponse findById(int id) {
        InteractionResponse interactionResponse = null;

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<InteractionResponse> crit = builder.createQuery(InteractionResponse.class);
        Root<InteractionResponse> from = crit.from(InteractionResponse.class);
        Predicate clause = builder.equal(from.get("id"), id);
        crit.select(from).where(clause);
        TypedQuery<InteractionResponse> query = getSession().createQuery(crit);

        try {
            interactionResponse = query.getSingleResult();
        } catch (NoResultException ex) {
            logger.info("No InteractionResponse found with id : {}", id);
        }

        return interactionResponse;
    }

    /**
     * Returns a List of persistent InteractionResponses for the specified Interaction. If no InteractionResponses exist,
     * an empty List is returned.
     *
     * @param interaction the Interaction to retrieve a list of InteractionResponses for.
     * @return a List of persistent InteractionResponses for the specified Interaction. If no InteractionResponses exist,
     * an empty List is returned.
     */
    @Override
    public List<InteractionResponse> findByInteraction(Interaction interaction) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<InteractionResponse> crit = builder.createQuery(InteractionResponse.class);
        Root<InteractionResponse> from = crit.from(InteractionResponse.class);
        Predicate clause = builder.equal(from.get("responseTo"), interaction);
        crit.select(from).where(clause);
        TypedQuery<InteractionResponse> query = getSession().createQuery(crit);

        return query.getResultList();
    }

    /**
     * Returns a List of persistent InteractionResponses for the specified User. If no InteractionResponses exist,
     * an empty List is returned.
     *
     * @param user the User to retrieve a list of InteractionResponses for.
     * @return a List of persistent InteractionResponses for the specified User. If no InteractionResponses exist,
     * an empty List is returned.
     */
    @Override
    public List<InteractionResponse> findByUser(User user) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<InteractionResponse> crit = builder.createQuery(InteractionResponse.class);
        Root<InteractionResponse> from = crit.from(InteractionResponse.class);
        Predicate clause = builder.equal(from.get("responseBy"), user);
        crit.select(from).where(clause);
        TypedQuery<InteractionResponse> query = getSession().createQuery(crit);

        return query.getResultList();
    }

    /**
     * Returns a List of persistent InteractionResponses for the specified type. If no InteractionResponses exist,
     * an empty List is returned.
     *
     * @param interactionResponseType The type of response to retrieve a list of InteractionResponses for.
     * @return a List of persistent InteractionResponses for the specified type. If no InteractionResponses exist,
     * an empty List is returned.
     */
    @Override
    public List<InteractionResponse> findByType(InteractionResponseType interactionResponseType) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<InteractionResponse> crit = builder.createQuery(InteractionResponse.class);
        Root<InteractionResponse> from = crit.from(InteractionResponse.class);
        Predicate clause = builder.equal(from.get("type"), interactionResponseType);
        crit.select(from).where(clause);
        TypedQuery<InteractionResponse> query = getSession().createQuery(crit);

        return query.getResultList();
    }

    /**
     * Returns a List of persistent InteractionResponses for the specified state. If no InteractionResponse exist,
     * an empty List is returned.
     *
     * @param flag The flag of the InteractionResponse to retrieve a list of InteractionResponses for.
     * @return a List of persistent InteractionResponses for the specified flag. If no InteractionResponses exist,
     * an empty List is returned.
     */
    @Override
    public List<InteractionResponse> findByFlag(String flag) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<InteractionResponse> crit = builder.createQuery(InteractionResponse.class);
        Root<InteractionResponse> from = crit.from(InteractionResponse.class);
        Predicate clause = builder.equal(from.get("flag"), flag);
        crit.select(from).where(clause);
        TypedQuery<InteractionResponse> query = getSession().createQuery(crit);

        return query.getResultList();
    }

    /**
     * Returns a List of all persistent InteractionsResponses. If no InteractionResponses exist,
     * an empty List is returned.
     *
     * @return a List of all persistent InteractionsResponses. If no InteractionResponses exist,
     * an empty List is returned.
     */
    @Override
    public List<InteractionResponse> findAll() {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<InteractionResponse> crit = builder.createQuery(InteractionResponse.class);
        Root<InteractionResponse> from = crit.from(InteractionResponse.class);
        crit.select(from).orderBy(builder.asc(from.get("responseTime")));
        TypedQuery<InteractionResponse> query = getSession().createQuery(crit);
        return query.getResultList();
    }

    /**
     * Immediately saves the specified InteractionResponse to the backing store.
     *
     * @param interactionResponse the InteractionResponse to save.
     */
    @Override
    public void save(InteractionResponse interactionResponse) {
        persist(interactionResponse);
    }

    /**
     * Deletes the spcified InteractionResponse from the backing store.
     *
     * @param id the id of the InteractionResponse to delete.
     */
    @Override
    public void deleteById(int id) {
        InteractionResponse interactionResponse = findById(id);

        if (interactionResponse != null) {
            delete(interactionResponse);
        }
    }

    /**
     * Deletes all InteractionResponses from the backing store for the specified Interaction.
     *
     * @param interaction the Interaction to delete responses for.
     */
    @Override
    public void deleteByInteraction(Interaction interaction) {
        List<InteractionResponse> interactionResponses = findByInteraction(interaction);

        interactionResponses.forEach(interactionResponse -> {
            deleteById(interactionResponse.getId());
        });
    }

    /**
     * Deletes all InteractionResponses from the backing store for the specified User.
     *
     * @param user the User to delete responses for.
     */
    @Override
    public void deleteByUser(User user) {
        List<InteractionResponse> interactionResponses = findByUser(user);

        interactionResponses.forEach(interactionResponse -> {
            deleteById(interactionResponse.getId());
        });
    }
}
