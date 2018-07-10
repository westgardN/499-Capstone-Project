package edu.metrostate.ics499.prim.exception;

/**
 * Thrown when requesting a user and the user doesn't exist in the system.
 * Not often thrown.
 */
public class UserNotFoundException extends Throwable{
    /**
     * Constructs a new UserNotFoundException with {@code null} as its detail message.
     */
    public UserNotFoundException() {
        super();
    }

    /**
     * Constructs a new UserNotFoundException with the specified detail message.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public UserNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new UserNotFoundException with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this throwable's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A {@code null} value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     */
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
