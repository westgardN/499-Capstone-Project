package edu.metrostate.ics499.prim.repository;

import edu.metrostate.ics499.prim.model.DataRefreshRequest;
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

@Repository("dataRefreshRequestDao")
public class DataRefreshRequestDaoImpl extends AbstractDao<Integer, DataRefreshRequest> implements DataRefreshRequestDao {
    static final Logger logger = LoggerFactory.getLogger(InteractionDaoImpl.class);

    /**
     * Returns a persistent DataRefreshRequest object identified by the specified id.
     * If no DataRefreshRequest with that id exists, null is returned.
     *
     * @param id the DataRefreshRequest Id to retrieve.
     * @return a persistent DataRefreshRequest object identified by the specified id.
     * If no DataRefreshRequest with that id exists, null is returned.
     */
    @Override
    public DataRefreshRequest findById(int id) {
        DataRefreshRequest dataRefreshRequest = null;

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<DataRefreshRequest> crit = builder.createQuery(DataRefreshRequest.class);
        Root<DataRefreshRequest> from = crit.from(DataRefreshRequest.class);
        Predicate clause = builder.equal(from.get("id"), id);
        crit.select(from).where(clause);
        TypedQuery<DataRefreshRequest> query = getSession().createQuery(crit);

        try {
            dataRefreshRequest = query.getSingleResult();
        } catch (NoResultException ex) {
            logger.info("No DataRefreshRequest found with id : {}", id);
        }

        return dataRefreshRequest;
    }

    /**
     * Returns a List of persistent DataRefreshRequests for the specified request maker. If no Interactions exist,
     * an empty List is returned.
     *
     * @param requestedBy the source of this request.
     * @return a List of persistent DataRefreshRequests for the specified Social Network. If no DataRefreshRequests
     * exist, an empty List is returned.
     */
    @Override
    public List<DataRefreshRequest> findByRequestedBy(String requestedBy) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<DataRefreshRequest> crit = builder.createQuery(DataRefreshRequest.class);
        Root<DataRefreshRequest> from = crit.from(DataRefreshRequest.class);
        Predicate clause = builder.equal(from.get("requestedBy"), requestedBy);
        crit.select(from).where(clause);
        TypedQuery<DataRefreshRequest> query = getSession().createQuery(crit);

        return query.getResultList();
    }

    /**
     * Returns a List of all persistent DataRefreshRequests in the backing store.
     *
     * @return a List of all persistent DataRefreshRequests in the backing store.
     */
    @Override
    public List<DataRefreshRequest> findAll() {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<DataRefreshRequest> crit = builder.createQuery(DataRefreshRequest.class);
        Root<DataRefreshRequest> from = crit.from(DataRefreshRequest.class);
        crit.select(from).orderBy(builder.asc(from.get("createdTime")));
        TypedQuery<DataRefreshRequest> query = getSession().createQuery(crit);

        return query.getResultList();
    }

    /**
     * Returns a list of persistent DataRefreshRequests that have not started processing.
     *
     * @return a list of persistent DataRefreshRequests that have not started processing.
     */
    @Override
    public List<DataRefreshRequest> getPendingRequests() {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<DataRefreshRequest> crit = builder.createQuery(DataRefreshRequest.class);
        Root<DataRefreshRequest> from = crit.from(DataRefreshRequest.class);
        Predicate clause = builder.isNull(from.get("startTime"));
        crit.select(from).where(clause);
        TypedQuery<DataRefreshRequest> query = getSession().createQuery(crit);

        return query.getResultList();
    }

    /**
     * Returns a list of persistent DataRefreshRequests that have not started processing for the specified request
     * maker.
     *
     * @param requestedBy the maker of the requests we are retrieving.
     * @return a list of persistent DataRefreshRequests that have not started processing for the specified request
     * maker.
     */
    @Override
    public List<DataRefreshRequest> getPendingRequestsByRequestedBy(String requestedBy) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<DataRefreshRequest> crit = builder.createQuery(DataRefreshRequest.class);
        Root<DataRefreshRequest> from = crit.from(DataRefreshRequest.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.isNull(from.get("startTime")));
        predicates.add(builder.equal(from.get("requestedBy"), requestedBy));
        Predicate clause = builder.and((predicates.toArray(new Predicate[predicates.size()])));
        crit.select(from).where(clause);
        TypedQuery<DataRefreshRequest> query = getSession().createQuery(crit);

        return query.getResultList();
    }

    /**
     * Returns a list of persistent DataRefreshRequests that have not started processing for the specified request
     * type.
     *
     * @param type the type of the requests we are retrieving.
     * @return a list of persistent DataRefreshRequests that have not started processing for the specified request
     * type.
     */
    @Override
    public List<DataRefreshRequest> getPendingRequestsByType(String type) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<DataRefreshRequest> crit = builder.createQuery(DataRefreshRequest.class);
        Root<DataRefreshRequest> from = crit.from(DataRefreshRequest.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.isNull(from.get("startTime")));
        predicates.add(builder.equal(from.get("type"), type));
        Predicate clause = builder.and((predicates.toArray(new Predicate[predicates.size()])));
        crit.select(from).where(clause);
        TypedQuery<DataRefreshRequest> query = getSession().createQuery(crit);

        return query.getResultList();
    }

    /**
     * Returns a list of persistent DataRefreshRequests that have finished processing.
     *
     * @return a list of persistent DataRefreshRequests that have finished processing.
     */
    @Override
    public List<DataRefreshRequest> getProcessedRequests() {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<DataRefreshRequest> crit = builder.createQuery(DataRefreshRequest.class);
        Root<DataRefreshRequest> from = crit.from(DataRefreshRequest.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.isNotNull(from.get("startTime")));
        predicates.add(builder.isNotNull(from.get("finishTime")));
        Predicate clause = builder.and((predicates.toArray(new Predicate[predicates.size()])));
        crit.select(from).where(clause);
        TypedQuery<DataRefreshRequest> query = getSession().createQuery(crit);

        return query.getResultList();
    }

    /**
     * Returns a list of persistent DataRefreshRequests that have finished processing for the specified request
     * maker.
     *
     * @param requestedBy the maker of the requests we are retrieving.
     * @return a list of persistent DataRefreshRequests that have finished processing for the specified request
     * maker.
     */
    @Override
    public List<DataRefreshRequest> getProcessedRequestsByRequestedBy(String requestedBy) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<DataRefreshRequest> crit = builder.createQuery(DataRefreshRequest.class);
        Root<DataRefreshRequest> from = crit.from(DataRefreshRequest.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.isNotNull(from.get("startTime")));
        predicates.add(builder.isNotNull(from.get("finishTime")));
        predicates.add(builder.equal(from.get("requestedBy"), requestedBy));
        Predicate clause = builder.and((predicates.toArray(new Predicate[predicates.size()])));
        crit.select(from).where(clause);
        TypedQuery<DataRefreshRequest> query = getSession().createQuery(crit);

        return query.getResultList();
    }

    /**
     * Returns a list of persistent DataRefreshRequests that have finished processing for the specified request
     * type.
     *
     * @param type the type of the requests we are retrieving.
     * @return a list of persistent DataRefreshRequests that have finished processing for the specified request
     * type.
     */
    @Override
    public List<DataRefreshRequest> getProcessedRequestsByType(String type) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<DataRefreshRequest> crit = builder.createQuery(DataRefreshRequest.class);
        Root<DataRefreshRequest> from = crit.from(DataRefreshRequest.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.isNotNull(from.get("startTime")));
        predicates.add(builder.isNotNull(from.get("finishTime")));
        predicates.add(builder.equal(from.get("type"), type));
        Predicate clause = builder.and((predicates.toArray(new Predicate[predicates.size()])));
        crit.select(from).where(clause);
        TypedQuery<DataRefreshRequest> query = getSession().createQuery(crit);

        return query.getResultList();
    }

    /**
     * Immediately saves the specified DataRefreshRequest to the backing store.
     *
     * @param dataRefreshRequest the DataRefreshRequest to save.
     */
    @Override
    public void save(DataRefreshRequest dataRefreshRequest) {
        persist(dataRefreshRequest);
    }

    /**
     * Deletes the spcified DataRefreshRequest from the backing store.
     *
     * @param id the id of the DataRefreshRequest to delete.
     */
    @Override
    public void deleteById(int id) {
        DataRefreshRequest dataRefreshRequest = findById(id);

        if (dataRefreshRequest != null) {
            delete(dataRefreshRequest);
        }
    }
}
