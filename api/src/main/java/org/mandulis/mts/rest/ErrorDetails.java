package org.mandulis.mts.rest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ErrorDetails {
    private String error;
    private String details;
    private LocalDateTime timeStamp;

    public ErrorDetails(String error, String details, LocalDateTime timeStamp) {
        super();
        this.error = error;
        this.details = details;
        this.timeStamp = timeStamp;
    }
}
