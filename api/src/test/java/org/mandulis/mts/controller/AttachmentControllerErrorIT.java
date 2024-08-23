package org.mandulis.mts.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mandulis.mts.exception.AttachmentWithoutNameException;
import org.mandulis.mts.exception.EmptyAttachmentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AttachmentControllerErrorIT extends BaseMinioIT {

    @Autowired
    private MockMvc mockMvc;

    // disable security in tests
    @MockBean
    private SecurityFilterChain securityFilterChain;

    @Test
    @DisplayName("no such ticket, expected 404")
    void shouldThrowExceptionWhenNoSuchTicket() throws Exception {
        // given
        String filename = "filename.txt";
        byte[] fileContent = "some xml".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", filename, "text/plain", fileContent);

        Long notExistingTicketId = 123L;
        String url = String.format("/api/v1/user/tickets/%d/attachments", notExistingTicketId);
        // when
        mockMvc.perform(multipart(url).file(file))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.content[0].error").value(String.format("Ticket[id=%d] not found.", notExistingTicketId)));
    }

    @Test
    @DisplayName("empty attachment, expect 400")
    void shouldThrowExceptionWhenEmptyFile() throws Exception {
        // given
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", new byte[0]);

        String url = String.format("/api/v1/user/tickets/%d/attachments", 123L);

        // when, then
        mockMvc.perform(multipart(url).file(file))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.content[0].error").value(EmptyAttachmentException.MESSAGE));
    }

    @Test
    @DisplayName("no attachment name, expect 400")
    void shouldThrowExceptionWhenNoAttachmentName() throws Exception {
        // given
        MockMultipartFile file = new MockMultipartFile("file", "", "text/plain", "content".getBytes());

        String url = String.format("/api/v1/user/tickets/%d/attachments", 123L);

        // when, then
        mockMvc.perform(multipart(url).file(file))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.content[0].error").value(AttachmentWithoutNameException.MESSAGE));
    }
}
