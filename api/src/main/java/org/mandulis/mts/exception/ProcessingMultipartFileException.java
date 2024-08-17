package org.mandulis.mts.exception;

public class ProcessingMultipartFileException extends RuntimeException {

    public ProcessingMultipartFileException(String message, Exception e) {
        super(message, e);
    }
}
