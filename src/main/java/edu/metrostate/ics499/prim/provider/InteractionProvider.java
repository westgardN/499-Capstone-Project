package edu.metrostate.ics499.prim.provider;

import edu.metrostate.ics499.prim.model.Interaction;

import java.util.List;

/**
 * The InteractionProvider can be implemented by any data class that is capable of providing an
 * Interaction from its data.
 */
public interface InteractionProvider {

    /**
     * Returns a List of Interactions or an empty list if there is no data.
     *
     * @return a List of Interactions or an empty list if there is no data.
     */
    List<Interaction> getInteractions();
}
