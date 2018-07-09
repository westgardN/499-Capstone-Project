package edu.metrostate.ics499.prim.service;

import edu.metrostate.ics499.prim.model.Interaction;
import edu.metrostate.ics499.prim.repository.InteractionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("interactionService")
@Transactional
public class InteractionServiceImpl implements InteractionService {

    @Autowired
    private InteractionDao dao;

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
    public List<Interaction> findBySocialNetwork(String socialNetwork) {
        return dao.findBySocialNetwork(socialNetwork);
    }

    /**
     * Returns a List of persistent Interactions for the specified source. If no Interactions exist,
     * an empty List is returned.
     *
     * @param source the source to find Interactions for.
     * @return a List of persistent Interactions for the specified source. If no Interactions exist,
     * an empty List is returned.
     */
    @Override
    public List<Interaction> findBySource(String source) {
        return dao.findBySource(source);
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
            entity.setSource(interaction.getSource());
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
}
