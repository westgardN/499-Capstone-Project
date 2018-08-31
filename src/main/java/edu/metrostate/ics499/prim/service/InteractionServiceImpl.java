package edu.metrostate.ics499.prim.service;

import edu.metrostate.ics499.prim.model.*;
import edu.metrostate.ics499.prim.provider.InteractionProvider;
import edu.metrostate.ics499.prim.repository.InteractionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The InteractionServiceImpl implements the InteractionService
 * interface for easily working with Interactions.
 */
@Service("interactionService")
@Transactional
public class InteractionServiceImpl implements InteractionService {

    @Autowired
    private InteractionDao dao;

    @Autowired
    private InteractionProviderService interactionProviderService;

    @Autowired
    private SentimentQueueItemService sentimentQueueItemService;

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
        return dao.findById(id);
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
        return dao.findBySocialNetwork(socialNetwork);
    }

    /**
     * Returns a List of persistent Interactions for the specified type. If no Interactions exist,
     * an empty List is returned.
     *
     * @param type the type to find Interactions for.
     * @return a List of persistent Interactions for the specified type. If no Interactions exist,
     * an empty List is returned.
     */
    @Override
    public List<Interaction> findByType(InteractionType type) {
        return dao.findByType(type);
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
        return dao.findByFlag(flag);
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
        return dao.findWithoutSentiment();
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
        return dao.findWithNoMessage();
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
        return dao.findAll();
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
        return dao.findAllOpen();
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
        return dao.findAllClosed();
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
        return dao.findAllDeferred();
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
        return dao.findAllDeleted();
    }

    /**
     * Immediately saves the specified Interaction to the backing store.
     *
     * @param interaction the Interaction to save.
     */
    @Override
    public void save(Interaction interaction) {
        dao.save(interaction);
    }

    /**
     * Updates the persistent Interaction based on the specified Interaction.
     *
     * @param interaction the Interaction to update.
     */
    @Override
    public void update(Interaction interaction) {
        Interaction entity = dao.findById(interaction.getId());
        if (entity != null) {
            entity.setCreatedTime(interaction.getCreatedTime());
            entity.setDescription(interaction.getDescription());
            entity.setFlag(interaction.getFlag());
            entity.setFromId(interaction.getFromId());
            entity.setFromName(interaction.getFromName());
            entity.setMessage(interaction.getMessage());
            entity.setMessageId(interaction.getMessageId());
            entity.setMessageLink(interaction.getMessageLink());
            entity.setSentiment(interaction.getSentiment());
            entity.setSocialNetwork(interaction.getSocialNetwork());
            entity.setType(interaction.getType());
            entity.setState(interaction.getState());
        }
    }

    /**
     * Deletes the specified Interaction from the backing store. If this Interaction has responses, then it will not
     * be deleted.
     *
     * @param id the id of the Interaction to delete.
     */
    @Override
    public void deleteById(int id) {
        dao.deleteById(id);
    }

    /**
     * Adds the list of Interactions.
     *
     * @param interactions
     */
    @Override
    public void addInteractions(List<Interaction> interactions) {
        for (Interaction interaction : interactions) {
            if (!dao.interactionMessageExists(interaction)) {
                save(interaction);
                sentimentQueueItemService.enqueue(interaction);
            }
        }
    }

    /**
     * Retrieves data from the available data providers and adds them as Interactions to PRIM.
     */
    @Override
    public void addInteractionsFromDataProviders() {
        for (InteractionProvider interactionProvider : interactionProviderService.getAllProviders()) {
            addInteractions(interactionProvider.getInteractions());
        }
    }

    /**
     * Returns a list that contains the count of interactions for each social network.
     *
     * @return a list that contains the count of interactions for each social network.
     */
    @Override
    public List<Object[]> interactionCountBySocialNetwork() {
        Map<SocialNetwork, Long> result = new HashMap<>();
        String queryString = "SELECT socialNetwork, Count(id) FROM Interaction GROUP BY socialNetwork";
        Query query = dao.getSession().createQuery(queryString);
        List<Object[]> results = query.getResultList();

        return results;
    }

    /**
     * Returns a list that contains the count of interactions for each state.
     *
     * @return a list that contains the count of interactions for each state.
     */
    @Override
    public List<Object[]> interactionCountByState() {
        Map<SocialNetwork, Long> result = new HashMap<>();
        String queryString = "SELECT state, Count(id) FROM Interaction GROUP BY state";
        Query query = dao.getSession().createQuery(queryString);
        List<Object[]> results = query.getResultList();

        return results;
    }

    /**
     * Returns a list of available reports along with hthe URL to get the data for the report.
     * The first element in the object array is the name of the report, and the second element
     * is the URL to get the data for that report.
     *
     * @param request the HttpServletRequest to get the host information from.
     * @return a list of available reports along with hthe URL to get the data for the report.
     */
    @Override
    public List<Object[]> getAvailableReports(HttpServletRequest request) {
        List<Object[]> result = new ArrayList<>();

        result.add(new Object[] {"Lifetime Interaction Count By Social Network", buildUrl(request, "report/interactionCountBySocialNetwork")});
        result.add(new Object[] {"Lifetime Interaction Count By State", buildUrl(request, "report/interactionCountByState")});

        return result;
    }

    /**
     * Sets the status of the specified interaction to InteractionStatus.IGNORED.
     *
     * @param id the id of the interaction to ignore.
     */
    @Override
    public void ignoreById(int id) {
        Interaction interaction = findById(id);

        if (interaction != null) {
            interaction.setState(InteractionState.IGNORED);
        }
    }

    /**
     * Sets the status of the specified interaction to InteractionStatus.CLOSED.
     *
     * @param id the id of the interaction to ignore.
     */
    @Override
    public void closeById(int id) {
        Interaction interaction = findById(id);

        if (interaction != null) {
            interaction.setState(InteractionState.CLOSED);
        }
    }

    /**
     * Sets the status of the specified interaction to InteractionStatus.FOLLOWUP.
     *
     * @param id the id of the interaction to ignore.
     */
    @Override
    public void deferById(int id) {
        Interaction interaction = findById(id);

        if (interaction != null) {
            interaction.setState(InteractionState.FOLLOWUP);
        }
    }

    /**
     * Sets the status of the specified interaction to InteractionStatus.OPEN.
     *
     * @param id the id of the interaction to ignore.
     */
    @Override
    public void reopenById(int id) {
        Interaction interaction = findById(id);

        if (interaction != null) {
            interaction.setState(InteractionState.OPEN);
        }
    }

    /**
     * This method builds a URL for an endpoint
     * @param request the request object
     * @param endPoint the end point to build a URL for.
     * @return the request URL based on the request object.
     */
    private String buildUrl(HttpServletRequest request, String endPoint) {
        final int serverPort = request.getServerPort();
        String url = "";
        if ((serverPort == 80) || (serverPort == 443)) {
            // No need to add the server port for standard HTTP and HTTPS ports, the scheme will help determine it.
            url = String.format("%s://%s/%s", request.getScheme(), request.getServerName(), endPoint);
        } else {
            url = String.format("%s://%s:%s/%s", request.getScheme(), request.getServerName(), serverPort, endPoint);
        }

        return url;
    }
}
