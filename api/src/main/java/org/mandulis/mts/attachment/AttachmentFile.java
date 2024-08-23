package org.mandulis.mts.attachment;

import lombok.*;
import org.mandulis.mts.exception.ProcessingMultipartFileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttachmentFile {
    private String fileName;
    private Long ticketId;
    private InputStream inputStream;
    private String contentType;


    private InputStream toInputStream(MultipartFile multipartFile) {
        try {
            return multipartFile.getInputStream();
        } catch (IOException e) {
            throw new ProcessingMultipartFileException("File invalid.", e);
        }
    }
}
