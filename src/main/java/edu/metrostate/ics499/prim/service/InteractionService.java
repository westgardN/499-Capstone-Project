package edu.metrostate.ics499.prim.service;

import edu.metrostate.ics499.prim.model.Interaction;
import edu.metrostate.ics499.prim.model.InteractionFlag;
import edu.metrostate.ics499.prim.model.InteractionType;
import edu.metrostate.ics499.prim.model.SocialNetwork;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * The InteractionService provides an interface for easily working with Interactions.
 */
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
    List<Interaction> findBySocialNetwork(SocialNetwork socialNetwork);

    /**
     * Returns a List of persistent Interactions for the specified type. If no Interactions exist,
     * an empty List is returned.
     *
     * @param source the source to find Interactions for.
     * @return a List of persistent Interactions for the specified type. If no Interactions exist,
     * an empty List is returned.
     */
    List<Interaction> findByType(InteractionType source);

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

    /**
     * Adds the list of Interactions.
     */
    void addInteractions(List<Interaction> interactions);

    /**
     * Retrieves data from the available data providers and adds them as Interactions to PRIM.
     */
    void addInteractionsFromDataProviders();

    /**
     * Returns a list that contains the count of interactions for each social network.
     *
     * @return a list that contains the count of interactions for each social network.
     */
    List<Object[]> interactionCountBySocialNetwork();

    /**
     * Returns a list that contains the count of interactions for each state.
     *
     * @return a list that contains the count of interactions for each state.
     */
    List<Object[]> interactionCountByState();

    /**
     * Returns a list of available reports along with hthe URL to get the data for the report.
     * The first element in the object array is the name of the report, and the second element
     * is the URL to get the data for that report.
     *
     * @param request the HttpServletRequest to get the host information from.
     *
     * @return a list of available reports along with hthe URL to get the data for the report.
     */
    List<Object[]> getAvailableReports(HttpServletRequest request);

    /**
     * Sets the status of the specified interaction to InteractionStatus.IGNORED.
     *
     * @param id the id of the interaction to ignore.
     */
    void ignoreById(int id);

    /**
     * Sets the status of the specified interaction to InteractionStatus.CLOSED.
     *
     * @param id the id of the interaction to ignore.
     */
    void closeById(int id);

    /**
     * Sets the status of the specified interaction to InteractionStatus.FOLLOWUP.
     *
     * @param id the id of the interaction to ignore.
     */
    void deferById(int id);

    /**
     * Sets the status of the specified interaction to InteractionStatus.OPEN.
     *
     * @param id the id of the interaction to ignore.
     */
    void reopenById(int id);
}
