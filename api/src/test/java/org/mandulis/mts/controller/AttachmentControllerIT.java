package org.mandulis.mts.controller;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mandulis.mts.entity.Attachment;
import org.mandulis.mts.exception.AttachmentWithoutNameException;
import org.mandulis.mts.exception.EmptyAttachmentException;
import org.mandulis.mts.repository.AttachmentRepository;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AttachmentControllerIT {

    @Value("${storage.location}")
    private String storageLocation;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private DatabaseHelper databaseHelper;

    // disable security in tests
    @MockBean
    private SecurityFilterChain securityFilterChain;

    @BeforeEach
    void before() {
        databaseHelper.clearCategories();
        databaseHelper.clearTickets();
        databaseHelper.clearUsers();
    }

    @AfterEach
    void after() throws IOException {
        File testFileStorage = new File(storageLocation);
        FileUtils.deleteDirectory(testFileStorage);
    }

    @Test
        // todo: maybe there is some property to keep sexy names?
    void shouldUploadAttachment() throws Exception {
        // given
        var category = databaseHelper.setupCategory();
        var user = databaseHelper.setupUser();
        var ticket = databaseHelper.setupTicket(user, category);

        String filename = "filename.txt";
        byte[] fileContent = "some xml".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", filename, "text/plain", fileContent);

        String url = String.format("/api/v1/user/tickets/%d/attachments", ticket.getId());
        // when
        mockMvc.perform(multipart(url).file(file))
                .andExpect(status().isCreated());

        // then
        var attachments = attachmentRepository.findAll();
        assertEquals(1, attachments.size());
        Attachment dbAttachment = attachments.getFirst();
        assertEquals(filename, dbAttachment.getFileName());
        String storedFilePath = storageLocation + "/" + filename;
        assertEquals(storedFilePath, dbAttachment.getFilePath());
        assertEquals(ticket.getId(), dbAttachment.getTicket().getId());

        File storedFile = new File(storedFilePath);
        assertTrue(storedFile.exists() && !storedFile.isDirectory());
        assertArrayEquals(fileContent, Files.readAllBytes(storedFile.toPath()));
    }

    @Test
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
    void shouldThrowExceptionWhenNoAttachmentName() throws Exception {
        // given
        MockMultipartFile file = new MockMultipartFile("file", "", "text/plain", new byte[0]);

        String url = String.format("/api/v1/user/tickets/%d/attachments", 123L);

        // when, then
        mockMvc.perform(multipart(url).file(file))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.content[0].error").value(AttachmentWithoutNameException.MESSAGE));
    }
}
