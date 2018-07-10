package edu.metrostate.ics499.prim.model;

/**
 * An enum that can be used to test the flag of an Interaction.
 * If more flags are added, this enum will need to be updated.
 */
public enum InteractionFlag {
    /**
     * This is a brand new Interaction
     */
    NEW("NEW"),
    /**
     * The Interaction has no message
     */
    NO_MESSAGE("NO_MESSAGE"),
    /**
     * The Interaction has not been scored yet for sentiment
     */
    NO_SENTIMENT_SCORE("NO_SCORE"),
    /**
     * The contents of the Interaction are negative
     */
    NEGATIVE("NEGATIVE"),
    /**
     * The entire contents of the Interaction is negative
     */
    ALL_NEGATIVE("ALL_NEGATIVE"),
    /**
     * The entire contents of the Interaction is mostly negative
     */
    MOSTLY_NEGATIVE("MOSTLY_NEGATIVE"),
    /**
     * The entire contents of the Interaction is slightly negative
     */
    SLIGHTLY_NEGATIVE("SLIGHTLY_NEGATIVE"),
    /**
     * The contents of the Interaction is positive
     */
    POSITIVE("POSITIVE"),
    /**
     * The entire contents of the Interaction is positive
     */
    ALL_POSITIVE("ALL_POSITIVE"),
    /**
     * The entire contents of the Interaction is mostly positive
     */
    MOSTLY_POSITIVE("MOSTLY_POSITIVE"),
    /**
     * The entire contents of the Interaction is slightly positive
     */
    SLIGHTLY_POSITIVE("SLIGHTLY_POSITIVE"),
    /**
     * The contents of the Interaction is neutral
     */
    NEUTRAL("NEUTRAL");

    private String flag;

    private InteractionFlag(String flag) {
        this.flag = flag;
    }

    /**
     * Returns the interaction flag as a String.
     *
     * @return the interaction flag as a String.
     */
    public String getInteractionFlag() {
        return flag;
    }
}
