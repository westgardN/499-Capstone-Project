package edu.metrostate.ics499.prim.service;

import edu.metrostate.ics499.prim.model.Interaction;
import edu.metrostate.ics499.prim.model.InteractionResponse;
import edu.metrostate.ics499.prim.model.InteractionResponseType;
import edu.metrostate.ics499.prim.model.User;
import edu.metrostate.ics499.prim.repository.InteractionResponseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The InteractionResponseServiceImpl implements the InteractionResponseService
 * interface for easily working with InteractionResponses.
 *
 */
@Service("interactionResponseService")
@Transactional
public class InteractionResponseServiceImpl implements InteractionResponseService {

    @Autowired
    private InteractionResponseDao dao;

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
        return dao.findById(id);
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
        return dao.findByInteraction(interaction);
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
        return dao.findByUser(user);
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
        return dao.findByType(interactionResponseType);
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
        return dao.findByFlag(flag);
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
        return dao.findAll();
    }

    /**
     * Immediately saves the specified InteractionResponse to the backing store.
     *
     * @param interactionResponse the InteractionResponse to save.
     */
    @Override
    public void save(InteractionResponse interactionResponse) {
        dao.save(interactionResponse);
    }

    /**
     * Updates the persistent InteractionResponse based on the specified Interaction.
     *
     * @param interactionResponse the InteractionResponse to update.
     */
    @Override
    public void update(InteractionResponse interactionResponse) {
        InteractionResponse entity = dao.findById(interactionResponse.getId());
        if (entity != null) {
            entity.setResponseTime(interactionResponse.getResponseTime());
            entity.setResponseTo(interactionResponse.getResponseTo());
            entity.setResponseBy(interactionResponse.getResponseBy());
            entity.setMessage(interactionResponse.getMessage());
            entity.setType(interactionResponse.getType());
        }
    }

    /**
     * Deletes the spcified InteractionResponse from the backing store.
     *
     * @param id the id of the InteractionResponse to delete.
     */
    @Override
    public void deleteById(int id) {
        dao.deleteById(id);
    }

    /**
     * Deletes all InteractionResponses from the backing store for the specified Interaction.
     *
     * @param interaction the Interaction to delete responses for.
     */
    @Override
    public void deleteByInteraction(Interaction interaction) {
        dao.deleteByInteraction(interaction);
    }

    /**
     * Deletes all InteractionResponses from the backing store for the specified User.
     *
     * @param user the User to delete responses for.
     */
    @Override
    public void deleteByUser(User user) {
        dao.deleteByUser(user);
    }
}
