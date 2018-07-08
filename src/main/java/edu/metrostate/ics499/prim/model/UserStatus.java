package edu.metrostate.ics499.prim.model;

/**
 * An enum that can be used to test the status of a user.
 * If more statuses are added, this enum will need to be updated.
 */
public enum UserStatus {
    /**
     * The user account has not been activated yet.
     */
    NOT_ACTIVATED("NOT_ACTIVATED"),
    /**
     * The user account is active and can login.
     */
    ACTIVE("ACTIVE"),
    /**
     * The admin has disabled the user's account and the user cannot login.
     */
    DISABLED("DISABLED"),
    /**
     * The account is locked due to too many successive failed login attempts.
     */
    LOCKED("LOCKED"),
    /**
     * The account has expired and the user needs to reset their password before they can login.
     */
    EXPIRED("EXPIRED");

    String userStatus;

    private UserStatus(String userStatus){
        this.userStatus = userStatus;
    }

    /**
     * Returns the UserStatus as a String.
     * @return
     */
    public String getUserStatus(){
        return userStatus;
    }

}
