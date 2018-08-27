package edu.metrostate.ics499.prim.datatransfer;

import edu.metrostate.ics499.prim.model.Role;
import edu.metrostate.ics499.prim.validator.PasswordMatches;
import edu.metrostate.ics499.prim.validator.ValidEmail;
import edu.metrostate.ics499.prim.validator.ValidPassword;
import org.apache.logging.log4j.util.Strings;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * The UserDataTransfer class is a utility class for transferring user data.
 *
 * It's main purpose is to capture and validate data for a user.
 */
@PasswordMatches
public class UserDataTransfer {
    /**
     * The unique username of this user. Will typically be the same as the ssoId.
     */
    @NotNull
    @NotEmpty
    private String username;

    /**
     * The unique ssoId of this user. Used to login to PRIM and used to tie SSO provider accounts
     * to a PRIM account.
     */
    @NotNull
    @NotEmpty
    private String ssoId;

    /**
     * The first name of this user.
     */
    @NotNull
    @NotEmpty
    private String firstName;

    /**
     * The last name of this user.
     */
    @NotNull
    @NotEmpty
    private String lastName;

    /**
     * The password for this user.
     */
    @ValidPassword
    @NotNull
    @NotEmpty
    private String password;

    /**
     * Used when initially setting up the user and when changing the user's password.
     */
    private String confirmPassword;

    /**
     * The user's e-mail address. Used for activation, verification, and resetting passwords.
     */
    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

    /**
     * Gets username
     *
     * @return value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username to the specified value in username
     *
     * @param username the new value for username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets ssoId
     *
     * @return value of ssoId
     */
    public String getSsoId() {
        return ssoId;
    }

    /**
     * Sets ssoId to the specified value in ssoId
     *
     * @param ssoId the new value for ssoId
     */
    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

    /**
     * Gets firstName
     *
     * @return value of firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets firstName to the specified value in firstName
     *
     * @param firstName the new value for firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets lastName
     *
     * @return value of lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets lastName to the specified value in lastName
     *
     * @param lastName the new value for lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets password
     *
     * @return value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password to the specified value in password
     *
     * @param password the new value for password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets confirmPassword
     *
     * @return value of confirmPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * Sets confirmPassword to the specified value in confirmPassword
     *
     * @param confirmPassword the new value for confirmPassword
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * Gets email
     *
     * @return value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email to the specified value in email
     *
     * @param email the new value for email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns true if the password and confirm password
     *
     * @return true if the password and confirm passwords match; otherwise false.
     */
    public boolean passwordsMatch() {
        boolean result = false;

        if (password != null && confirmPassword != null) {
            result = password.equals(confirmPassword);
        } else if (password == null && confirmPassword == null) {
            result = true;
        }

        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserDataTransfer{");
        sb.append("username='").append(username).append('\'');
        sb.append(", ssoId='").append(ssoId).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", password='").append("*** PROTECTED ***").append('\'');
        sb.append(", confirmPassword='").append("*** PROTECTED ***").append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
