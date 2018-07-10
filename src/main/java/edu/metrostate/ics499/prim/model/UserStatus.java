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
     * The user account is enabled and can login.
     */
    ENABLED("ENABLED"),
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
    ACCOUNT_EXPIRED("ACCOUNT_EXPIRED"),
    /**
     * The password has expired and the user needs to change their password before they can login.
     */
    PASSWORD_EXPIRED("PASSWORD_EXPIRED");

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
