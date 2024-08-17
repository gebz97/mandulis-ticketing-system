package org.mandulis.mts.dto;

import java.io.InputStream;

public class AttachmentDto {
    public String fileName;
    public Long ticketId;
    public InputStream inputStream;

    public AttachmentDto(String fileName, Long ticketId, InputStream inputStream) {
        this.fileName = fileName;
        this.ticketId = ticketId;
        this.inputStream = inputStream;
    }
}
