package org.mandulis.mts.dto;

import org.mandulis.mts.exception.ProcessingMultipartFileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class AttachmentDto {
    public String fileName;
    public Long ticketId;
    public InputStream inputStream;
    public String contentType;

    public AttachmentDto(MultipartFile multipartFile, Long ticketId) {
        this.fileName = multipartFile.getOriginalFilename();
        this.ticketId = ticketId;
        this.inputStream = toInputStream(multipartFile);
        this.contentType = multipartFile.getContentType();
    }

    private InputStream toInputStream(MultipartFile multipartFile) {
        try {
            return multipartFile.getInputStream();
        } catch (IOException e) {
            throw new ProcessingMultipartFileException("File invalid.", e);
        }
    }
}
