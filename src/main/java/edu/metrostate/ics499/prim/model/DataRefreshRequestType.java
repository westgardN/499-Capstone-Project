package edu.metrostate.ics499.prim.model;

/**
 * An enum that can be used to test the type of a DataRefreshRequest.
 * If more types are added, this enum will need to be updated.
 */
public enum DataRefreshRequestType {
    /**
     * This is a brand new Interaction
     */
    SOCIAL("SOCIAL"),
    /**
     * The Interaction has no message
     */
    SENTIMENT("SENTIMENT");

    private String dataRefreshRequestType;

    private DataRefreshRequestType(String dataRefreshRequestType) {
        this.dataRefreshRequestType = dataRefreshRequestType;
    }

    /**
     * Returns the data refresh request type as a String.
     * @return the data refresh request type as a String.
     */
    public String getDataRefreshRequestType() {
        return dataRefreshRequestType;
    }
}
