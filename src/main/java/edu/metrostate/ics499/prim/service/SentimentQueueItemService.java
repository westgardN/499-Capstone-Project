package edu.metrostate.ics499.prim.service;

import edu.metrostate.ics499.prim.model.Interaction;
import edu.metrostate.ics499.prim.model.SentimentQueueItem;

import java.util.List;

/**
 * The SentimentQueueItemService provides an interface for easily working with SentimentQueueItems.
 */
public interface SentimentQueueItemService {
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
     * Returns a List of persistent SentimentQueueItems for the specified Interaction. If no SentimentQueueItems exist,
     * an empty list is returned.
     *
     * @param interaction the Interaction to retrieve a SentimentQueueItem for.
     *
     * @return a List of persistent SentimentQueueItems for the specified Interaction. If no SentimentQueueItems exist,
     * an empty list is returned.
     */
    List<SentimentQueueItem> findByInteraction(Interaction interaction);

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
     * Immediately saves the specified SentimentQueueItem to the backing store.
     *
     * @param sentimentQueueItem the SentimentQueueItem to save.
     */
    void save(SentimentQueueItem sentimentQueueItem);

    /**
     * Updates the persistent SentimentQueueItem based on the specified SentimentQueueItem.
     *
     * @param sentimentQueueItem the SentimentQueueItem to update.
     */
    void update(SentimentQueueItem sentimentQueueItem);

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

    /**
     * Adds the interaction to the queue
     *
     * @param interaction the interaction to add to the queue.
     */
    void enqueue(Interaction interaction);

    /**
     * Removes the next item in the queue and returns it. If the queue is empty an
     * exception is thrown.
     *
     * @return the next item in the queue to process.
     */
    Interaction dequeue();

    /**
     * Returns true if the queue is empty.
     *
     * @return true if the queue is empty.
     */
    boolean isEmpty();

    /**
     * Returns the next item in the queue but does not remove it from
     * the queue. If the queue is empty an
     * exception is thrown.
     *
     * @return the next item in the queue to process.
     */
    Interaction peek();
}
