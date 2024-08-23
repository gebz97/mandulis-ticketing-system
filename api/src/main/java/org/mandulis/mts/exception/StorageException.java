package org.mandulis.mts.exception;

public class StorageException extends RuntimeException {
    public StorageException(final String message, final Exception e) {
        super(message, e);
    }

    public StorageException(final String message) {
        super(message);
    }
}
