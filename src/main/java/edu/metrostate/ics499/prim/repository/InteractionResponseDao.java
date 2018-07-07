package edu.metrostate.ics499.prim.repository;

import edu.metrostate.ics499.prim.model.InteractionResponse;

import java.util.List;

/**
 * The InteractionResponseDao interface defines the operations that can be performed for an
 * InteractionResponse.
 */
public interface InteractionResponseDao {

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
     * @param interactionId The Id of the Interaction to retrieve a list of InteractionResponses for.
     *
     * @return a List of persistent InteractionResponses for the specified Interaction. If no InteractionResponses exist,
     * an empty List is returned.
     */
    List<InteractionResponse> findByInteractionId(int interactionId);

    /**
     * Returns a List of persistent InteractionResponses for the specified User. If no InteractionResponses exist,
     * an empty List is returned.
     *
     * @param userId The Id of the User to retrieve a list of InteractionResponses for.
     *
     * @return a List of persistent InteractionResponses for the specified User. If no InteractionResponses exist,
     * an empty List is returned.
     */
    List<InteractionResponse> findByUserId(int userId);

    /**
     * Returns a List of persistent InteractionResponses for the specified type. If no InteractionResponses exist,
     * an empty List is returned.
     *
     * @param type The type of response to retrieve a list of InteractionResponses for.
     *
     * @return a List of persistent InteractionResponses for the specified type. If no InteractionResponses exist,
     * an empty List is returned.
     */
    List<InteractionResponse> findByType(String type);

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
     * Deletes the spcified InteractionResponse from the backing store.
     *
     * @param id the id of the InteractionResponse to delete.
     */
    void deletebyId(int id);

    /**
     * Deletes all InteractionResponses from the backing store for the specified Interaction Id.
     *
     * @param InteractionId the id of the Interaction to delete responses for.
     */
    void deleteByInteractionId(int InteractionId);

    /**
     * Deletes all InteractionResponses from the backing store for the specified User Id.
     *
     * @param userId the id of the User to delete responses for.
     */
    void deleteByUserId(int userId);
}
