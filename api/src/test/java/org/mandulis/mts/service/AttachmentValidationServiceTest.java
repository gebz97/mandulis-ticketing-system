package org.mandulis.mts.service;

import org.junit.jupiter.api.Test;
import org.mandulis.mts.dto.AttachmentDto;
import org.mandulis.mts.exception.AttachmentWithoutNameException;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class AttachmentValidationServiceTest {

    @Test
    void shouldThrowExceptionWhenNoOriginalName() {
        var multipart = new MockMultipartFile("file", null, "text/plain", "asd".getBytes());

        assertThrows(
                AttachmentWithoutNameException.class,
                () -> new AttachmentValidationService().validateAttachment(new AttachmentDto(multipart, 43L))
        );
    }

    @Test
    void shouldThrowExceptionWhenEmptyOriginalName() {
        var multipart = new MockMultipartFile("file", "", "text/plain", "asd".getBytes());

        assertThrows(
                AttachmentWithoutNameException.class,
                () -> new AttachmentValidationService().validateAttachment(new AttachmentDto(multipart, 43L))
        );
    }

    @Test
    void shouldPass() {
        var multipart = new MockMultipartFile("file", "filename.exe", "text/plain", "content".getBytes());
        try {
            new AttachmentValidationService().validateAttachment(new AttachmentDto(multipart, 43L));
        } catch (Exception e) {
            fail();
        }
    }
}
