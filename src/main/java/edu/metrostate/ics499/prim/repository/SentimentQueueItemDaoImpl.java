package edu.metrostate.ics499.prim.repository;

import edu.metrostate.ics499.prim.model.Interaction;
import edu.metrostate.ics499.prim.model.SentimentQueueItem;
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

@Repository("sentimentQueueItemDao")
public class SentimentQueueItemDaoImpl extends AbstractDao<Integer, SentimentQueueItem> implements SentimentQueueItemDao {
    static final Logger logger = LoggerFactory.getLogger(SentimentQueueItemDaoImpl.class);

    /**
     * Returns a persistent SentimentQueueItemDao object identified by the specified id.
     * If no SentimentQueueItemDao with that id exists, null is returned.
     *
     * @param id The Id of the SentimentQueueItemDao to retrieve.
     * @return a persistent SentimentQueueItemDao object identified by the specified id.
     * If no SentimentQueueItemDao with that id exists, null is returned.
     */
    @Override
    public SentimentQueueItem findById(int id) {
        SentimentQueueItem sentimentQueueItem = null;

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<SentimentQueueItem> crit = builder.createQuery(SentimentQueueItem.class);
        Root<SentimentQueueItem> from = crit.from(SentimentQueueItem.class);
        Predicate clause = builder.equal(from.get("id"), id);
        crit.select(from).where(clause);
        TypedQuery<SentimentQueueItem> query = getSession().createQuery(crit);

        try {
            sentimentQueueItem = query.getSingleResult();
        } catch (NoResultException ex) {
            logger.info("No SentimentQueueItem found with id : {}", id);
        }

        return sentimentQueueItem;
    }

    /**
     * Returns a List of persistent SentimentQueueItems for the specified Interaction. If no SentimentQueueItems exist,
     * an empty list is returned.
     *
     * @param interaction the Interaction to retrieve a SentimentQueueItem for.
     *
     * @return a List of persistent SentimentQueueItems for the specified Interaction. If no SentimentQueueItems exist,
     * an empty list is returned.
     */
    @Override
    public List<SentimentQueueItem> findByInteraction(Interaction interaction) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<SentimentQueueItem> crit = builder.createQuery(SentimentQueueItem.class);
        Root<SentimentQueueItem> from = crit.from(SentimentQueueItem.class);
        Predicate clause = builder.equal(from.get("interaction"), interaction);
        crit.select(from).where(clause);
        TypedQuery<SentimentQueueItem> query = getSession().createQuery(crit);

        return query.getResultList();
    }

    /**
     * Returns a List of persistent SentimentQueueItems for the specified User. If no SentimentQueueItems exist,
     * an empty List is returned.
     *
     * @param priority The priority of the queue items to retrieve a list of SentimentQueueItem for.
     * @return a List of persistent SentimentQueueItems for the specified priority. If no SentimentQueueItems exist,
     * an empty List is returned.
     */
    @Override
    public List<SentimentQueueItem> findByPriority(int priority) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<SentimentQueueItem> crit = builder.createQuery(SentimentQueueItem.class);
        Root<SentimentQueueItem> from = crit.from(SentimentQueueItem.class);
        Predicate clause = builder.equal(from.get("priority"), priority);
        crit.select(from).where(clause).orderBy(builder.asc(from.get("priority")));
        TypedQuery<SentimentQueueItem> query = getSession().createQuery(crit);

        return query.getResultList();
    }

    /**
     * Returns a List of persistent SentimentQueueItems that have been processed. If no SentimentQueueItems exist,
     * an empty List is returned.
     *
     * @return a List of persistent SentimentQueueItems that have been processed. If no SentimentQueueItems exist,
     * an empty List is returned.
     */
    @Override
    public List<SentimentQueueItem> findProcessed() {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<SentimentQueueItem> crit = builder.createQuery(SentimentQueueItem.class);
        Root<SentimentQueueItem> from = crit.from(SentimentQueueItem.class);
        Predicate clause = builder.equal(from.get("processed"), true);
        crit.select(from).where(clause);
        TypedQuery<SentimentQueueItem> query = getSession().createQuery(crit);

        return query.getResultList();
    }

    /**
     * Returns a List of persistent SentimentQueueItems waiting to be processed. If no SentimentQueueItems exist,
     * an empty List is returned.
     *
     * @return a List of persistent SentimentQueueItems waiting to be processed. If no SentimentQueueItems exist,
     * an empty List is returned.
     */
    @Override
    public List<SentimentQueueItem> findUnprocessed() {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<SentimentQueueItem> crit = builder.createQuery(SentimentQueueItem.class);
        Root<SentimentQueueItem> from = crit.from(SentimentQueueItem.class);
        Predicate clause = builder.equal(from.get("processed"), false);
        crit.select(from).where(clause);
        TypedQuery<SentimentQueueItem> query = getSession().createQuery(crit);

        return query.getResultList();
    }

    /**
     * Returns a List of all persistent SentimentQueueItems. If no SentimentQueueItems exist,
     * an empty List is returned.
     *
     * @return a List of all persistent SentimentQueueItems. If no SentimentQueueItems exist,
     * an empty List is returned.
     */
    @Override
    public List<SentimentQueueItem> findAll() {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<SentimentQueueItem> crit = builder.createQuery(SentimentQueueItem.class);
        Root<SentimentQueueItem> from = crit.from(SentimentQueueItem.class);
        crit.select(from).orderBy(builder.asc(from.get("priority")), builder.asc(from.get("createdTime")));
        TypedQuery<SentimentQueueItem> query = getSession().createQuery(crit);
        return query.getResultList();
    }

    /**
     * Immediately saves the specified SentimentQueueItem to the backing store.
     *
     * @param sentimentQueueItem the SentimentQueueItem to save.
     */
    @Override
    public void save(SentimentQueueItem sentimentQueueItem) {
        persist(sentimentQueueItem);
    }

    /**
     * Deletes the specified SentimentQueueItem from the backing store.
     *
     * @param id the id of the SentimentQueueItem to delete.
     */
    @Override
    public void deleteById(int id) {
        SentimentQueueItem sentimentQueueItem = findById(id);

        if (sentimentQueueItem != null) {
            delete(sentimentQueueItem);
        }
    }

    /**
     * Deletes all processed SentimentsQueueItems from the backing store.
     */
    @Override
    public void deleteAllProcessed() {
        findProcessed().forEach(sentimentQueueItem -> delete(sentimentQueueItem));
    }

    /**
     * Deletes all unprocessed SentimentsQueueItems from the backing store.
     */
    @Override
    public void deleteAllUnprocessed() {
        findUnprocessed().forEach(sentimentQueueItem -> delete(sentimentQueueItem));
    }
}
