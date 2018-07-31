package edu.metrostate.ics499.prim.model;

/**
 * An enum that can be used to test the type of an Interaction.
 * If more types are added, this enum will need to be updated.
 */
public enum InteractionType {
    /**
     * The Interaction is a LINK item. Mainly used by Facebook.
     */
    LINK("LINK"),
    /**
     * The Interaction is a PHOTO item. Mainly used by Facebook.
     */
    PHOTO("PHOTO"),
    /**
     * The Interaction is a PHOTO item. Mainly used by Facebook.
     */
    VIDEO("VIDEO"),
    /**
     * The Interaction is a STATUS item. Mainly used by Facebook.
     */
    STATUS("STATUS"),
    /**
     * The Interaction is a RETWEET. Mainly used by Twitter.
     */
    RETWEET("RETWEET"),
    /**
     * The Interaction is a TWEET. Mainly used by Twitter.
     */
    TWEET("TWEET"),
    /**
     * The Interaction is a MENTION. Mainly used by Twitter.
     */
    MENTION("MENTION"),
    /**
     * The Interaction is of an UNKNOWN type.
     */
    UNKNOWN("UNKNOWN");

    private String interactionType;

    private InteractionType(String interactionType) {
        this.interactionType = interactionType;
    }

    /**
     * Returns the interaction type as a String.
     *
     * @return the interaction type as a String.
     */
    public String getInteractionType() {
        return interactionType;
    }
}
