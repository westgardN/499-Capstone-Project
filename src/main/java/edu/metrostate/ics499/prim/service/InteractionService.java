package edu.metrostate.ics499.prim.service;

import edu.metrostate.ics499.prim.model.Interaction;

import java.util.List;

public interface InteractionService {

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
     * Returns a List of persistent Interactions for the specified Social Network. If no Interactions exist,
     * an empty List is returned.
     *
     * @param socialNetwork the Social Network to find Interactions for.
     * @return a List of persistent Interactions for the specified Social Network. If no Interactions exist,
     * an empty List is returned.
     */
    List<Interaction> findBySocialNetwork(String socialNetwork);

    /**
     * Returns a List of persistent Interactions for the specified source. If no Interactions exist,
     * an empty List is returned.
     *
     * @param source the source to find Interactions for.
     * @return a List of persistent Interactions for the specified source. If no Interactions exist,
     * an empty List is returned.
     */
    List<Interaction> findBySource(String source);

    /**
     * Returns a List of persistent Interactions for the specified flag. If no Interactions exist,
     * an empty List is returned.
     *
     * @param flag the flag to find Interactions for.
     * @return a List of persistent Interactions for the specified flag. If no Interactions exist,
     * an empty List is returned.
     */
    List<Interaction> findByFlag(String flag);

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
     * Updates the persistent Interaction based on the specified Interaction.
     *
     * @param interaction the Interaction to update.
     */
    void update(Interaction interaction);

    /**
     * Deletes the spcified Interaction from the backing store. If this Interaction has responses, then it will not
     * be deleted.
     *
     * @param id the id of the Interaction to delete.
     */
    void deleteById(int id);

}