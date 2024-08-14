package org.mandulis.mts.exception;

public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException(final String message) {
        super(message);
    }
}
