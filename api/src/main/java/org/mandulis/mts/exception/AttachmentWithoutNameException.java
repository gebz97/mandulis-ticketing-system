package org.mandulis.mts.exception;

public class AttachmentWithoutNameException extends RuntimeException {
    public static final String MESSAGE = "No attachment name provided.";

    public AttachmentWithoutNameException() {
        super(MESSAGE);
    }
}
