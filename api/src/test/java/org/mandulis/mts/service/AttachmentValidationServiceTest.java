package org.mandulis.mts.service;

import org.junit.jupiter.api.Test;
import org.mandulis.mts.exception.AttachmentWithoutNameException;
import org.mandulis.mts.exception.EmptyAttachmentException;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class AttachmentValidationServiceTest {

    @Test
    void shouldThrowExceptionWhenNoOriginalName() {
        var multipart = new MockMultipartFile("file", null, "text/plain", "asd".getBytes());

        assertThrows(
                AttachmentWithoutNameException.class,
                () -> new AttachmentValidationService().validateAttachment(multipart)
        );
    }

    @Test
    void shouldThrowExceptionWhenEmptyOriginalName() {
        var multipart = new MockMultipartFile("file", "", "text/plain", "asd".getBytes());

        assertThrows(
                AttachmentWithoutNameException.class,
                () -> new AttachmentValidationService().validateAttachment(multipart)
        );
    }

    @Test
    void shouldThrowExceptionWhenFileIsEmpty() {
        var multipart = new MockMultipartFile("file", "filename.exe", "text/plain", new byte[0]);

        assertThrows(
                EmptyAttachmentException.class,
                () -> new AttachmentValidationService().validateAttachment(multipart)
        );
    }

    @Test
    void shouldPass() {
        var multipart = new MockMultipartFile("file", "filename.exe", "text/plain", "content".getBytes());
        try {
            new AttachmentValidationService().validateAttachment(multipart);
        } catch (Exception e) {
            fail();
        }
    }
}
