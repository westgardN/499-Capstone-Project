package edu.metrostate.ics499.prim.model;

/**
 * An enum that can be used to test the type of an InteractionResponse.
 * If more states are added, this enum will need to be updated.
 */
public enum InteractionResponseType {
    /**
     * The Interaction was re-opened.
     */
    OPEN("OPEN"),
    /**
     * The Interaction was closed.
     */
    CLOSED("CLOSED"),
    /**
     * The Interaction was deferred.
     */
    DEFERRED("FOLLOWUP"),
    /**
     * The Interaction was deleted.
     */
    DELETED("IGNORED");

    private String interactionResponseType;

    private InteractionResponseType(String interactionResponseType) {
        this.interactionResponseType = interactionResponseType;
    }

    /**
     * Returns the interaction response state as a String.
     *
     * @return the interaction response state as a String.
     */
    public String getInteractionResponseType() {
        return interactionResponseType;
    }
}
