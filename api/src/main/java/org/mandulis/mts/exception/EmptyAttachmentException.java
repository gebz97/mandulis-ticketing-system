package org.mandulis.mts.exception;

public class EmptyAttachmentException extends RuntimeException {
    public static final String MESSAGE = "No attachment provided.";

    public EmptyAttachmentException() {super(MESSAGE);}
}
