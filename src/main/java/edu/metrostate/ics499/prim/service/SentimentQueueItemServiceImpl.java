package edu.metrostate.ics499.prim.service;

import edu.metrostate.ics499.prim.model.Interaction;
import edu.metrostate.ics499.prim.model.SentimentQueueItem;
import edu.metrostate.ics499.prim.repository.SentimentQueueItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The SentimentQueueItemServiceImpl implements the SentimentQueueItemService
 * interface for easily working with the SentimentQueueItems.
 */
@Service("sentimentQueueItemService")
@Transactional
public class SentimentQueueItemServiceImpl implements SentimentQueueItemService {

    @Autowired
    private SentimentQueueItemDao dao;

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
        return dao.findById(id);
    }

    /**
     * Returns a List of persistent SentimentQueueItems for the specified Interaction. If no SentimentQueueItems exist,
     * an empty list is returned.
     *
     * @param interaction the Interaction to retrieve a SentimentQueueItem for.
     * @return a List of persistent SentimentQueueItems for the specified Interaction. If no SentimentQueueItems exist,
     * an empty list is returned.
     */
    @Override
    public List<SentimentQueueItem> findByInteraction(Interaction interaction) {
        return dao.findByInteraction(interaction);
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
        return dao.findByPriority(priority);
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
        return dao.findProcessed();
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
        return findUnprocessed();
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
        return dao.findAll();
    }

    /**
     * Immediately saves the specified SentimentQueueItem to the backing store.
     *
     * @param sentimentQueueItem the SentimentQueueItem to save.
     */
    @Override
    public void save(SentimentQueueItem sentimentQueueItem) {
        dao.save(sentimentQueueItem);
    }

    /**
     * Updates the persistent SentimentQueueItem based on the specified SentimentQueueItem.
     *
     * @param sentimentQueueItem the SentimentQueueItem to update.
     */
    @Override
    public void update(SentimentQueueItem sentimentQueueItem) {
        SentimentQueueItem entity = dao.findById(sentimentQueueItem.getId());

        if (entity != null) {
            entity.setCreatedTime(sentimentQueueItem.getCreatedTime());
            entity.setInteraction(sentimentQueueItem.getInteraction());
            entity.setPriority(sentimentQueueItem.getPriority());
            entity.setProcessed(sentimentQueueItem.isProcessed());
        }
    }

    /**
     * Deletes the specified SentimentQueueItem from the backing store.
     *
     * @param id the id of the SentimentQueueItem to delete.
     */
    @Override
    public void deleteById(int id) {
        dao.deleteById(id);
    }

    /**
     * Deletes all processed SentimentsQueueItems from the backing store.
     */
    @Override
    public void deleteAllProcessed() {
        dao.deleteAllProcessed();
    }

    /**
     * Deletes all unprocessed SentimentsQueueItems from the backing store.
     */
    @Override
    public void deleteAllUnprocessed() {
        dao.deleteAllUnprocessed();
    }
}
