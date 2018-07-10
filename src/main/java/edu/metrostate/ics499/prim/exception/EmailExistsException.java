package edu.metrostate.ics499.prim.exception;

/**
 * Thrown when registering a new user account and the provided e-mail already exists in the system.
 */
public class EmailExistsException extends Throwable {

    /**
     * Constructs a new EmailExistsException with {@code null} as its detail message.
     */
    public EmailExistsException() {
        super();
    }

    /**
     * Constructs a new EmailExistsException with the specified detail message.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public EmailExistsException(String message) {
        super(message);
    }

    /**
     * Constructs a new EmailExistsException with the specified detail message and
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
    public EmailExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
