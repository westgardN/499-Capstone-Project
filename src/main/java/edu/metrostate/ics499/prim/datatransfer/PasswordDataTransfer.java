package edu.metrostate.ics499.prim.datatransfer;

import edu.metrostate.ics499.prim.validator.ValidPassword;

/**
 * The PasswordDataTransfer class is used when a user changes their password. It is used to validate
 * the new password.
 */
public class PasswordDataTransfer {

    /**
     * The user's current password.
     */
    private String currentPassword;

    /**
     * Uses our custom @ValidPassword annotation so we can use the built-in validation functions
     * of Spring to validate a password change request.
     */
    @ValidPassword
    private String newPassword;

    /**
     * Gets currentPassword
     *
     * @return value of currentPassword
     */
    public String getCurrentPassword() {
        return currentPassword;
    }

    /**
     * Sets currentPassword to the specified value in currentPassword
     *
     * @param currentPassword the new value for currentPassword
     */
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    /**
     * Gets newPassword
     *
     * @return value of newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Sets newPassword to the specified value in newPassword
     *
     * @param newPassword the new value for newPassword
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
