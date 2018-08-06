package edu.metrostate.ics499.prim.service;

import edu.metrostate.ics499.prim.model.Interaction;
import edu.metrostate.ics499.prim.model.InteractionType;
import edu.metrostate.ics499.prim.model.SocialNetwork;
import edu.metrostate.ics499.prim.provider.InteractionProvider;
import edu.metrostate.ics499.prim.repository.InteractionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    public List<Interaction> findByFlag(String flag) {
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
            if (dao.interactionMessageExists(interaction)) {
                Interaction existing = dao.findBySocialNetworkAndMessageId(interaction.getMessageId(), interaction.getSocialNetwork());
                interaction.setId(existing.getId());
                update(interaction);
            } else {
                save(interaction);
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
     * Returns a map that contains the count of interactions for each social network.
     *
     * @return a map that contains the count of interactions for each social network.
     */
    @Override
    public List<Object[]> interactionCountBySocialNetwork() {
        Map<SocialNetwork, Long> result = new HashMap<>();
        String queryString = "SELECT socialNetwork, Count(id) FROM Interaction GROUP BY socialNetwork";
        Query query = dao.getSession().createQuery(queryString);
        List<Object[]> results = query.getResultList();

//        for (int i = 0; i < results.size(); i++) {
//            Object obj[] = results.get(i);
//            result.put((SocialNetwork)obj[0], (Long)obj[1]);
//        }

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

        result.add(new Object[] {"Lifetime Interaction Count By Social Network", "http://localhost:8080/report/interactionCountBySocialNetwork"});

        return result;
    }
}
