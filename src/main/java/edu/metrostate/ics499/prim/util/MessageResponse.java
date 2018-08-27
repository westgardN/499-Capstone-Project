package edu.metrostate.ics499.prim.util;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * A MessageResponse can be used as the return value for REST methods.
 */
public class MessageResponse {
    private String message;
    private String error;

    public MessageResponse(final String message) {
        super();
        this.message = message;
    }

    public MessageResponse(final String message, final String error) {
        super();
        this.message = message;
        this.error = error;
    }

    public MessageResponse(List<ObjectError> allErrors, String error) {
        this.error = error;
        String temp = allErrors.stream().map(e -> {
            if (e instanceof FieldError) {
                return "{\"field\":\"" + ((FieldError) e).getField() + "\",\"defaultMessage\":\"" + e.getDefaultMessage() + "\"}";
            } else {
                return "{\"object\":\"" + e.getObjectName() + "\",\"defaultMessage\":\"" + e.getDefaultMessage() + "\"}";
            }
        }).collect(Collectors.joining(","));
        this.message = "[" + temp + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageResponse that = (MessageResponse) o;
        return Objects.equals(getMessage(), that.getMessage()) &&
                Objects.equals(getError(), that.getError());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessage(), getError());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MessageResponse{");
        sb.append("message='").append(message).append('\'');
        sb.append(", error='").append(error).append('\'');
        sb.append('}');
        return sb.toString();
    }

    /**
     * Gets message
     *
     * @return value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message to the specified value in message
     *
     * @param message the new value for message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets error
     *
     * @return value of error
     */
    public String getError() {
        return error;
    }

    /**
     * Sets error to the specified value in error
     *
     * @param error the new value for error
     */
    public void setError(String error) {
        this.error = error;
    }
}