package edu.metrostate.ics499.prim.repository;

import edu.metrostate.ics499.prim.model.SentimentQueueItem;

import java.util.List;

/**
 * The SentimentQueueItemDao interface defines the operations that can be performed for an
 * SentimentQueueItem.
 */
public interface SentimentQueueItemDao {

    /**
     * Returns a persistent SentimentQueueItemDao object identified by the specified id.
     * If no SentimentQueueItemDao with that id exists, null is returned.
     *
     * @param id The Id of the SentimentQueueItemDao to retrieve.
     *
     * @return a persistent SentimentQueueItemDao object identified by the specified id.
     * If no SentimentQueueItemDao with that id exists, null is returned.
     */
    SentimentQueueItem findById(int id);

    /**
     * Returns a persistent SentimentQueueItem for the specified Interaction. If no SentimentQueueItem exist,
     * null is returned.
     *
     * @param interactionId The Id of the Interaction to retrieve a SentimentQueueItem for.
     *
     * @return a persistent SentimentQueueItem for the specified Interaction. If no SentimentQueueItem exist,
     * null is returned.
     */
    SentimentQueueItem findByInteractionId(int interactionId);

    /**
     * Returns a List of persistent SentimentQueueItems for the specified User. If no SentimentQueueItems exist,
     * an empty List is returned.
     *
     * @param priority The priority of the queue items to retrieve a list of SentimentQueueItem for.
     *
     * @return a List of persistent SentimentQueueItems for the specified priority. If no SentimentQueueItems exist,
     * an empty List is returned.
     */
    List<SentimentQueueItem> findByPriority(int priority);

    /**
     * Returns a List of persistent SentimentQueueItems that have been processed. If no SentimentQueueItems exist,
     * an empty List is returned.
     *
     * @return a List of persistent SentimentQueueItems that have been processed. If no SentimentQueueItems exist,
     * an empty List is returned.
     */
    List<SentimentQueueItem> findProcessed();

    /**
     * Returns a List of persistent SentimentQueueItems waiting to be processed. If no SentimentQueueItems exist,
     * an empty List is returned.
     *
     * @return a List of persistent SentimentQueueItems waiting to be processed. If no SentimentQueueItems exist,
     * an empty List is returned.
     */
    List<SentimentQueueItem> findUnprocessed();

    /**
     * Returns a List of all persistent SentimentQueueItems. If no SentimentQueueItems exist,
     * an empty List is returned.
     *
     * @return a List of all persistent SentimentQueueItems. If no SentimentQueueItems exist,
     * an empty List is returned.
     */
    List<SentimentQueueItem> findAll();

    /**
     * Deletes the specified SentimentQueueItem from the backing store.
     *
     * @param id the id of the SentimentQueueItem to delete.
     */
    void deleteById(int id);

    /**
     * Deletes all processed SentimentsQueueItems from the backing store.
     */
    void deleteAllProcessed();

    /**
     * Deletes all unprocessed SentimentsQueueItems from the backing store.
     */
    void deleteAllUnprocessed();
}
