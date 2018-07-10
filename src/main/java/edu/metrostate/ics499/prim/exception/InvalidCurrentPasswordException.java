package edu.metrostate.ics499.prim.exception;

/**
 * Thrown when a user attempts to change their password but the current password doesn't match.
 */
public class InvalidCurrentPasswordException extends Throwable {

    /**
     * Constructs a new InvalidCurrentPasswordException with {@code null} as its detail message.
     */
    public InvalidCurrentPasswordException() {
        super();
    }

    /**
     * Constructs a new InvalidCurrentPasswordException with the specified detail message.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public InvalidCurrentPasswordException(String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidCurrentPasswordException with the specified detail message and
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
    public InvalidCurrentPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
