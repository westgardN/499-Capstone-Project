package edu.metrostate.ics499.prim.model;

/**
 * An enum that can be used to test the state of an Interaction.
 * If more states are added, this enum will need to be updated.
 */
public enum InteractionState {
    /**
     * The Interaction is OPEN.
     */
    OPEN("OPEN"),
    /**
     * The Interaction is CLOSED.
     */
    CLOSED("CLOSED"),
    /**
     * The Interaction is DEFERRED.
     */
    DEFERRED("DEFERRED"),
    /**
     * The Interaction is DELETED.
     */
    DELETED("DELETED");

    private String interactionState;

    private InteractionState(String interactionState) {
        this.interactionState = interactionState;
    }

    /**
     * Returns the interaction state as a String.
     *
     * @return the interaction state as a String.
     */
    public String getInteractionState() {
        return interactionState;
    }
}
