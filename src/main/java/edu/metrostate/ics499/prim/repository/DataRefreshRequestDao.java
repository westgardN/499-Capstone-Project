package edu.metrostate.ics499.prim.repository;

import edu.metrostate.ics499.prim.model.DataRefreshRequest;

import java.util.List;

/**
 * The DataRefreshRequestDao interface defines the operations that can be performed for a DataRefreshRequest.
 */
public interface DataRefreshRequestDao extends IRepository {

    /**
     * Returns a persistent DataRefreshRequest object identified by the specified id.
     * If no DataRefreshRequest with that id exists, null is returned.
     *
     * @param id the DataRefreshRequest Id to retrieve.
     *
     * @return a persistent DataRefreshRequest object identified by the specified id.
     * If no DataRefreshRequest with that id exists, null is returned.
     */
    DataRefreshRequest findById(int id);

    /**
     * Returns a List of persistent DataRefreshRequests for the specified request maker. If no Interactions exist,
     * an empty List is returned.
     *
     * @param requestedBy the source of this request.
     * @return a List of persistent DataRefreshRequests for the specified Social Network. If no DataRefreshRequests
     * exist, an empty List is returned.
     */
    List<DataRefreshRequest> findByRequestedBy(String requestedBy);

    /**
     * Returns a List of all persistent DataRefreshRequests in the backing store.
     *
     * @return a List of all persistent DataRefreshRequests in the backing store.
     */
    List<DataRefreshRequest> findAll();

    /**
     * Returns a list of persistent DataRefreshRequests that have not started processing.
     *
     * @return a list of persistent DataRefreshRequests that have not started processing.
     */
    List<DataRefreshRequest> getPendingRequests();

    /**
     * Returns a list of persistent DataRefreshRequests that have not started processing for the specified request
     * maker.
     *
     * @param requestedBy the maker of the requests we are retrieving.
     *
     * @return a list of persistent DataRefreshRequests that have not started processing for the specified request
     * maker.
     */
    List<DataRefreshRequest> getPendingRequestsByRequestedBy(String requestedBy);

    /**
     * Returns a list of persistent DataRefreshRequests that have not started processing for the specified request
     * type.
     *
     * @param type the type of the requests we are retrieving.
     *
     * @return a list of persistent DataRefreshRequests that have not started processing for the specified request
     * type.
     */
    List<DataRefreshRequest> getPendingRequestsByType(String type);

    /**
     * Returns a list of persistent DataRefreshRequests that have finished processing.
     *
     * @return a list of persistent DataRefreshRequests that have finished processing.
     */
    List<DataRefreshRequest> getProcessedRequests();

    /**
     * Returns a list of persistent DataRefreshRequests that have finished processing for the specified request
     * maker.
     *
     * @param requestedBy the maker of the requests we are retrieving.
     *
     * @return a list of persistent DataRefreshRequests that have finished processing for the specified request
     * maker.
     */
    List<DataRefreshRequest> getProcessedRequestsByRequestedBy(String requestedBy);

    /**
     * Returns a list of persistent DataRefreshRequests that have finished processing for the specified request
     * type.
     *
     * @param type the type of the requests we are retrieving.
     *
     * @return a list of persistent DataRefreshRequests that have finished processing for the specified request
     * type.
     */
    List<DataRefreshRequest> getProcessedRequestsByType(String type);

    /**
     * Immediately saves the specified DataRefreshRequest to the backing store.
     *
     * @param dataRefreshRequest the DataRefreshRequest to save.
     */
    void save(DataRefreshRequest dataRefreshRequest);

    /**
     * Deletes the spcified DataRefreshRequest from the backing store.
     *
     * @param id the id of the DataRefreshRequest to delete.
     */
    void deleteById(int id);
}
