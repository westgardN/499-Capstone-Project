package edu.metrostate.ics499.prim.repository;

import edu.metrostate.ics499.prim.model.*;

import java.util.List;

/**
 * The InteractionDao interface defines the operations that can be performed for an Interaction.
 */
public interface InteractionDao extends IRepository {

    /**
     * Returns a persistent Interaction object identified by the specified id.
     * If no Interaction with that id exists, null is returned.
     *
     * @param id the Interaction Id to retrieve.
     *
     * @return a persistent Interaction object identified by the specified id.
     * If no Interaction with that id exists, null is returned.
     */
    Interaction findById(int id);

    /**
     * Returns a persistent Interaction object identified by the specified message id.
     * If no Interaction with that message id exists, null is returned.
     *
     * @param messageId the Interaction Message Id to retrieve.
     * @param socialNetwork the Social Network associated with the message.
     *
     * @return a persistent Interaction object identified by the specified message id.
     * If no Interaction with that id exists, null is returned.
     */
    Interaction findBySocialNetworkAndMessageId(String messageId, SocialNetwork socialNetwork);

    /**
     * Returns a List of persistent Interactions for the specified Social Network. If no Interactions exist,
     * an empty List is returned.
     *
     * @param socialNetwork the Social Network to find Interactions for.
     * @return a List of persistent Interactions for the specified Social Network. If no Interactions exist,
     * an empty List is returned.
     */
    List<Interaction> findBySocialNetwork(SocialNetwork socialNetwork);

    /**
     * Returns a List of persistent Interactions for the specified type. If no Interactions exist,
     * an empty List is returned.
     *
     * @param interactionType the type to find Interactions for.
     * @return a List of persistent Interactions for the specified type. If no Interactions exist,
     * an empty List is returned.
     */
    List<Interaction> findByType(InteractionType interactionType);

    /**
     * Returns a List of persistent Interactions for the specified flag. If no Interactions exist,
     * an empty List is returned.
     *
     * @param flag the flag to find Interactions for.
     * @return a List of persistent Interactions for the specified flag. If no Interactions exist,
     * an empty List is returned.
     */
    List<Interaction> findByFlag(InteractionFlag flag);

    /**
     * Returns a List of persistent Interactions for the specified state. If no Interactions exist,
     * an empty List is returned.
     *
     * @param state the state to find Interactions for.
     * @return a List of persistent Interactions for the specified state. If no Interactions exist,
     * an empty List is returned.
     */
    List<Interaction> findByState(InteractionState state);

    /**
     * Returns a List of persistent Interactions that have no sentiment score. If no Interactions exist,
     * an empty List is returned.
     *
     * @return a List of persistent Interactions that have no sentiment score. If no Interactions exist,
     * an empty List is returned.
     */
    List<Interaction> findWithoutSentiment();

    /**
     * Returns a List of persistent Interactions that have no message. If no Interactions exist,
     * an empty List is returned.
     *
     * @return a List of persistent Interactions that have no message. If no Interactions exist,
     * an empty List is returned.
     */
    List<Interaction> findWithNoMessage();

    /**
     * Returns a List of all persistent Interactions. If no Interactions exist,
     * an empty List is returned.
     *
     * @return a List of all persistent Interactions. If no Interactions exist,
     * an empty List is returned.
     */
    List<Interaction> findAll();

    /**
     * Immediately saves the specified Interaction to the backing store.
     *
     * @param interaction the Interaction to save.
     */
    void save(Interaction interaction);

    /**
     * Deletes the spcified Interaction from the backing store. If this Interaction has responses, then it will not
     * be deleted.
     *
     * @param id the id of the Interaction to delete.
     */
    void deleteById(int id);

    /**
     * Returns true if the message id of the specified Interaction already exists in the database.
     *
     * @param interaction The interaction to check for. Message ID must be populated.
     *
     * @return true if the specified interaction message id already exists in the database.
     */
    boolean interactionMessageExists(Interaction interaction);

    /**
     * Returns a List of all Open persistent Interactions. If no Open Interactions exist,
     * an empty List is returned.
     *
     * @return a List of all Open persistent Interactions. If no Open Interactions exist,
     * an empty List is returned.
     */
    List<Interaction> findAllOpen();

    /**
     * Returns a List of all Closed persistent Interactions. If no Closed Interactions exist,
     * an empty List is returned.
     *
     * @return a List of all Closed persistent Interactions. If no Closed Interactions exist,
     * an empty List is returned.
     */
    List<Interaction> findAllClosed();

    /**
     * Returns a List of all Deferred persistent Interactions. If no Deferred Interactions exist,
     * an empty List is returned.
     *
     * @return a List of all Deferred persistent Interactions. If no Deferred Interactions exist,
     * an empty List is returned.
     */
    List<Interaction> findAllDeferred();

    /**
     * Returns a List of all Deleted persistent Interactions. If no Deleted Interactions exist,
     * an empty List is returned.
     *
     * @return a List of all Deleted persistent Interactions. If no Deleted Interactions exist,
     * an empty List is returned.
     */
    List<Interaction> findAllDeleted();
}
