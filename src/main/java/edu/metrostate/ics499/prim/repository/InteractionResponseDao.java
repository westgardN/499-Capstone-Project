package edu.metrostate.ics499.prim.repository;

import edu.metrostate.ics499.prim.model.*;

import java.util.List;

/**
 * The InteractionResponseDao interface defines the operations that can be performed for an
 * InteractionResponse.
 */
public interface InteractionResponseDao extends IRepository {

    /**
     * Returns a persistent InteractionResponse object identified by the specified id.
     * If no InteractionResponse with that id exists, null is returned.
     *
     * @param id The Id of the InteractionResponse to retrieve.
     *
     * @return a persistent InteractionResponse object identified by the specified id.
     * If no InteractionResponse with that id exists, null is returned.
     */
    InteractionResponse findById(int id);

    /**
     * Returns a List of persistent InteractionResponses for the specified Interaction. If no InteractionResponses exist,
     * an empty List is returned.
     *
     * @param interaction the Interaction to retrieve a list of InteractionResponses for.
     *
     * @return a List of persistent InteractionResponses for the specified Interaction. If no InteractionResponses exist,
     * an empty List is returned.
     */
    List<InteractionResponse> findByInteraction(Interaction interaction);

    /**
     * Returns a List of persistent InteractionResponses for the specified User. If no InteractionResponses exist,
     * an empty List is returned.
     *
     * @param user the User to retrieve a list of InteractionResponses for.
     *
     * @return a List of persistent InteractionResponses for the specified User. If no InteractionResponses exist,
     * an empty List is returned.
     */
    List<InteractionResponse> findByUser(User user);

    /**
     * Returns a List of persistent InteractionResponses for the specified type. If no InteractionResponses exist,
     * an empty List is returned.
     *
     * @param interactionResponseType The type of response to retrieve a list of InteractionResponses for.
     *
     * @return a List of persistent InteractionResponses for the specified type. If no InteractionResponses exist,
     * an empty List is returned.
     */
    List<InteractionResponse> findByType(InteractionResponseType interactionResponseType);

    /**
     * Returns a List of persistent InteractionResponses for the specified state. If no InteractionResponse exist,
     * an empty List is returned.
     *
     * @param flag The flag of the InteractionResponse to retrieve a list of InteractionResponses for.
     *
     * @return a List of persistent InteractionResponses for the specified flag. If no InteractionResponses exist,
     * an empty List is returned.
     */
    List<InteractionResponse> findByFlag(String flag);

    /**
     * Returns a List of all persistent InteractionsResponses. If no InteractionResponses exist,
     * an empty List is returned.
     *
     * @return a List of all persistent InteractionsResponses. If no InteractionResponses exist,
     * an empty List is returned.
     */
    List<InteractionResponse> findAll();

    /**
     * Immediately saves the specified InteractionResponse to the backing store.
     *
     * @param interactionResponse the InteractionResponse to save.
     */
    void save(InteractionResponse interactionResponse);

    /**
     * Deletes the spcified InteractionResponse from the backing store.
     *
     * @param id the id of the InteractionResponse to delete.
     */
    void deleteById(int id);

    /**
     * Deletes all InteractionResponses from the backing store for the specified Interaction.
     *
     * @param interaction the Interaction to delete responses for.
     */
    void deleteByInteraction(Interaction interaction);

    /**
     * Deletes all InteractionResponses from the backing store for the specified User.
     *
     * @param user the User to delete responses for.
     */
    void deleteByUser(User user);
}
