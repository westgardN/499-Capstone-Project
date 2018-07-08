package edu.metrostate.ics499.prim.model;

/**
 * An enum that can be used to test the social network of an Interaction.
 * If more social networks are added, this enum will need to be updated.
 */
public enum SocialNetwork {
    /**
     * The social network is Facebook.
     */
    FACEBOOK("FACEBOOK"),
    /**
     * The social network is Twitter.
     */
    TWITTER("TWITTER"),
    /**
     * The social network is LinkedIn.
     */
    LINKEDIN("LINKEDIN"),
    /**
     * The social network is not known to PRIM.
     */
    OTHER("OTHER");

    private String socialNetwork;

    private SocialNetwork(String socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    /**
     * Returns the social network as a String.
     *
     * @return the social network as a String.
     */
    public String getSocialNetwork() {
        return socialNetwork;
    }
}
